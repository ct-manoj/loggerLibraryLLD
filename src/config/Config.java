package config;

import core.LogLevel;
import sinks.SinkType;

import java.util.HashMap;
import java.util.Map;

public class Config {
    private static final Map<String, String> configMap = new HashMap<>();

    static {
        // core.Logger settings
        configMap.put("ts_format", "yyyy-MM-dd HH:mm:ss,SSS");
        configMap.put("log_level", LogLevel.INFO.name());
        configMap.put("sink_type", SinkType.FILE.name());  // Change to "DB" for database logging

        configMap.put("sink_mapping.INFO", SinkType.FILE.name());
        configMap.put("sink_mapping.WARN", SinkType.FILE.name());
        configMap.put("sink_mapping.ERROR", SinkType.CONSOLE.name());

        // File sink.Sink Configuration
        configMap.put("file_location", "logs/application.log");
        configMap.put("max_file_size", "1048576"); // 1MB

        // Database sink.Sink Configuration (only used if sink_type = "DB")
        configMap.put("db_host", "127.0.0.1");
        configMap.put("db_port", "5432");
        configMap.put("db_name", "logdb");
        configMap.put("db_user", "dbuser");
        configMap.put("db_password", "dbpassword");
    }

    public static Map<String, String> getConfigMap() {
        return configMap;
    }
}
