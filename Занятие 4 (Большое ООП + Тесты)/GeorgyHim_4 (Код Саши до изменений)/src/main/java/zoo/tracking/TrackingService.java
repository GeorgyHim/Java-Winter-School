package zoo.tracking;

import zoo.tracking.event.TrackingEvent;
import zoo.tracking.finder.EmployeeAndAnimalInteractionEventFinder;
import zoo.tracking.finder.EventFinder;
import zoo.tracking.location.Position;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
        eventFinders.add(new EmployeeAndAnimalInteractionEventFinder(3.0));
    }


    //-------------------------Новый функционал----------------------------------------------------------------------

    /**
     * Определяет, находится ли позиция внутри зоопарка
     * Считаем границу зоопарка окружностью радиуса radius
     * @param p позиция
     * @param radius радиус
     * @return True, если внутри
     */
    private boolean insideZoo(Position p, double radius) {
        return Math.sqrt(Math.pow(p.x, 2) + Math.pow(p.y, 2)) < radius;
    }























    //-------------------------Новый функционал закончен--------------------------------------------------------------

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
