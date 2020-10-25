package zoo.locationControlSystem.tracking;

/** Уникальный идентификатор */
public class TrackID {
    /** Счетчик идентификаторов */
    private static int id = 0;
    /** Идентификатор */
    private final int trackID;

    /**Уникальный идентификатор */
    public TrackID() {
        this.trackID = ++id;
    }

    public int getTrackID() {
        return trackID;
    }
}
