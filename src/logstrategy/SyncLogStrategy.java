package logstrategy;

import core.LogMessage;
import sinks.Sink;

public class SyncLogStrategy implements LogStrategy {
    @Override
    public void log(LogMessage message, Sink sink) {
        sink.log(message);
    }

    @Override
    public void close() {
        // No executor to close for synchronous strategy.
    }
}
