package model;

public enum FlightStatus {
    SCHEDULED,
    CHECK_IN,
    BOARDING,
    DEPARTING,
    EN_ROUTE,
    LANDED,
    DELAYED,
    CANCELED;

    private static FlightStatus[] values = FlightStatus.values();

    public static FlightStatus getStatus(int ordinal) {
        return values[ordinal];
    }
}
