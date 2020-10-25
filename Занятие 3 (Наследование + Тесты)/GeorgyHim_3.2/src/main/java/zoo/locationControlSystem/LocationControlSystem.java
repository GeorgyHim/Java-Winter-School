package zoo.locationControlSystem;

import zoo.animal.Animal;
import zoo.employee.Employee;
import zoo.locationControlSystem.records.*;
import zoo.locationControlSystem.events.Event;
import zoo.locationControlSystem.events.EventType;
import zoo.locationControlSystem.tracking.Location;
import zoo.locationControlSystem.tracking.Trackable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/** Система контроля местоположения */
public class LocationControlSystem {
    /** Местоположение служебного входа */
    private Location serviceEntrance;
    /** Журнал передвижения животных */
    private List<MoveRecord> animalMoves;
    /** Журнал передвижения сотрудников */
    private List<MoveRecord> employeeMoves;
    /** Журнал контактов */
    private List<ContactRecord> contacts;
    /** Журнал контактирующих животных и сотрудников на данный момент */
    private List<Pair> nowContacting;
    /** Журнал прихода и ухода сотрудников */
    private List <ArrivalDepartureRecord> arrivalDepartureJournal;

    /**
     * Система контроля местоположения
     * @param serviceEntrance местоположение служебного входа
     */
    public LocationControlSystem(Location serviceEntrance) {
        this.serviceEntrance = serviceEntrance;
        animalMoves = new ArrayList<MoveRecord>();
        employeeMoves = new ArrayList<MoveRecord>();
        contacts = new ArrayList<ContactRecord>();
        nowContacting = new ArrayList<Pair>();
        arrivalDepartureJournal = new ArrayList<ArrivalDepartureRecord>();
    }

    /**
     * Добавление записи о приходе или уходе сотрудника
     * @param employee сотрудник
     * @param arrived пришел или ушел
     * @param dateTime дата и время события
     */
    public void addADRecord(Employee employee, LocalDateTime dateTime, boolean arrived) {
        arrivalDepartureJournal.add(new ArrivalDepartureRecord(employee, arrived, dateTime));
    }

    /**
     * Движение объекта
     * @param entity объект
     * @param location новая локация
     * @param dateTime дата и время события
     */
    public void move(Trackable entity, Location location, LocalDateTime dateTime) {
        entity.setLocation(location.x(), location.y());
        if (entity instanceof Animal) {
            animalMoves.add(new MoveRecord(entity, location, dateTime));
        }
        else { // Employee
            employeeMoves.add(new MoveRecord(entity, location, dateTime));
            // Также будем отслеживать несанкцианированный выход с территории
            if (location.equals(serviceEntrance)) {
                process(new Event(EventType.EMPLOYEEDEPART, dateTime, new Object[]{entity}));
            }
        }
    }

    /**
     * Определение контакта животного и сотрудника
     * @param animal животное
     * @param employee сотрудник
     * @return есть ли контакт
     */
    public boolean isContact(Animal animal, Employee employee) {
        return (animal.getLocation() != null) && (employee.getLocation() != null) &&
                (animal.getLocation().distance(employee.getLocation()) < 3);
    }

    /**
     * Добавление контакта в общий список контактов и в список контактирующих в данный момент
     * @param animal животное
     * @param employee сотрудник
     * @param beginDateTime дата и время начала контакта
     */
    public void addContact(Animal animal, Employee employee, LocalDateTime beginDateTime) {
        contacts.add(new ContactRecord(animal, employee, beginDateTime));
        nowContacting.add(new Pair(animal, employee));
    }

    /**
     * Обновляем список контактирующих в данный момент
     * @param dateTime текущая дата и время
     */
    public void updateNowContacting(LocalDateTime dateTime) {
        List<Pair> newList = new ArrayList<Pair>();
        for (Pair cPair : nowContacting) {
            if (!isContact(cPair.getAnimal(), cPair.getEmployee())) {
                // Контакт завершился после последнего обновления местоположений, в указанное время
                for (ContactRecord cRecord : contacts) {
                    if (cRecord.getAnimal() == cPair.getAnimal() && cRecord.getEmployee() == cPair.getEmployee() &&
                            cRecord.getEndDateTime() == null) {
                        // Устанавливаем дату и время окончания контакта
                        cRecord.setEndDateTime(dateTime);
                    }
                }
            }
            else {
                newList.add(cPair);
            }
        }
        nowContacting = newList;
    }

    /**
     * Проверяем, контактирует ли в данный момент пара
     * @param cPair пара
     * @return  контактирует ли
     */
    public boolean isNowContacting(Pair cPair) {
        return nowContacting.contains(cPair);
    }

    /**
     * Обработка событий в каждый момент времени
     * @param event событие
     */
    public void process(Event event) {
        if (event.getType() == EventType.ANIMALMOVE || event.getType() == EventType.EMPLOYEEMOVE) {
            move((Trackable)event.getParams()[0], (Location)event.getParams()[1],
                    event.getDateTime());
        }
        else { // EMPLOYEEARRIVE или EMPLOYEEDEPART
            addADRecord((Employee) event.getParams()[0], event.getDateTime(),
                    event.getType() == EventType.EMPLOYEEARRIVE);
        }
        // Обновляем список текущих контактов
        updateNowContacting(event.getDateTime());
    }

    public List<MoveRecord> getAnimalMoves() {
        return animalMoves;
    }

    public List<MoveRecord> getEmployeeMoves() {
        return employeeMoves;
    }

    public List<Pair> getNowContacting() {
        return nowContacting;
    }

    public List<ArrivalDepartureRecord> getArrivalDepartureJournal() {
        return arrivalDepartureJournal;
    }

    public List<ContactRecord> getContacts() {
        return contacts;
    }
}
