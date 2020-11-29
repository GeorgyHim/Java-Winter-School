package service;

import db.DataSourceProvider;
import model.Flight;
import org.junit.jupiter.api.BeforeAll;
import repository.FlightRepository;
import utils.FlightStatus;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FlightScheduleServiceTest {
    static List<Flight> flights = createList();
    static Flight flight1 = createFlight("Сочи", "Санкт-Петербург", LocalDateTime.of(2020, 11, 28, 10, 0));
    static Flight flight2 = createFlight("Сочи", "Санкт-Петербург", LocalDateTime.of(2020, 11, 28, 16, 0));
    static Flight flight3 = createFlight("Сочи", "Санкт-Петербург", LocalDateTime.of(2020, 11, 28, 22, 0));

    public static Flight createFlight(String cityFrom, String cityTo, LocalDateTime dateTime) {
        return new Flight(
                "Num-777", cityFrom, cityTo, "SomebodyAirlines", dateTime, dateTime.plusHours(4), FlightStatus.SCHEDULED
        );
    }

    private static List<Flight> createList() {
        List<Flight> flights = new ArrayList<>();

        flights.add(createFlight("Сочи", "Санкт-Петербург", LocalDateTime.of(2020, 11, 27, 10, 0)));
        flights.add(createFlight("Сочи", "Санкт-Петербург", LocalDateTime.of(2020, 11, 27, 22, 0)));
        flights.add(flight3);
        flights.add(flight1);
        flights.add(flight2);
        flights.add(createFlight("Сочи", "Санкт-Петербург", LocalDateTime.of(2020, 11, 29, 10, 0)));
        flights.add(createFlight("Сочи", "Москва", LocalDateTime.of(2020, 11, 28, 10, 0)));
        flights.add(createFlight("Краснодар", "Санкт-Петербург", LocalDateTime.of(2020, 11, 28, 10, 0)));
        flights.add(createFlight("Краснодар", "Москва", LocalDateTime.of(2020, 11, 28, 10, 0)));

        return flights;
    }


}
