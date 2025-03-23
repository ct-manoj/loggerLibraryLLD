import core.Logger;

public class LoggerLibraryDemo {
    private static final Logger logger = new Logger(LoggerLibraryDemo.class);

    public void run() {
        int i=10000;
        while (i-->0) {
            // Log some sample messages with different levels
            logger.debug("This is an INFO message." + i);
            logger.info("This is a DEBUG message." + i);
            logger.warn("This is a WARNING message." + i);
            logger.error("This is an ERROR message." + i);
            logger.fatal("This is a FATAL message!" + i);
        }

        // Close the logger to release resources (e.g., database connections, file handles)
        logger.close();
    }
}
