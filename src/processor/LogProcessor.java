package processor;

import log.LogMessage;
import sink.Sink;

public interface LogProcessor {
    void log(LogMessage message, Sink sink);
    void close();
}
