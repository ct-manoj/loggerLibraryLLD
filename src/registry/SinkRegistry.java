package registry;

import sinks.Sink;
import sinks.SinkType;

import java.util.Collection;
import java.util.EnumMap;
import java.util.Map;

public class SinkRegistry {
    private final Map<SinkType, Sink> sinkMap = new EnumMap<>(SinkType.class);

    public void registerSink(SinkType sinkType, Sink sink) {
        sinkMap.put(sinkType, sink);
    }

    public Sink getSink(SinkType sinkType) {
        return sinkMap.get(sinkType);
    }

    public boolean containsSink(SinkType sinkType) {
        return sinkMap.containsKey(sinkType);
    }

    public Collection<Sink> getAllSinks() {
        return sinkMap.values();
    }

    public void clear() {
        sinkMap.clear();
    }
}
