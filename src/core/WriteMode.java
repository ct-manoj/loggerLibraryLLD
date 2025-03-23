package core;

public enum WriteMode {
    SYNC, ASYNC;

    public static WriteMode fromString(String value) {
        try {
            return WriteMode.valueOf(value.toUpperCase());
        } catch (Exception e) {
            return SYNC; // default value
        }
    }
}