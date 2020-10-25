package zoo.locationControlSystem.records;

import zoo.locationControlSystem.tracking.Location;
import zoo.locationControlSystem.tracking.Trackable;

import java.time.LocalDateTime;

public class MoveRecord {
    /** Отслеживаемый объект */
    private Trackable entity;
    /** Новое местоположение */
    private Location location;
    /** Дата и время перемещения */
    private LocalDateTime dateTime;

    /**
     * Запись о перемещении объекта
     * @param entity объект
     * @param location новое местоположение
     * @param dateTime дата и время перемещения
     */
    public MoveRecord(Trackable entity, Location location, LocalDateTime dateTime) {
        this.entity = entity;
        this.location = location;
        this.dateTime = dateTime;
    }

    public Trackable getEntity() {
        return entity;
    }

    public Location getLocation() {
        return location;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}
