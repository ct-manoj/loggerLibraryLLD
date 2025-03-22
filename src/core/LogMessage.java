package core;

public record LogMessage(String content, LogLevel level, String namespace, String timestamp) {

    @Override
    public String toString() {
        return String.format("%s [%s] %s - %s", level, timestamp, namespace, content);
    }
}
