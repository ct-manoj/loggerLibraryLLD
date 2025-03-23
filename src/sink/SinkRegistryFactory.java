package sink;

import config.LoggerConfig;

public class SinkRegistryFactory {
    private SinkRegistryFactory() {}

    public static SinkRegistry createRegistry(LoggerConfig config) {
        SinkRegistry registry = new SinkRegistry();
        Sink defaultSink = SinkFactory.createSink(config, config.getDefaultSinkType());
        registry.registerSink(config.getDefaultSinkType(), defaultSink);

        for (SinkType sinkType : config.getLevelSinkMapping().values()) {
            if (!registry.containsSink(sinkType)) {
                Sink sink = SinkFactory.createSink(config, sinkType);
                registry.registerSink(sinkType, sink);
            }
        }
        return registry;
    }
}
