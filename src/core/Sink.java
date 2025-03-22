package core;

public interface Sink {
    void log(LogMessage message);
    void close();
}
