package sink;

import log.LogMessage;

public interface Sink {
    void log(LogMessage message);
    void close();
}
