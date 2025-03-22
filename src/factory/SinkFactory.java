package factory;

import config.LoggerConfig;
import core.Sink;
import sinks.ConsoleSink;
import sinks.DBSink;
import sinks.FileSink;

public class SinkFactory {
    public static Sink createSink(LoggerConfig config) {
        switch (config.getSinkType()) {
            case FILE:
                return new FileSink(config.getFileLocation(), config.getMaxFileSize());
            case DB:
                return new DBSink(config.getDbHost(), config.getDbPort(), config.getDbName(),
                        config.getDbUser(), config.getDbPassword());
            case CONSOLE:
            default:
                return new ConsoleSink(); // Default to sink.ConsoleSink
        }
    }
}
