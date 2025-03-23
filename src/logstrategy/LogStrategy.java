package logstrategy;

import core.LogMessage;
import sinks.Sink;

public interface LogStrategy {
    void log(LogMessage message, Sink sink);
    void close();
}
