package zoo;

import zoo.animal.Animal;
import zoo.employee.Employee;
import zoo.locationControlSystem.LocationControlSystem;
import zoo.locationControlSystem.events.EventType;
import zoo.locationControlSystem.records.Pair;
import zoo.locationControlSystem.tracking.Location;
import zoo.locationControlSystem.events.Event;
import zoo.locationControlSystem.tracking.Trackable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Зоопарк.
 */
public class Zoo {
    /** Название. */
    private String title;
    /** Животные. */
    private List<Animal> animals;
    /** Сотрудники. */
    private List<Employee> employees;
    /** Система контроля местоположения */
    private LocationControlSystem lcs;
    /** Местоположение служебного входа */
    private Location serviceEntrance = new Location(-1, -1);

    /**
     * Зоопарк.
     *
     * @param title название
     */
    public Zoo(String title) {
        this.title = title;
        animals = new ArrayList<>();
        employees = new ArrayList<>();
        lcs = new LocationControlSystem(serviceEntrance);
    }

    /**Событие перемещения объекта
     *
     * @param entity животное
     * @param location новое местоположение
     * @param dateTime дата и время
     */
    public void move(Trackable entity, Location location, LocalDateTime dateTime) {
        EventType type;
        if (entity instanceof Animal) {
            type = EventType.ANIMALMOVE;
        }
        else { // Employee
            type = EventType.EMPLOYEEMOVE;
        }
        process(new Event(type, dateTime, new Object[]{entity, location}));
    }

    /**
     * Обработка событий в каждый момент времени
     * @param event событие
     */
    public void process(Event event) {
        lcs.process(event);
        // Проверка и добавление новых контактов
        if (event.getType() == EventType.ANIMALMOVE) {
            Animal animal = (Animal)event.getParams()[0];
            for (Employee employee : employees) {
                if (lcs.isContact(animal, employee) &&
                        !lcs.isNowContacting(new Pair(animal, employee))) {
                    lcs.addContact(animal, employee, event.getDateTime());
                }
            }
        }
        if (event.getType() == EventType.EMPLOYEEMOVE) {
            Employee employee = (Employee)event.getParams()[0];
            for (Animal animal : animals) {
                if (lcs.isContact(animal, employee) &&
                        !lcs.isNowContacting(new Pair(animal, employee))) {
                    lcs.addContact(animal, employee, event.getDateTime());
                }
            }
        }
    }

    /**
     * Добавляем животное.
     *
     * @param animal животное
     * @param employee опекун
     */
    public void add(Animal animal, Employee employee) {
        if (!animals.contains(animal) && employees.contains(employee)) {
            animals.add(animal);
            employee.add(animal);
        } else {
            // TODO что делать
        }
    }

    /**
     * Добавляем сотрудника.
     *
     * @param employee сотрудник
     */
    public void add(Employee employee) {
        employees.add(employee);
    }

    /**
     * Добавляем сотрудников.
     *
     * @param employees сотрудники
     */
    public void add(Employee... employees) {
        for (Employee employee : employees) {
            add(employee);
        }
    }

    /**
     * Удаляем животное.
     *
     * @param animal животное
     */
    public void remove(Animal animal) {
        for (Employee employee : employees) {
            if (employee.isCare(animal)) {
                employee.remove(animal);
            }
        }
        animals.remove(animal);
    }

    /**
     * Удаление сотрудника
     *
     * @param employee сотрудник
     */
    public void remove(Employee employee) {
        if (employees.contains(employee) && employee.getAnimals().isEmpty()) {
            employees.remove(employee);
        } else {
            // TODO учим исключения
        }
    }

    /**
     * Проверяем, контактирует ли в данный момент пара
     * @param cPair пара
     * @return  контактирует ли
     */
    public boolean isNowContacting(Pair cPair) {
        return lcs.isNowContacting(cPair);
    }


    public String getTitle() {
        return title;
    }

    public List<Animal> getAnimals() {
        return animals;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    @Override
    public String toString() {
        return "Zoo{" +
                "title='" + title + '\'' +
                ", animals=" + animals +
                ", employees=" + employees +
                '}';
    }
}
