package sinks;

import core.LogLevel;
import core.LogMessage;

public class ConsoleSink implements Sink {
    @Override
    public void log(LogMessage message) {
        if (message.getLevel().ordinal() >= LogLevel.ERROR.ordinal()) {
            System.err.println(message);
        } else {
            System.out.println(message);
        }
    }

    @Override
    public void close() {}
}
