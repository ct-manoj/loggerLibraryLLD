package core;

import config.ConfigLoader;
import config.LoggerConfig;
import factory.SinkFactory;
import sinks.SinkType;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Logger {
    private final LoggerConfig config;
    private final SimpleDateFormat dateFormat;
    private final Map<SinkType, Sink> sinkMap = new HashMap<>();
    private final String namespace;
    private ExecutorService executor;

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

        if (config.getWriteMode() == WriteMode.ASYNC) { // TODO: try strategy pattern maybe
            if (config.getThreadModel() == ThreadModel.SINGLE) {
                executor = Executors.newSingleThreadExecutor();
            } else { // MULTI
                executor = Executors.newCachedThreadPool();
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

        if (config.getWriteMode() == WriteMode.ASYNC && executor != null) {
            executor.submit(() -> sink.log(message));
        } else {
            sink.log(message);
        }
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
        if (executor != null) {
            executor.shutdown();
            try {
                executor.awaitTermination(5, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                System.err.println("Error shutting down logging executor: " + e.getMessage());
            }
        }

        for (Sink s : sinkMap.values()) { // TODO: remove comment: dont change order, first executor needs to finish
            s.close();
        }
    }
}
