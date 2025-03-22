package config;

import core.LogLevel;
import sinks.SinkType;

import java.util.Map;

public class ConfigLoader {
    public static LoggerConfig loadConfig() {
        Map<String, String> configMap = Config.getConfigMap();

        // Extract values from Map
        String timeFormat = configMap.getOrDefault("ts_format", "yyyy-MM-dd HH:mm:ss,SSS");
        LogLevel logLevel = LogLevel.valueOf(configMap.getOrDefault("log_level", LogLevel.INFO.name()).toUpperCase());
        SinkType sinkType = SinkType.fromString(configMap.getOrDefault("sink_type", SinkType.FILE.name()));

        LoggerConfig config = new LoggerConfig(timeFormat, logLevel, sinkType);

        // File sink.Sink settings
        if (sinkType == SinkType.FILE) {
            config.setFileLocation(configMap.getOrDefault("file_location", "logs/application.log"));
            config.setMaxFileSize(Long.parseLong(configMap.getOrDefault("max_file_size", "10")));
        }

        // DB sink.Sink settings
        if (sinkType == SinkType.DB) {
            config.setDbConfig(
                    configMap.getOrDefault("db_host", "127.0.0.1"),
                    Integer.parseInt(configMap.getOrDefault("db_port", "5432")),
                    configMap.getOrDefault("db_name", "logdb"),
                    configMap.getOrDefault("db_user", "dbuser"),
                    configMap.getOrDefault("db_password", "dbpassword")
            );
        }

        return config;
    }
}
