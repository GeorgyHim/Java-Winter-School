package service;

import db.DataSourceProvider;
import model.Flight;
import org.apache.derby.jdbc.EmbeddedDataSource;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import repository.FlightRepository;
import utils.FlightStatus;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FlightScheduleServiceTest {
    static List<Flight> flights = createList();
    static Flight flight1;
    static Flight flight2;
    static Flight flight3;
    static FlightScheduleService scheduleService;
    static EmbeddedDataSource dataSource;
    static FlightRepository flightRepository;

    public static Flight createFlight(String cityFrom, String cityTo, LocalDateTime dateTime) {
        return new Flight(
                "Num-777", cityFrom, cityTo, "SomebodyAirlines", dateTime, dateTime.plusHours(4), FlightStatus.SCHEDULED
        );
    }

    private static List<Flight> createList() {
        List<Flight> flights = new ArrayList<>();
         flight1 = createFlight("Сочи", "Санкт-Петербург", LocalDateTime.of(2020, 11, 28, 10, 0));
         flight2 = createFlight("Сочи", "Санкт-Петербург", LocalDateTime.of(2020, 11, 28, 16, 0));
         flight3 = createFlight("Сочи", "Санкт-Петербург", LocalDateTime.of(2020, 11, 28, 22, 0));

        flights.add(createFlight("Сочи", "Санкт-Петербург", LocalDateTime.of(2020, 11, 27, 10, 0)));
        flights.add(createFlight("Сочи", "Санкт-Петербург", LocalDateTime.of(2020, 11, 27, 22, 0)));
        flights.add(flight3);
        flights.add(flight1);
        flights.add(flight2);

        Flight flightCancelled = createFlight("Сочи", "Санкт-Петербург", LocalDateTime.of(2020, 11, 28, 10, 0));
        flightCancelled.setStatus(FlightStatus.CANCELED);
        flights.add(flightCancelled);

        flights.add(createFlight("Сочи", "Санкт-Петербург", LocalDateTime.of(2020, 11, 29, 10, 0)));
        flights.add(createFlight("Сочи", "Москва", LocalDateTime.of(2020, 11, 28, 10, 0)));
        flights.add(createFlight("Краснодар", "Санкт-Петербург", LocalDateTime.of(2020, 11, 28, 10, 0)));
        flights.add(createFlight("Краснодар", "Москва", LocalDateTime.of(2020, 11, 28, 10, 0)));

        return flights;
    }

    @BeforeAll
    static void init() throws IOException {
        DataSourceProvider dataSourceProvider = new DataSourceProvider();
        dataSource = dataSourceProvider.getDataSource();
        flightRepository = new FlightRepository(dataSource);
        scheduleService = new FlightScheduleService(flightRepository);
        for (Flight flight : flights) {
            flightRepository.save(flight);
        }
    }

    @Test
    public void testFindFittingFlights() {
        List<Flight> fittingFlights =
                scheduleService.findFittingFlights("Сочи", "Санкт-Петербург", LocalDate.of(2020, 11, 28));

        Assertions.assertEquals(Arrays.asList(flight1, flight2, flight3), fittingFlights);
    }

    @AfterAll
    static void shutdown() throws SQLException {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {

            statement.executeUpdate("DROP TABLE " + Flight.TABLE_NAME);
        }
    }

}
