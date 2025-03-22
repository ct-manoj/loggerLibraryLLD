package sinks;

public enum SinkType {
    FILE, DB, CONSOLE;

    public static SinkType fromString(String value) {
        try {
            return SinkType.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.err.println("Unknown sink type: " + value + ". Defaulting to CONSOLE.");
            return CONSOLE; // Fallback to CONSOLE if the type is unknown
        }
    }
}
