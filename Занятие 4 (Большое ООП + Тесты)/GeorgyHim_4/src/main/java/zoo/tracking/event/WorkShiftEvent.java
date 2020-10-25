package zoo.tracking.event;

import zoo.employee.Employee;
import zoo.tracking.incidents.WorkShift;

import java.time.LocalDateTime;

/** Событие рабочая смена сотрудника */
public class WorkShiftEvent extends TrackingEvent {
    private final WorkShift workShift;

    public WorkShiftEvent(WorkShift workShift) {
        super(workShift.getStartTime());
        this.workShift = workShift;
    }

    public WorkShift getWorkShift() {
        return workShift;
    }
}
