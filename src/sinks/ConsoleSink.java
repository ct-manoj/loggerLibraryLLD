package sinks;

import core.LogMessage;
import core.Sink;

public class ConsoleSink implements Sink {
    @Override
    public void log(LogMessage message) {
        System.out.println(message.toString());
    }

    @Override
    public void close() {
        // Nothing to close for console output.
    }
}
