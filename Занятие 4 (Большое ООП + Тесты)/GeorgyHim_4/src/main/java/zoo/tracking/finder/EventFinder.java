package zoo.tracking.finder;

import zoo.tracking.Trackable;
import zoo.tracking.event.TrackingEvent;

import java.util.List;
import java.util.Map;

/**
 * Поиск событий определенного типа.
 */
public interface EventFinder {
    /**
     * Ищет события для отслеживаемого объекта, у которого изменились координаты.
     *
     * @param updatedTracked обновленый отслеживаемый объект
     * @param trackable все отслеживаемые объекты
     * @return список новых событий, если они обнаружены
     */
    List<? extends TrackingEvent> findNext(final Trackable updatedTracked, final Map<String, Trackable> trackable);
}
