package model;

import java.time.LocalDateTime;

/**
 * Рейс
 */
public class Flight {

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

    // TODO: Создать нужные поля класса, а также дополнить их аннотациями для датабиндинга в XML
}
