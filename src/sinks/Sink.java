package sinks;

import core.LogMessage;

public interface Sink {
    void log(LogMessage message);
    void close();
}
