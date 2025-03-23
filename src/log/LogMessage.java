package log;

public class LogMessage {
    private final String content;
    private final LogLevel level;
    private final String namespace;
    private final String timestamp;

    public LogMessage(String content, LogLevel level, String namespace, String timestamp) {
        this.content = content;
        this.level = level;
        this.namespace = namespace;
        this.timestamp = timestamp;
    }

    public String getContent() {
        return content;
    }

    public LogLevel getLevel() {
        return level;
    }

    public String getNamespace() {
        return namespace;
    }

    public String getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return String.format("%s [%s] %s - %s", level, timestamp, namespace, content);
    }
}
