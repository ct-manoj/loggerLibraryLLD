package sinks;

import core.LogMessage;
import core.Sink;

public class ConsoleSink implements Sink {
    @Override
    public void log(LogMessage message) {
        System.out.println(message.toString());
    }

    @Override
    public void close() { // TODO: interface segregation
        // Nothing to close for console output.
    }
}
