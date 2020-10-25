package zoo.locationControlSystem.tracking;

import java.util.Objects;

/** Местоположение */
public class Location {
    /** Координата по оси Ох */
    private double x;
    /** Координата по оси Оy */
    private double y;

    /**
     * Местоположение
     * @param x Координата по оси Ох
     * @param y Координата по оси Оy
     */
    public Location(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Расстояние до другого местоположения
     * @param l другое местоположениие
     * @return расстояние
     */
    public double distance(Location l) {
        double dx = this.x - l.x();
        double dy = this.y - l.y();
        return Math.sqrt(dx*dx + dy*dy);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return Double.compare(location.x, x) == 0 &&
                Double.compare(location.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public double x() {
        return x;
    }

    public double y() {
        return y;
    }
}
