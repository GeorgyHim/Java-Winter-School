package model;

import java.time.LocalDateTime;

/**
 * Рейс
 */
public class Flight {
    // TODO: Создать нужные поля класса, а также дополнить их аннотациями для датабиндинга в XML

    /** Название таблицы в БД */
    public static final String TABLE_NAME = "flight";

    /** Идентификатор */
    private Integer id;

    /** Номер рейса */
    private String number;

    /** Город вылета */
    private String cityFrom;

    /** Город прилета */
    private String cityTo;

    /** Авиакомпания */
    private String airline;

    /** Время вылета */
    private LocalDateTime departureTime;

    /** Время прилета */
    private LocalDateTime arrivalTime;

    /** Статус */
    private FlightStatus status;

    /** Конструктор для создания объектов в коде */
    public Flight(String number, String cityFrom, String cityTo, String airline,
                  LocalDateTime departureTime, LocalDateTime arrivalTime, FlightStatus status) {

        this(null, number, cityFrom, cityTo, airline, departureTime, arrivalTime, status);
    }

    /** Конструктор для создания объектов при загрузке из базы */
    public Flight(Integer id, String number, String cityFrom, String cityTo, String airline,
                  LocalDateTime departureTime, LocalDateTime arrivalTime, FlightStatus status) {

        this.id = id;
        this.number = number;
        this.cityFrom = cityFrom;
        this.cityTo = cityTo;
        this.airline = airline;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.status = status;
    }
}
