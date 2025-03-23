package sink;

import log.LogMessage;
import util.LogFileManager;

public class FileSink implements Sink {
    private final LogFileManager fileManager;

    public FileSink(String fileLocation, long maxFileSize) {
        this.fileManager = new LogFileManager(fileLocation, maxFileSize);
    }

    @Override
    public synchronized void log(LogMessage message) {
        fileManager.write(message.toString());
    }

    @Override
    public void close() {
        fileManager.close();
    }
}
