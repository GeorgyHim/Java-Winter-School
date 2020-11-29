package model;

import utils.FlightStatus;

import javax.xml.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Рейс
 */
@XmlRootElement(name = "flight")
@XmlType(propOrder = {
        "cityFrom", "cityTo", "departureDate", "departureTime",
        "arrivalDate", "arrivalTime", "airline", "number", "status"
})
public class Flight {
    // TODO: Дополнить поля класа аннотациями для датабиндинга в XML

    /** Название таблицы в БД */
    public static final String TABLE_NAME = "flight";

    /** Идентификатор */
    @XmlTransient
    private Integer id;

    /** Номер рейса */
    @XmlElement
    private String number;

    /** Город вылета */
    @XmlElement
    private String cityFrom;

    /** Город прилета */
    @XmlElement
    private String cityTo;

    /** Авиакомпания */
    @XmlElement
    private String airline;

    /** Время вылета */
    @XmlTransient
    private LocalDateTime departureTime;

    /** Время прилета */
    @XmlTransient
    private LocalDateTime arrivalTime;

    /** Статус */
    @XmlElement
    private FlightStatus status;

    @XmlElement(name = "departureDate")
    public String departureDateString() {
        return departureTime.toLocalDate().toString();
    }

    @XmlElement(name = "departureTime")
    public String departureTimeString() {
        return departureTime.toLocalTime().toString();
    }

    @XmlElement(name = "arrivalDate")
    public String arrivalDateString() {
        return arrivalTime.toLocalDate().toString();
    }

    @XmlElement(name = "arrivalTime")
    public String arrivalTimeString() {
        return arrivalTime.toLocalTime().toString();
    }

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

    public Integer getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public String getCityFrom() {
        return cityFrom;
    }

    public String getCityTo() {
        return cityTo;
    }

    public String getAirline() {
        return airline;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public FlightStatus getStatus() {
        return status;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void setStatus(FlightStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flight flight = (Flight) o;
        return Objects.equals(id, flight.id) &&
                Objects.equals(number, flight.number) &&
                Objects.equals(cityFrom, flight.cityFrom) &&
                Objects.equals(cityTo, flight.cityTo) &&
                Objects.equals(airline, flight.airline) &&
                Objects.equals(departureTime, flight.departureTime) &&
                Objects.equals(arrivalTime, flight.arrivalTime) &&
                status == flight.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, cityFrom, cityTo, airline, departureTime, arrivalTime, status);
    }
}
