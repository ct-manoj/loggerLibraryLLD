package core;

public enum ThreadModel {
    SINGLE, MULTI;

    public static ThreadModel fromString(String value) {
        try {
            return ThreadModel.valueOf(value.toUpperCase());
        } catch (Exception e) {
            return SINGLE; // default value
        }
    }
}