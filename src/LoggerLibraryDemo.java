import config.ConfigLoader;
import config.LoggerConfig;
import core.LogLevel;
import core.Logger;

public class LoggerLibraryDemo {
    public static void main(String[] args) {
        // Load configuration from the config.ConfigLoader
        LoggerConfig config = ConfigLoader.loadConfig();

        // Create core.Logger with loaded configuration
        Logger logger = new Logger(config);

        int i=10000;
        while (i-->0) {
            // Log some sample messages with different levels
            logger.log("This is an INFO message." + i, LogLevel.INFO, "InfoModule");
            logger.log("This is a DEBUG message." + i, LogLevel.DEBUG, "DebugModule");
            logger.log("This is a WARNING message." + i, LogLevel.WARN, "WarningModule");
            logger.log("This is an ERROR message." + i, LogLevel.ERROR, "ErrorModule");
            logger.log("This is a FATAL message!" + i, LogLevel.FATAL, "FatalModule");
        }

        // Close the logger to release resources (e.g., database connections, file handles)
        logger.close();
    }
}
