package org.example.Enums;

public enum ListingStage {
    Draft(0),
    Published(1),
    Closed(2);

    private final int value;
    private ListingStage(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static ListingStage fromInteger(int x) {
        return switch (x) {
            case 0 -> Draft;
            case 1 -> Published;
            case 2 -> Closed;
            default -> null;
        };
    }
}
