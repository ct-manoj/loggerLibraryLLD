package core;

public enum WriteMode {
    SYNC, ASYNC;

    public static WriteMode fromValue(String value) {
        try {
            return WriteMode.valueOf(value.toUpperCase());
        } catch (Exception e) {
            return SYNC;
        }
    }
}