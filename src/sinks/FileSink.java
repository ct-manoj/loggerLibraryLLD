package sinks;

import core.LogMessage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Pattern;
import java.util.zip.GZIPOutputStream;

public class FileSink implements Sink {
    private final String fileLocation;
    private final long maxFileSize;
    private BufferedWriter writer;

    public FileSink(String fileLocation, long maxFileSize) {
        this.fileLocation = fileLocation;
        this.maxFileSize = maxFileSize;
        openWriter();
    }

    private void openWriter() {
        try {
            File file = new File(fileLocation);
            // Ensure parent directories exist
            file.getParentFile().mkdirs();
            writer = new BufferedWriter(new FileWriter(file, true));
        } catch (IOException e) {
            throw new RuntimeException("Unable to open log file: " + e.getMessage(), e);
        }
    }

    /**
     * Determines the next rotation number by scanning the parent directory for files
     * matching the pattern: baseName + ".{number}" or baseName + ".{number}.gz"
     */
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
     * Rotates the log file without deleting any files.
     * The active log file is renamed to "fileLocation.n" where n is the next available rotation number.
     * Then, a compressed copy "fileLocation.n.gz" is created from the rotated file.
     * The rotated file is kept intact.
     */
    private void rotateLogFile() {
        try {
            writer.close();
            File activeLog = new File(fileLocation);
            int rotationNumber = getNextRotationNumber();
            File rotatedFile = new File(fileLocation + "." + rotationNumber);

            // Rename active log file to the rotated file name.
            if (!activeLog.renameTo(rotatedFile)) {
                System.err.println("Log rotation failed: Unable to rename file " + activeLog.getName());
            }

            // Create a compressed copy without deleting the original rotated file.
            File gzFile = new File(fileLocation + "." + rotationNumber + ".gz");
            compressFile(rotatedFile, gzFile);
            // Delete the uncompressed rotated file after zipping
            if (!rotatedFile.delete()) {
                System.err.println("Failed to delete uncompressed rotated file: " + rotatedFile.getName());
            }

            // Open a new writer for the active log file.
            openWriter();
        } catch (IOException e) {
            System.err.println("Error during log rotation: " + e.getMessage());
        }
    }

    /**
     * Compresses the source file to the destination file using GZIP.
     */
    private void compressFile(File source, File destination) throws IOException {
        try (
                FileInputStream fis = new FileInputStream(source);
                FileOutputStream fos = new FileOutputStream(destination);
                GZIPOutputStream gzipOS = new GZIPOutputStream(fos)
        ) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = fis.read(buffer)) != -1) {
                gzipOS.write(buffer, 0, len);
            }
        }
    }

    @Override
    public synchronized void log(LogMessage message) {
        try {
            File file = new File(fileLocation);
            if (file.exists() && file.length() >= maxFileSize) {
                rotateLogFile();
            }
            writer.write(message.toString());
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            System.err.println("Error writing log message: " + e.getMessage());
        }
    }

    @Override
    public void close() {
        try {
            if (writer != null)
                writer.close();
        } catch (IOException e) {
            System.err.println("Error closing writer: " + e.getMessage());
        }
    }
}
