package sink;

import config.LoggerConfig;
import sink.Sink;
import sink.ConsoleSink;
import sink.DBSink;
import sink.FileSink;
import sink.SinkType;

public class SinkFactory {
    private SinkFactory() {}

    public static Sink createSink(LoggerConfig config, SinkType sinkType) {
        switch (sinkType) {
            case FILE:
                return new FileSink(config.getFileLocation(), config.getMaxFileSize());
            case DB:
                return new DBSink(config.getDbHost(), config.getDbPort(), config.getDbName(),
                        config.getDbUser(), config.getDbPassword());
            case CONSOLE:
            default:
                return new ConsoleSink();
        }
    }
}
