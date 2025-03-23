package config;

import core.LogLevel;
import core.ThreadModel;
import core.WriteMode;
import sinks.SinkType;

import java.util.HashMap;
import java.util.Map;

public class ConfigLoader {
    public static LoggerConfig loadConfig() {
        Map<String, String> configMap = Config.getConfigMap();

        // Extract values from Map
        String timeFormat = configMap.getOrDefault("ts_format", "yyyy-MM-dd HH:mm:ss,SSS");
        LogLevel logLevel = LogLevel.valueOf(configMap.getOrDefault("threshold_log_level", LoggerConfig.DEFAULT_LOG_LEVEL.name()).toUpperCase());
        SinkType defaultSink = SinkType.fromString(configMap.getOrDefault("default_sink_type", LoggerConfig.DEFAULT_SINK_TYPE.name()));

        LoggerConfig config = new LoggerConfig(timeFormat, logLevel, defaultSink);

        config.setLevelSinkMapping(getLogLevelSinkTypeMap(configMap));

        // TODO: if condition????
        applyFileSettings(config, configMap);

        // TODO: if condition????
        applyDbSettings(config, configMap);

        applyOptionalSettings(config, configMap);

        return config;
    }

    private static Map<LogLevel, SinkType> getLogLevelSinkTypeMap(Map<String, String> configMap) {
        Map<LogLevel, SinkType> levelSinkMapping = new HashMap<>();
        for (String key : configMap.keySet()) {
            if (key.startsWith("sink_mapping.")) {
                String levelStr = key.substring("sink_mapping.".length());
                try {
                    LogLevel level = LogLevel.valueOf(levelStr.toUpperCase());
                    SinkType sinkType = SinkType.fromString(configMap.get(key));
                    levelSinkMapping.put(level, sinkType);
                } catch (IllegalArgumentException e) {
                    System.err.println("Invalid log level in config for key: " + key);
                }
            }
        }
        return levelSinkMapping;
    }

    private static void applyFileSettings(LoggerConfig config, Map<String, String> configMap) {
        String fileLocation = configMap.getOrDefault("file_location", "logs/application.log");
        config.setFileLocation(fileLocation);
        long maxFileSize = Long.parseLong(configMap.getOrDefault("max_file_size", "10"));
        config.setMaxFileSize(maxFileSize);
    }

    private static void applyDbSettings(LoggerConfig config, Map<String, String> configMap) {
        config.setDbConfig(
                configMap.getOrDefault("db_host", "127.0.0.1"),
                Integer.parseInt(configMap.getOrDefault("db_port", "5432")),
                configMap.getOrDefault("db_name", "logdb"),
                configMap.getOrDefault("db_user", "dbuser"),
                configMap.getOrDefault("db_password", "dbpassword")
        );
    }

    private static void applyOptionalSettings(LoggerConfig config, Map<String, String> configMap) {
        ThreadModel threadModel = ThreadModel.fromString(
                configMap.getOrDefault("thread_model", ThreadModel.SINGLE.name()));
        config.setThreadModel(threadModel);
        WriteMode writeMode = WriteMode.fromString(
                configMap.getOrDefault("write_mode", WriteMode.SYNC.name()));
        config.setWriteMode(writeMode);
    }
}
