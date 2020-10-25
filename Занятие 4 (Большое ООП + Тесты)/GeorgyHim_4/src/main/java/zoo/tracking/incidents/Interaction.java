package zoo.tracking.incidents;


import zoo.tracking.Trackable;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Взаимодействие между двумя отслеживаемыми объектами.
 */
public class Interaction {
    private final Trackable a;
    private final Trackable b;
    private final LocalDateTime startTime;
    private LocalDateTime finishTime;

    /**
     * Взаимодействие между двумя отслеживаемыми объектами.
     *
     * @param a первый объект
     * @param b второй объект
     * @param startTime время начала взаимодействия
     */
    public Interaction(Trackable a, Trackable b, LocalDateTime startTime) {
        this.a = a;
        this.b = b;
        this.startTime = startTime;
        finishTime = null;
    }

    /**
     * Находим продолжительность взаимодействия
     * @return Находим продолжительность взаимодействия
     */
    public Duration getDuration() {
        if (finishTime != null) {
            return Duration.between(startTime, finishTime);
        }
        else {
            return Duration.between(startTime, LocalDateTime.now());
        }
    }

    /**
     * Время окончания взаимодействия.
     *
     * @return время, null если объекты еще взаимодействуют
     */
    public LocalDateTime getFinishTime() {
        return finishTime;
    }

    /**
     * Устанавливаем время окончания взаимодействия.
     *
     * @param finishTime время окончания
     */
    public void setFinishTime(LocalDateTime finishTime) {
        this.finishTime = finishTime;
    }

    public Trackable getA() {
        return a;
    }

    public Trackable getB() {
        return b;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }
}
