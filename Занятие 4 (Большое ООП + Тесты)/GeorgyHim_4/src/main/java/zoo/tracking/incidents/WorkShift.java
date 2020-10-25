package zoo.tracking.incidents;

import zoo.employee.Employee;

import java.time.LocalDateTime;

/** Рабочая смена сотрудника */
public class WorkShift {
    private final Employee employee;
    private final LocalDateTime startTime;
    private LocalDateTime finishTime;

    /**
     * Рабочая смена сотрудника
     * @param employee сотрудник
     * @param startTime начало смены
     */
    public WorkShift(Employee employee, LocalDateTime startTime) {
        this.employee = employee;
        this.startTime = startTime;
        finishTime = null;
    }

    public LocalDateTime getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(LocalDateTime finishTime) {
        this.finishTime = finishTime;
    }

    public Employee getEmployee() {
        return employee;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }
}
