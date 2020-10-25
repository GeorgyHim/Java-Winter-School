package zoo.locationControlSystem.tracking;

/** Интерфейс "Отслеживаемый" */
public interface Trackable {
    /** Получение уникального идентификатора */
    public int getId();
    /** Перемещение в новую точку */
    public void setLocation(double x, double y);
}
