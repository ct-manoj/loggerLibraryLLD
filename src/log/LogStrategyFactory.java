package log;

import processor.AsyncLogProcessor;
import processor.LogProcessor;
import processor.SyncLogProcessor;

public class LogStrategyFactory {

    private LogStrategyFactory(){}

    public static LogProcessor createStrategy(WriteMode writeMode, ThreadModel threadModel) {
        if (writeMode == WriteMode.ASYNC) {
            return new AsyncLogProcessor(threadModel);
        } else {
            return new SyncLogProcessor();
        }
    }
}
