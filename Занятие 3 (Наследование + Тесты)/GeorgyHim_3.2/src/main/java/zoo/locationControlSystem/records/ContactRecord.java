package zoo.locationControlSystem.records;

import zoo.animal.Animal;
import zoo.employee.Employee;

import java.time.Duration;
import java.time.LocalDateTime;

/** Запись о контакте */
public class ContactRecord extends Pair {
    /** Дата и время начала контакта*/
    private final LocalDateTime beginDateTime;
    /** Дата и время окончания контакта */
    private LocalDateTime endDateTime;

    /**
     * Запись о контакте
     * @param animal животное
     * @param employee сотрудник
     * @param beginDateTime дата и время начала контакта
     */
    public ContactRecord(Animal animal, Employee employee, LocalDateTime beginDateTime) {
        super(animal, employee);
        this.beginDateTime = beginDateTime;
    }

    /** Продолжительность контакта */
    public Duration contactDuration() {
        return Duration.between(beginDateTime, endDateTime);
    }

    public LocalDateTime getBeginDateTime() {
        return beginDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }
}
