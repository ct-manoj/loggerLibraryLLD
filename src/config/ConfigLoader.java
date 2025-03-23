package config;

import log.LogLevel;
import log.ThreadModel;
import log.WriteMode;
import sink.SinkType;

import java.util.HashMap;
import java.util.Map;

public class ConfigLoader {
    public static final SinkType DEFAULT_SINK_TYPE = SinkType.CONSOLE;
    public static final LogLevel DEFAULT_LOG_LEVEL = LogLevel.INFO;

    public static LoggerConfig loadConfig() {
        Map<String, String> configMap = Config.getConfigMap();

        String timeFormat = configMap.getOrDefault("ts_format", "yyyy-MM-dd HH:mm:ss,SSS");
        LogLevel logLevel = LogLevel.valueOf(configMap.getOrDefault("threshold_log_level", DEFAULT_LOG_LEVEL.name()).toUpperCase());
        SinkType defaultSink = SinkType.fromString(configMap.getOrDefault("default_sink_type", DEFAULT_SINK_TYPE.name()));
        String fileLocation = configMap.getOrDefault("file_location", "logs/application.log");
        long maxFileSize = Long.parseLong(configMap.getOrDefault("max_file_size", "10"));
        String dbHost = configMap.getOrDefault("db_host", "127.0.0.1");
        int dbPort = Integer.parseInt(configMap.getOrDefault("db_port", "5432"));
        String dbName = configMap.getOrDefault("db_name", "logdb");
        String dbUser = configMap.getOrDefault("db_user", "dbuser");
        String dbPassword = configMap.getOrDefault("db_password", "dbpassword");
        ThreadModel threadModel = ThreadModel.fromValue(configMap.getOrDefault("thread_model", ThreadModel.SINGLE.name()));
        WriteMode writeMode = WriteMode.fromValue(configMap.getOrDefault("write_mode", WriteMode.SYNC.name()));

        LoggerConfig.Builder builder = LoggerConfig.builder()
                .timeFormat(timeFormat)
                .logLevel(logLevel)
                .defaultSinkType(defaultSink)
                .fileLocation(fileLocation)
                .maxFileSize(maxFileSize)
                .dbConfig(dbHost,dbPort,dbName,dbUser,dbPassword)
                .threadModel(threadModel)
                .writeMode(writeMode)
                .levelSinkMapping(getLogLevelSinkTypeMap(configMap));

        return builder.build();
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
}