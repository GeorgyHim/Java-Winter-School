package zoo.locationControlSystem.records;

import zoo.employee.Employee;

import java.time.LocalDateTime;

/** Запись о приходе или уходе */
public class ArrivalDepartureRecord {
    /* Сотрудник */
    private final Employee employee;
    /* Пришел или ушел */
    private final boolean arrived;
    /* Дата и время прихода или ухода */
    private final LocalDateTime dateTime;

    /**
     * Запись о приходе или уходе
     * @param employee сотрудник
     * @param arrived пришел или ушел
     * @param dateTime дата и время
     */
    public ArrivalDepartureRecord(Employee employee, boolean arrived, LocalDateTime dateTime) {
        this.employee = employee;
        this.arrived = arrived;
        this.dateTime = dateTime;
    }

    public Employee getEmployee() {
        return employee;
    }

    public boolean isArrived() {
        return arrived;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}
