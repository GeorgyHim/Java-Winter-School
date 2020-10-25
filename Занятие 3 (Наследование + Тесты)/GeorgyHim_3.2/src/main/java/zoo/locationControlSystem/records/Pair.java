package zoo.locationControlSystem.records;

import zoo.animal.Animal;
import zoo.employee.Employee;

import java.util.Objects;

/** Пара животное - сотрудник */
public class Pair {
    /** Контактирующее животное */
    private Animal animal;
    /** Контактирующий сотрудник */
    private Employee employee;

    /**
     * Контактирующая пара животное - сотрудник
     * @param animal животное
     * @param employee сотрудник
     */
    public Pair(Animal animal, Employee employee) {
        this.animal = animal;
        this.employee = employee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair pair = (Pair) o;
        return Objects.equals(animal, pair.animal) &&
                Objects.equals(employee, pair.employee);
    }

    @Override
    public int hashCode() {
        return Objects.hash(animal, employee);
    }

    public Animal getAnimal() {
        return animal;
    }

    public Employee getEmployee() {
        return employee;
    }
}
