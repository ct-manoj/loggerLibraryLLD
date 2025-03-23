package processor;

import log.LogMessage;
import sink.Sink;

public class SyncLogProcessor implements LogProcessor {
    @Override
    public void log(LogMessage message, Sink sink) {
        sink.log(message);
    }

    @Override
    public void close() {}
}
