package factory;

import core.ThreadModel;
import core.WriteMode;
import logstrategy.AsyncLogStrategy;
import logstrategy.LogStrategy;
import logstrategy.SyncLogStrategy;

public class LogStrategyFactory {
    public static LogStrategy createStrategy(WriteMode writeMode, ThreadModel threadModel) {
        if (writeMode == WriteMode.ASYNC) {
            return new AsyncLogStrategy(threadModel);
        } else {
            return new SyncLogStrategy();
        }
    }
}
