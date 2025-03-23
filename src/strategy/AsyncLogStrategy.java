package strategy;

import core.LogMessage;
import core.ThreadModel;
import sinks.Sink;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class AsyncLogStrategy implements LogStrategy {
    private final ExecutorService executor;

    public AsyncLogStrategy(ThreadModel threadModel) {
        if (threadModel == ThreadModel.SINGLE) {
            this.executor = Executors.newSingleThreadExecutor();
        } else {
            this.executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        }
    }

    @Override
    public void log(LogMessage message, Sink sink) {
        executor.submit(() -> {
            try {
                sink.log(message);
            } catch (Exception e) {
                System.err.println("Error in asynchronous log task: " + e.getMessage());
            }
        });
    }

    @Override
    public void close() {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
