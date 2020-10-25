package zoo.tracking;

import zoo.tracking.location.Location;

import java.util.List;

/**
 * Отслеживаемый объект.
 */
public interface Trackable {
    /**
     * Уникальный номер объект объекта отслеживания.
     * @return уникальный идентификатор
     */
    String getId();

    /**
     * Передача текущего местоположения, в текущий момент времени.
     *
     * @param x кордината по OX
     * @param y координата по OY
     */
    void updatePosition(double x, double y);

    /**
     * Журнал местоположения во времени.
     * @return журнал
     */
    List<Location> getLocations();

    /**
     * Текущее местоположение.
     *
     * @return местоположение.
     */
    Location getCurrentLocation();
}
