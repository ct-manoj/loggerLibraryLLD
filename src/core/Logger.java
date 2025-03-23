package core;

import config.ConfigLoader;
import config.LoggerConfig;
import factory.SinkFactory;
import sinks.SinkType;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Logger {
    private final LoggerConfig config;
    private final SimpleDateFormat dateFormat;
    private final Map<SinkType, Sink> sinkMap = new HashMap<>();

    private final String namespace;

    public Logger(Class clazz) {
        this.namespace = clazz.getName();
        this.config = ConfigLoader.loadConfig();
        this.dateFormat = new SimpleDateFormat(config.getTimeFormat());
        sinkMap.put(SinkType.CONSOLE, SinkFactory.createSink(config, SinkType.CONSOLE)); // TODO: config.getDefaultSinkType()

        for (SinkType st : config.getLevelSinkMapping().values()) {
            if (!sinkMap.containsKey(st)) {
                sinkMap.put(st, SinkFactory.createSink(config, st));
            }
        }
    }

    private void log(String content, LogLevel level) {
        if (level.ordinal() < config.getLogLevel().ordinal()) {
            return; // Ignore messages below the threshold
        }
        String timestamp = dateFormat.format(new Date());
        LogMessage message = new LogMessage(content, level, namespace, timestamp);
        SinkType sinkType = config.getLevelSinkMapping().getOrDefault(level, SinkType.CONSOLE);
        Sink sink = sinkMap.get(sinkType);
        sink.log(message);
    }

    public void debug(String content) {
        log(content, LogLevel.DEBUG);
    }

    public void info(String content) {
        log(content, LogLevel.INFO);
    }

    public void warn(String content) {
        log(content, LogLevel.WARN);
    }

    public void error(String content) {
        log(content, LogLevel.ERROR);
    }

    public void fatal(String content) {
        log(content, LogLevel.FATAL);
    }

    public void close() {
        for (Sink s : sinkMap.values()) {
            s.close();
        }
    }
}
