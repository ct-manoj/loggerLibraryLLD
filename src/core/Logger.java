package core;

import config.ConfigLoader;
import config.LoggerConfig;
import factory.LogStrategyFactory;
import strategy.LogStrategy;
import registry.SinkRegistry;
import registry.SinkRegistryFactory;
import sinks.Sink;
import sinks.SinkType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    private final LoggerConfig config;
    private final DateTimeFormatter dateFormatter;
    private final String namespace;
    private final LogStrategy logStrategy;
    private final SinkRegistry sinkRegistry;

    public Logger(Class clazz) {
        this.namespace = clazz.getName();
        this.config = ConfigLoader.loadConfig();
        this.dateFormatter = DateTimeFormatter.ofPattern(config.getTimeFormat());
        this.sinkRegistry = SinkRegistryFactory.createRegistry(config);
        this.logStrategy = LogStrategyFactory.createStrategy(config.getWriteMode(), config.getThreadModel());
    }

    private void log(String content, LogLevel level) {
        if (level.ordinal() < config.getLogLevel().ordinal()) {
            return; // Ignore messages below the threshold
        }
        String timestamp = dateFormatter.format(LocalDateTime.now());
        LogMessage message = new LogMessage(content, level, namespace, timestamp);
        SinkType sinkType = config.getLevelSinkMapping().getOrDefault(level, config.getDefaultSinkType());
        Sink sink = sinkRegistry.getSink(sinkType);
        logStrategy.log(message, sink);
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
        logStrategy.close();
        sinkRegistry.getAllSinks().forEach(Sink::close);
    }
}
