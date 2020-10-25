package zoo.animal;

import zoo.locationControlSystem.tracking.Location;
import zoo.locationControlSystem.tracking.TrackID;
import zoo.locationControlSystem.tracking.Trackable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Животное.
 */
public class Animal implements Trackable {
    /** Название. */
    private String name;
    /** Дата рождения. */
    private LocalDate dateBirth;
    /** Журнал болезней. */
    private List<IllnessRecord> illnessRecords;
    /** Уникальный идентификатор */
    private final TrackID id;
    /** Местоположение */
    private Location location;

    /**
     * Животное.
     *
     * @param name название
     * @param dateBirth дата рождения
     */
    public Animal(String name, LocalDate dateBirth) {
        this.name = name;
        this.dateBirth = dateBirth;
        this.id = new TrackID();
        illnessRecords = new ArrayList<>();
    }

    /**
     * Добавляем запись в журнал болезней.
     *
     * @param illnessRecord запись о болезни.
     */
    public void add(IllnessRecord illnessRecord) {
        illnessRecords.add(illnessRecord);
    }

    public void setLocation(double x, double y) {
        this.location = new Location(x, y);
    }

    public int getId() {
        return id.getTrackID();
    }

    public String getName() {
        return name;
    }

    public LocalDate getDateBirth() {
        return dateBirth;
    }

    public List<IllnessRecord> getIllnessRecords() {
        return illnessRecords;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.setLocation(location.x(), location.y());
    }

    @Override
    public String toString() {
        return "Animal{" +
                "name='" + name + '\'' +
                ", dateBirth=" + dateBirth +
                ", illnessRecords=" + illnessRecords +
                '}';
    }
}
