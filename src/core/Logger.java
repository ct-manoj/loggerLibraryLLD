package core;

import config.LoggerConfig;
import factory.SinkFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
    private final LoggerConfig config;
    private final Sink sink;
    private final SimpleDateFormat dateFormat;

    public Logger(LoggerConfig config) {
        this.config = config;
        this.sink = SinkFactory.createSink(config);
        this.dateFormat = new SimpleDateFormat(config.getTimeFormat());
    }

    public void log(String content, LogLevel level, String namespace) {
        if (level.ordinal() < config.getLogLevel().ordinal()) {
            return; // Ignore messages below the threshold
        }
        String timestamp = dateFormat.format(new Date());
        LogMessage message = new LogMessage(content, level, namespace, timestamp);
        sink.log(message);
    }

    public void close() {
        sink.close();
    }
}
