package factory;

import core.ThreadModel;
import core.WriteMode;
import strategy.AsyncLogStrategy;
import strategy.LogStrategy;
import strategy.SyncLogStrategy;

public class LogStrategyFactory {
    public static LogStrategy createStrategy(WriteMode writeMode, ThreadModel threadModel) {
        if (writeMode == WriteMode.ASYNC) {
            return new AsyncLogStrategy(threadModel);
        } else {
            return new SyncLogStrategy();
        }
    }
}
