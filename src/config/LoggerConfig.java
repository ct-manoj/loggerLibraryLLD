package config;

import core.LogLevel;
import sinks.SinkType;

public class LoggerConfig {
    private String timeFormat;      // e.g. "yyyy-MM-dd HH:mm:ss,SSS"
    private LogLevel logLevel;      // threshold level
    private SinkType sinkType;        // "FILE", "DB", etc.
    private String fileLocation;    // for file sink

    // DB-specific configuration
    private String dbHost;
    private int dbPort;
    private String dbName;
    private String dbUser;
    private String dbPassword;

    // Maximum file size for file sink rotation (in bytes)
    private long maxFileSize;

    public LoggerConfig(String timeFormat, LogLevel logLevel, SinkType sinkType) {
        this.timeFormat = timeFormat;
        this.logLevel = logLevel;
        this.sinkType = sinkType;
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
    public SinkType getSinkType() { return sinkType; }
    public String getFileLocation() { return fileLocation; }
    public long getMaxFileSize() { return maxFileSize; }
    public String getDbHost() { return dbHost; }
    public int getDbPort() { return dbPort; }
    public String getDbName() { return dbName; }
    public String getDbUser() { return dbUser; }
    public String getDbPassword() { return dbPassword; }
}
