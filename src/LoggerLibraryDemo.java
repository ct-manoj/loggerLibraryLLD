import log.Logger;

public class LoggerLibraryDemo {
    private static final Logger logger = new Logger(LoggerLibraryDemo.class);

    public void run() {
        int i=10000;
        while (i-->0) {
            logger.debug("This is an DEBUG message." + i);
            logger.info("This is a INFO message." + i);
            logger.warn("This is a WARNING message." + i);
            logger.error("This is an ERROR message." + i);
            logger.fatal("This is a FATAL message!" + i);
        }

        logger.close();
    }
}
