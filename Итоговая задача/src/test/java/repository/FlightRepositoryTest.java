package repository;

import db.DataSourceProvider;
import model.Flight;
import utils.FlightStatus;
import org.apache.derby.jdbc.EmbeddedDataSource;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.List;

public class FlightRepositoryTest {
    static FlightRepository flightRepository;
    static EmbeddedDataSource dataSource = null;
    static Flight flight1 = createFlight("US-228", "Краснодар", "Санкт-Петербург");
    static Flight flight2 = createFlight("TT-322", "Сочи", "Москва");

    public static Flight createFlight(String number, String cityFrom, String cityTo) {
        return new Flight(
                number, cityFrom, cityTo, "SomebodyAirlines", LocalDateTime.of(2020, 11, 28, 10, 0),
                LocalDateTime.of(2020, 11, 28, 12, 30), FlightStatus.SCHEDULED
        );
    }

    @BeforeAll
    static void init() throws IOException {
        DataSourceProvider dataSourceProvider = new DataSourceProvider();
        dataSource = dataSourceProvider.getDataSource();
        flightRepository = new FlightRepository(dataSource);
    }

    @Test
    public void testEmptyFindAll() {
        List<Flight> Flights = flightRepository.findAll();
        Assertions.assertTrue(Flights.isEmpty());
    }

    @Test
    public void testCreateNewAndFindAll() {
        Assertions.assertTrue(flightRepository.createNew(flight1));
        Assertions.assertTrue(flightRepository.createNew(flight2));
        List<Flight> flights = flightRepository.findAll();
        Assertions.assertEquals(2, flights.size());
        Assertions.assertTrue(flights.contains(flight1));
        Assertions.assertTrue(flights.contains(flight2));
    }

    @Test
    public void testGetById() {
        flightRepository.createNew(flight1);
        Flight flight = flightRepository.getById(flight1.getId());
        Assertions.assertEquals(flight1, flight);

        Assertions.assertNull(flightRepository.getById(99));
    }

    @Test
    public void testUpdate() {
        flightRepository.createNew(flight1);

        flight1.setArrivalTime(LocalDateTime.of(2020, 11, 28, 12, 0));
        flight1.setDepartureTime(LocalDateTime.of(2020, 11, 28, 14, 30));
        flight1.setStatus(FlightStatus.DELAYED);
        Assertions.assertTrue(flightRepository.update(flight1));

        Flight flight = flightRepository.getById(flight1.getId());
        Assertions.assertEquals(flight1, flight);
    }

    @Test
    public void testDelete() {
        flightRepository.createNew(flight1);
        flightRepository.createNew(flight2);
        Assertions.assertTrue(flightRepository.delete(flight1));
        Assertions.assertNull(flightRepository.getById(flight1.getId()));

        Assertions.assertTrue(flightRepository.deleteById(flight2.getId()));
        Assertions.assertNull(flightRepository.getById(flight2.getId()));
    }

    @AfterEach
    public void clearTable() throws SQLException {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {

            statement.executeUpdate("TRUNCATE TABLE " + Flight.TABLE_NAME);
        }
    }

    @AfterAll
    static void shutdown() throws SQLException {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {

            statement.executeUpdate("DROP TABLE " + Flight.TABLE_NAME);
        }
    }

}
