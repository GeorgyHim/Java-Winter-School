package zoo.locationControlSystem.events;

import zoo.animal.Animal;
import zoo.employee.Employee;
import zoo.locationControlSystem.tracking.Location;

import java.time.LocalDateTime;

/** Событие */
public class Event {
    /** Тип события */
    private final EventType type;
    /** Дата и время события */
    private final LocalDateTime dateTime;
    /** Параметры события */
    private Object[] params;

    /**Событие
     *
     * @param type тип
     * @param dateTime дата и время
     * @param params параметры
     */
    public Event(EventType type, LocalDateTime dateTime, Object[] params) {
        this.type = type;
        this.dateTime = dateTime;
        this.params = params;
    }

    public Object[] getParams() {
        return params;
    }

    public EventType getType() {
        return type;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}
