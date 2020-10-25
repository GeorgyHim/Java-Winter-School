package zoo.employee;

import zoo.animal.Animal;
import zoo.locationControlSystem.tracking.Location;
import zoo.locationControlSystem.tracking.TrackID;
import zoo.locationControlSystem.tracking.Trackable;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Сотрудник.
 */
public class Employee implements Trackable {
    /** Имя. */
    private String name;
    /** Дата рождения. */
    private LocalDate dateOfBirth;
    /** Подопечные животные. */
    private Set<Animal> animals;
    /** Уникальный идентификатор */
    private final TrackID id;
    /** Местоположение */
    private Location location;

    /**
     * Сотрудник.
     *
     * @param name ФИО
     * @param dateOfBirth дата рождения
     */
    public Employee(String name, LocalDate dateOfBirth) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.id = new TrackID();
        animals = new HashSet<>();
    }

    /**
     * Поручить сотруднику животное.
     *
     * @param animal животное
     */
    public void add(Animal animal) {
        animals.add(animal);
    }

    /**
     * Снимаем ответственность за животное.
     *
     * @param animal животное
     */
    public void remove(Animal animal) {
        animals.remove(animal);
    }

    /**
     * Находится ли животное на попичении?
     *
     * @param animal животное
     * @return true, если находится
     */
    public boolean isCare(Animal animal) {
        return animals.contains(animal);
    }

    public void setLocation(double x, double y) {
        this.location = new Location(x, y);
    }

    public int getId() {
        return id.getTrackID();
    }

    public String getName() {
        return name;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public Set<Animal> getAnimals() {
        return animals;
    }

    public void setLocation(Location location) {
        this.setLocation(location.x(), location.y());
    }

    public Location getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", animals=" + animals +
                '}';
    }
}
