package zoo.tracking;

import javafx.util.Pair;
import zoo.employee.Employee;
import zoo.tracking.event.EmployeeAnimalInteractionEvent;
import zoo.tracking.event.TrackingEvent;
import zoo.tracking.event.WorkShiftEvent;
import zoo.tracking.finder.*;
import zoo.tracking.incidents.Interaction;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Сервис отслеживания {@link Trackable}.
 */
public class TrackingService {
    /** Отслеживаемые объекты. ид -> объект. */
    private final Map<String, Trackable> trackable;
    /** Журнал событий. */
    private final List<TrackingEvent> events;
    /** Анализаторы событий. */
    private final List<EventFinder> eventFinders;

    public TrackingService() {
        trackable = new HashMap<>();
        events = new ArrayList<>();
        eventFinders = new ArrayList<>();
        // Добавляем отслеживание событий на сближение сотрудника и животного на 3 метра
        eventFinders.add(new EmployeeAnimalInteractionEventFinder(3.0));
        // Добавляем отслеживание событий на сближение двух сотрудников на 2 метра
        eventFinders.add(new EmployeeEmployeeInteractionEventFinder(2.0));
        // Добавляем отслеживание событий прихода-ухода сотрудника, радиус границы зоопарка - 200 метров
        eventFinders.add(new WorkShiftEventFinder(200.0));
    }

    /**
     * Суммарное время в секундах, проведенное сотрудником с его подопечными за все время работы
     * @param employee сотрудник
     * @return суммарное время с подопечными
     */
    public long generalTimeWithWards(Employee employee) {
        long generalTime = 0;
        for (TrackingEvent event : events) {
            if (event instanceof EmployeeAnimalInteractionEvent) {
                final boolean isEmployee = ((EmployeeAnimalInteractionEvent) event).getEmployee() == employee;
                final boolean InCare = employee.isCare(((EmployeeAnimalInteractionEvent) event).getAnimal());
                if(isEmployee && InCare) {
                    Interaction interaction = ((EmployeeAnimalInteractionEvent) event).getInteraction();
                    generalTime += interaction.getDuration().getSeconds();
                }
            }
        }
        return generalTime;
    }

    /**
     * Сколько раз сотрудник выводил животных из зоопарка
     * @param employee сотрудник
     * @return сколько раз выводил
     */
    public int countBringingOut(Employee employee) {
        // Количество выводов
        int countOut = 0;

        List<Pair<LocalDateTime, String>> times = createTimeList(employee);

        times.sort(Comparator.comparing(Pair::getKey));
        int countActiveInteractions = 0;
        for (Pair <LocalDateTime, String>  pair : times) {
            if (pair.getValue() == "AB") { // Animal Begin
                countActiveInteractions++;
            }
            if (pair.getValue() == "AF") { // Animal Finish
                countActiveInteractions--;
            }
            if (pair.getValue() == "EF") { // Employee Finish
                if (countActiveInteractions > 0) { // Взаимодействие с животным во время выхода сотрудника
                    countOut++;
                }
            }
        }
        return countOut;
    }

    /**
     * Создадим список временных отметок начала и конца событий
     * @param employee сотрудник
     * @return список временных отметок
     */
    private List<Pair<LocalDateTime, String>> createTimeList(Employee employee) {
        //За тип события отвечает String
        List <Pair <LocalDateTime, String> > times = new ArrayList<>();

        for (TrackingEvent event : events) {
            if (event instanceof WorkShiftEvent) {
                final boolean isEmployee = ((WorkShiftEvent) event).getWorkShift().getEmployee() == employee;
                if (isEmployee) {
                   times.add(new Pair(event.getTime(), "EB"));
                   if (((WorkShiftEvent) event).getWorkShift().getFinishTime() != null) {
                       times.add(new Pair(((WorkShiftEvent) event).getWorkShift().getFinishTime(), "EF"));
                   }
                }
            }
            if (event instanceof EmployeeAnimalInteractionEvent) {
                final boolean isEmployee = ((EmployeeAnimalInteractionEvent) event).getEmployee() == employee;
                if (isEmployee) {
                    times.add(new Pair(event.getTime(), "AB"));
                    if (((EmployeeAnimalInteractionEvent) event).getInteraction().getFinishTime() != null) {
                        times.add(new Pair(((EmployeeAnimalInteractionEvent) event).
                                getInteraction().getFinishTime(), "AF"));
                    }
                }
            }
        }
        return times;
    }


    /**
     * Добавляем новый объект для отслеживания.
     *
     * @param trackable новый объект
     */
    public void add(Trackable trackable) {
        this.trackable.put(trackable.getId(), trackable);
    }

    /**
     * Обновление местоположения - Пришли данные с GPS-датчика(обработанные).
     *
     * @param id идентификатор отслеживаемого объекта
     * @param x Х
     * @param y Y
     */
    public void update(String id, double x, double y) {
        if (!trackable.containsKey(id)) {
            return;
        }
        // Обновляем позицию
        trackable.get(id).updatePosition(x, y);
        // Проверяем появление новых событий для каждого типа событий
        for (EventFinder eventFinder : eventFinders) {
            events.addAll(eventFinder.findNext(trackable.get(id), trackable));
        }
    }

    /**
     * Снимаем слежение с объекта.
     *
     * @param trackable объект
     */
    public void remove(Trackable trackable) {
        this.trackable.remove(trackable);
    }

    public Set<Trackable> getTrackable() {
        return new HashSet<>(trackable.values());
    }

    public List<TrackingEvent> getEvents() {
        return events;
    }
}
