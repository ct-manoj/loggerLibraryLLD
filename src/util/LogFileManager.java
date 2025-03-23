package util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Pattern;

public class LogFileManager {
    private final String fileLocation;
    private final long maxFileSize;
    private BufferedWriter writer;

    public LogFileManager(String fileLocation, long maxFileSize) {
        this.fileLocation = fileLocation;
        this.maxFileSize = maxFileSize;
        openWriter();
    }

    private void openWriter() {
        try {
            File file = new File(fileLocation);
            file.getParentFile().mkdirs();
            writer = new BufferedWriter(new FileWriter(file, true));
        } catch (IOException e) {
            throw new RuntimeException("Unable to open log file: " + e.getMessage(), e);
        }
    }

    private int getNextRotationNumber() {
        File logFile = new File(fileLocation);
        File parentDir = logFile.getParentFile();
        String baseName = logFile.getName();
        int maxRotation = 0;
        File[] files = parentDir.listFiles((dir, name) -> name.matches(Pattern.quote(baseName) + "\\.(\\d+)(\\.gz)?"));
        if (files != null) {
            for (File f : files) {
                String name = f.getName();
                // Extract the numeric part after the base name and a dot.
                String numberPart = name.substring(baseName.length() + 1);
                if (numberPart.endsWith(".gz")) {
                    numberPart = numberPart.substring(0, numberPart.length() - 3);
                }
                try {
                    int num = Integer.parseInt(numberPart);
                    if (num > maxRotation) {
                        maxRotation = num;
                    }
                } catch (NumberFormatException ignored) {}
            }
        }
        return maxRotation + 1;
    }

    /**
     * Rotates the log file:
     * - Closes the current writer.
     * - Renames the active file to include a rotation number.
     * - Compresses the rotated file using util.GzipCompressor.
     * - Deletes the uncompressed rotated file.
     * - Reopens the writer for the active log file.
     */
    public synchronized void rotate() throws IOException {
        writer.close();
        File activeLog = new File(fileLocation);
        int rotationNumber = getNextRotationNumber();
        File rotatedFile = new File(fileLocation + "." + rotationNumber);

        if (!activeLog.renameTo(rotatedFile)) {
            System.err.println("Log rotation failed: Unable to rename file " + activeLog.getName());
        }

        File gzFile = new File(fileLocation + "." + rotationNumber + ".gz");
        GzipCompressor.compress(rotatedFile, gzFile);

        if (!rotatedFile.delete()) {
            System.err.println("Failed to delete uncompressed rotated file: " + rotatedFile.getName());
        }
        openWriter();
    }

    public synchronized void write(String logLine) {
        try {
            File file = new File(fileLocation);
            if (file.exists() && file.length() >= maxFileSize) {
                rotate();
            }
            writer.write(logLine);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            System.err.println("Error writing log line: " + e.getMessage());
        }
    }

    public void close() {
        try {
            if (writer != null)
                writer.close();
        } catch (IOException e) {
            System.err.println("Error closing writer: " + e.getMessage());
        }
    }
}
