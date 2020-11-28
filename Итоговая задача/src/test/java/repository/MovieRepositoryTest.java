package repository;

import db.DataSourceProvider;
import model.Flight;
import org.apache.derby.jdbc.EmbeddedDataSource;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FlightRepositoryTest {
    static FlightRepository FlightRepository;
    static EmbeddedDataSource dataSource = null;
    static Flight inception = new Flight("Inception", 150);
    static Flight leon = new Flight("Leon", 120);

    @BeforeAll
    static void init() throws IOException {
        try {
            DataSourceProvider dataSourceProvider = new DataSourceProvider();
            dataSource = dataSourceProvider.getDataSource();
            FlightRepository = new FlightRepository(dataSource);
        } catch (IOException e) {
            System.out.println("Ошибка создания провайдера: " + e.getMessage());
            throw e;
        }
    }

    @Test
    public void testEmptyFindAll() {
        List<Flight> Flights = FlightRepository.findAll();
        Assertions.assertTrue(Flights.isEmpty());
    }

    @Test
    public void testCreateNewAndFindAll() {
        Assertions.assertTrue(FlightRepository.createNew(inception));
        Assertions.assertTrue(FlightRepository.createNew(leon));
        List<Flight> Flights = FlightRepository.findAll();
        Assertions.assertEquals(2, Flights.size());
        Assertions.assertTrue(Flights.contains(inception));
        Assertions.assertTrue(Flights.contains(leon));
    }

    @Test
    public void testRead() {
        FlightRepository.createNew(inception);
        Flight Flight = FlightRepository.read(inception.getId());
        Assertions.assertEquals(inception, Flight);

        Assertions.assertNull(FlightRepository.read(99));
    }

    @Test
    public void testUpdate() {
        FlightRepository.createNew(inception);

        inception.setRating(8.0);
        inception.setHasAwards(true);
        Map<String, Object> args = new HashMap<>();
        args.put("rating", 8.0);
        args.put("hasAwards", true);
        Flight Flight = FlightRepository.update(inception.getId(), args);
        Assertions.assertEquals(inception, Flight);

        Assertions.assertNull(FlightRepository.update(99, args));
    }

    @Test
    public void testDelete() {
        FlightRepository.createNew(inception);
        Assertions.assertTrue(FlightRepository.delete(inception.getId()));
        Assertions.assertNull(FlightRepository.read(inception.getId()));
    }

    @AfterEach
    public void clearTable() throws SQLException {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {

            statement.executeUpdate("TRUNCATE TABLE " + FlightRepository.TABLE_NAME);
        }
    }

    @AfterAll
    static void shutdown() throws SQLException {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {

            statement.executeUpdate("DROP TABLE " + FlightRepository.TABLE_NAME);
        }
    }

}
