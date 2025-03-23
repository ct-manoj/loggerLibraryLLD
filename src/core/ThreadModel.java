package core;

public enum ThreadModel {
    SINGLE, MULTI;

    public static ThreadModel fromValue(String value) {
        try {
            return ThreadModel.valueOf(value.toUpperCase());
        } catch (Exception e) {
            return SINGLE;
        }
    }
}