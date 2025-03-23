package config;

import core.LogLevel;
import core.ThreadModel;
import core.WriteMode;
import sinks.SinkType;

import java.util.HashMap;
import java.util.Map;

public class LoggerConfig {
    public static final SinkType DEFAULT_SINK_TYPE = SinkType.CONSOLE;
    private String timeFormat;      // e.g. "yyyy-MM-dd HH:mm:ss,SSS"
    private LogLevel logLevel;      // threshold level
    private String fileLocation;    // for file sink

    // DB-specific configuration
    private String dbHost;
    private int dbPort;
    private String dbName;
    private String dbUser;
    private String dbPassword;

    // Maximum file size for file sink rotation (in bytes)
    private long maxFileSize;

    private Map<LogLevel, SinkType> levelSinkMapping = new HashMap<>();

    private ThreadModel threadModel;
    private WriteMode writeMode;
    private SinkType defaultSinkType;

    public LoggerConfig(String timeFormat, LogLevel logLevel, SinkType defaultSinkType) {
        this.timeFormat = timeFormat;
        this.logLevel = logLevel;

        this.threadModel = ThreadModel.SINGLE;
        this.writeMode = WriteMode.SYNC;
        this.defaultSinkType = defaultSinkType;
    }

    // Setters for file sink config
    public void setFileLocation(String fileLocation) { this.fileLocation = fileLocation; }
    public void setMaxFileSize(long maxFileSize) { this.maxFileSize = maxFileSize; }

    // Setters for DB sink config
    public void setDbConfig(String dbHost, int dbPort, String dbName, String dbUser, String dbPassword) {
        this.dbHost = dbHost;
        this.dbPort = dbPort;
        this.dbName = dbName;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
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

    public void setLevelSinkMapping(Map<LogLevel, SinkType> mapping) {
        this.levelSinkMapping = mapping;
    }
    public Map<LogLevel, SinkType> getLevelSinkMapping() {
        return levelSinkMapping;
    }

    public ThreadModel getThreadModel() { return threadModel; }
    public void setThreadModel(ThreadModel threadModel) { this.threadModel = threadModel; }
    public WriteMode getWriteMode() { return writeMode; }
    public void setWriteMode(WriteMode writeMode) { this.writeMode = writeMode; }
    public SinkType getDefaultSinkType() { return defaultSinkType; }
}
