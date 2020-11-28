package repository;

import db.DataSourceProvider;
import model.Flight;
import model.FlightStatus;
import org.apache.derby.jdbc.EmbeddedDataSource;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FlightRepositoryTest {
    static FlightRepository FlightRepository;
    static EmbeddedDataSource dataSource = null;
    static Flight flight1 = createFlight("US-228", "Краснодар", "Санкт-Петербург");
    static Flight flight2 = createFlight("TT-322", "Сочи", "Москва");

    private static Flight createFlight(String number, String cityFrom, String cityTo) {
        return new Flight(
                number, cityFrom, cityTo, "SomebodyAirlines", LocalDateTime.of(2020, 11, 28, 10, 0),
                LocalDateTime.of(2020, 11, 28, 12, 30), FlightStatus.SCHEDULED
        );
    }

    @BeforeAll
    static void init() throws IOException {
        DataSourceProvider dataSourceProvider = new DataSourceProvider();
        dataSource = dataSourceProvider.getDataSource();
        FlightRepository = new FlightRepository(dataSource);
    }

    @Test
    public void testEmptyFindAll() {
        List<Flight> Flights = FlightRepository.findAll();
        Assertions.assertTrue(Flights.isEmpty());
    }

//    @Test
//    public void testCreateNewAndFindAll() {
//        Assertions.assertTrue(FlightRepository.createNew(flight1));
//        Assertions.assertTrue(FlightRepository.createNew(leon));
//        List<Flight> Flights = FlightRepository.findAll();
//        Assertions.assertEquals(2, Flights.size());
//        Assertions.assertTrue(Flights.contains(flight1));
//        Assertions.assertTrue(Flights.contains(leon));
//    }
//
//    @Test
//    public void testRead() {
//        FlightRepository.createNew(flight1);
//        Flight Flight = FlightRepository.read(flight1.getId());
//        Assertions.assertEquals(flight1, Flight);
//
//        Assertions.assertNull(FlightRepository.read(99));
//    }
//
//    @Test
//    public void testUpdate() {
//        FlightRepository.createNew(flight1);
//
//        flight1.setRating(8.0);
//        flight1.setHasAwards(true);
//        Map<String, Object> args = new HashMap<>();
//        args.put("rating", 8.0);
//        args.put("hasAwards", true);
//        Flight Flight = FlightRepository.update(flight1.getId(), args);
//        Assertions.assertEquals(flight1, Flight);
//
//        Assertions.assertNull(FlightRepository.update(99, args));
//    }
//
//    @Test
//    public void testDelete() {
//        FlightRepository.createNew(flight1);
//        Assertions.assertTrue(FlightRepository.delete(flight1.getId()));
//        Assertions.assertNull(FlightRepository.read(flight1.getId()));
//    }

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
