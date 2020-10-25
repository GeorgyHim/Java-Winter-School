package zoo.tracking.finder;

import zoo.employee.Employee;
import zoo.tracking.Trackable;
import zoo.tracking.event.WorkShiftEvent;
import zoo.tracking.incidents.Interaction;
import zoo.tracking.incidents.WorkShift;
import zoo.tracking.location.Position;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Анализатор события {@link WorkShiftEvent}
 */
public class WorkShiftEventFinder implements EventFinder {
    /** Незавершенная смена сотрудников. id -> смена (всегда не более чем одна) */
    private final Map<String, WorkShift> workShifts = new HashMap<>();
    /** Радиус окружности границы зоопарка */
    private double radius;


    /**
     * Анализатор события {@link WorkShiftEvent}
     * @param radius радиус окружности границы зоопарка
     */
    public WorkShiftEventFinder(double radius) {
        this.radius = radius;
    }

    /**
     * Ищет события для отслеживаемого объекта, у которого изменились координаты.
     *
     * @param updatedTrackable обновленый отслеживаемый объект
     * @param trackable      все отслеживаемые объекты
     * @return список новых событий, если они обнаружены
     */
    @Override
    public List<WorkShiftEvent> findNext(Trackable updatedTrackable, Map<String, Trackable> trackable) {
        final List<WorkShiftEvent> newEvents = new ArrayList<>();
        final WorkShiftEvent event = findWorkShiftEvent(updatedTrackable);
        if (event != null) {
            newEvents.add(event);
        }
        return newEvents;
    }


    /**
     * Ищет событие рабочей смены сотрудника
     * @param updatedTrackable
     * @return
     */
    private WorkShiftEvent findWorkShiftEvent(Trackable updatedTrackable) {
        if (! (updatedTrackable instanceof Employee)) {
            return null;
        }

        final boolean isInside = insideZoo(updatedTrackable.getCurrentLocation().position);

        if (isInside) { // Сотрудник внутри
            if (existsNotCompletedWorkShift(updatedTrackable)) { // и смена продолжается
                return null; // то ничего не делаем
            }

            // Иначе генерируем новую смену
            final WorkShift workShift = new WorkShift((Employee) updatedTrackable, LocalDateTime.now());
            // Добавляем взаимодействие в список незавершенных для отслеживания завершения
            addWorkShift(workShift);
            // Возвращаем событие появления взаимодействия
            return new WorkShiftEvent(workShift);


        } else { // Сотрудник снаружи
            if (! existsNotCompletedWorkShift(updatedTrackable)) { // и смены нет
                return null; // ничего не делаем
            }

            // Иначе смена была, но завершилась
            // Нужно проставить время завершения в созданной ранее смене
            // И удалить её с отслеживания (причем вместе с сотрудником)
            final WorkShift workShift = workShifts.get(updatedTrackable.getId());
            workShift.setFinishTime(LocalDateTime.now());
            removeWorkShift(workShift);
            return null;
        }
    }

    private void removeWorkShift(WorkShift workShift) {
        workShifts.remove(workShift.getEmployee().getId());
    }

    /**
     * Добавление незавершенной смены для отслеживания
     * @param workShift смена
     */
    private void addWorkShift(WorkShift workShift) {
        workShifts.put(workShift.getEmployee().getId(), workShift);
    }

    /**
     * Проверка на существование незавершенной смены сотрудника
     * @param a сотрудник
     * @return true если существует
     */
    private boolean existsNotCompletedWorkShift(Trackable a) {
        return workShifts.containsKey(a.getId());
    }


    /**
     * Определяет, находится ли позиция внутри зоопарка
     * Считаем границу зоопарка окружностью радиуса radius и центром в точке (0, 0)
     * @param p позиция
     * @return True, если внутри
     */
    private boolean insideZoo(Position p) {
        return Math.sqrt(Math.pow(p.x, 2) + Math.pow(p.y, 2)) < radius;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }
}
