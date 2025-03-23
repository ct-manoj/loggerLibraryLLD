package core;

import config.ConfigLoader;
import config.LoggerConfig;
import factory.SinkFactory;
import sinks.ConsoleSink;
import sinks.SinkType;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Logger {
    private final LoggerConfig config;
    private final SimpleDateFormat dateFormat;
    private final Map<SinkType, Sink> sinkMap = new HashMap<>();

    public Logger() {
        this.config = ConfigLoader.loadConfig();;
        this.dateFormat = new SimpleDateFormat(config.getTimeFormat());
        sinkMap.put(SinkType.CONSOLE, SinkFactory.createSink(config, SinkType.CONSOLE)); // TODO: config.getDefaultSinkType()

        // Also create sinks for any additional mappings.
        for (SinkType st : config.getLevelSinkMapping().values()) {
            if (!sinkMap.containsKey(st)) {
                sinkMap.put(st, SinkFactory.createSink(config, st));
            }
        }
    }

    public void log(String content, LogLevel level, String namespace) {
        if (level.ordinal() < config.getLogLevel().ordinal()) {
            return; // Ignore messages below the threshold
        }
        String timestamp = dateFormat.format(new Date());
        LogMessage message = new LogMessage(content, level, namespace, timestamp);
        SinkType sinkType = config.getLevelSinkMapping().getOrDefault(level, SinkType.CONSOLE);
        Sink sink = sinkMap.get(sinkType);
        sink.log(message);
    }

    public void close() {
        for (Sink s : sinkMap.values()) {
            s.close();
        }
    }
}
