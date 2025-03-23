package config;

import core.LogLevel;
import core.ThreadModel;
import core.WriteMode;
import sinks.SinkType;

import java.util.HashMap;
import java.util.Map;

public class LoggerConfig {
    private final String timeFormat;
    private final LogLevel logLevel;
    private String fileLocation;

    private String dbHost;
    private int dbPort;
    private String dbName;
    private String dbUser;
    private String dbPassword;

    private long maxFileSize;

    private Map<LogLevel, SinkType> levelSinkMapping;

    private ThreadModel threadModel;
    private WriteMode writeMode;
    private final SinkType defaultSinkType;

    private LoggerConfig(Builder builder) {
        this.timeFormat = builder.timeFormat;
        this.logLevel = builder.logLevel;
        this.defaultSinkType = builder.defaultSinkType;
        this.fileLocation = builder.fileLocation;
        this.maxFileSize = builder.maxFileSize;
        this.dbHost = builder.dbHost;
        this.dbPort = builder.dbPort;
        this.dbName = builder.dbName;
        this.dbUser = builder.dbUser;
        this.dbPassword = builder.dbPassword;
        this.levelSinkMapping = builder.levelSinkMapping;
        this.threadModel = builder.threadModel;
        this.writeMode = builder.writeMode;
    }

    public String getTimeFormat() { return timeFormat; }
    public LogLevel getLogLevel() { return logLevel; }
    public String getFileLocation() { return fileLocation; }
    public long getMaxFileSize() { return maxFileSize; }
    public String getDbHost() { return dbHost; }
    public int getDbPort() { return dbPort; }
    public String getDbName() { return dbName; }
    public String getDbUser() { return dbUser; }
    public String getDbPassword() { return dbPassword; }
    public Map<LogLevel, SinkType> getLevelSinkMapping() {
        return levelSinkMapping;
    }
    public ThreadModel getThreadModel() { return threadModel; }
    public WriteMode getWriteMode() { return writeMode; }
    public SinkType getDefaultSinkType() { return defaultSinkType; }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String timeFormat;
        private LogLevel logLevel;
        private SinkType defaultSinkType;
        private String fileLocation;
        private long maxFileSize;
        private String dbHost;
        private int dbPort;
        private String dbName;
        private String dbUser;
        private String dbPassword;
        private Map<LogLevel, SinkType> levelSinkMapping = new HashMap<>();
        private ThreadModel threadModel = ThreadModel.SINGLE;
        private WriteMode writeMode = WriteMode.SYNC;

        public Builder timeFormat(String timeFormat) {
            this.timeFormat = timeFormat;
            return this;
        }
        public Builder logLevel(LogLevel logLevel) {
            this.logLevel = logLevel;
            return this;
        }
        public Builder defaultSinkType(SinkType defaultSinkType) {
            this.defaultSinkType = defaultSinkType;
            return this;
        }
        public Builder fileLocation(String fileLocation) {
            this.fileLocation = fileLocation;
            return this;
        }
        public Builder maxFileSize(long maxFileSize) {
            this.maxFileSize = maxFileSize;
            return this;
        }

        public Builder dbConfig(String dbHost, int dbPort, String dbName, String dbUser, String dbPassword) {
            this.dbHost = dbHost;
            this.dbPort = dbPort;
            this.dbName = dbName;
            this.dbUser = dbUser;
            this.dbPassword = dbPassword;
            return this;
        }

        public Builder levelSinkMapping(Map<LogLevel, SinkType> mapping) {
            this.levelSinkMapping = mapping;
            return this;
        }

        public Builder threadModel(ThreadModel threadModel) {
            this.threadModel = threadModel;
            return this;
        }

        public Builder writeMode(WriteMode writeMode) {
            this.writeMode = writeMode;
            return this;
        }

        public LoggerConfig build() {
            return new LoggerConfig(this);
        }
    }
}
