package repository;

import db.DataSourceProvider;
import model.Movie;
import org.apache.derby.jdbc.EmbeddedDataSource;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MovieRepositoryTest {
    static MovieRepository movieRepository;
    static EmbeddedDataSource dataSource = null;
    static Movie inception = new Movie("Inception", 150);
    static Movie leon = new Movie("Leon", 120);

    @BeforeAll
    static void init() throws IOException {
        try {
            DataSourceProvider dataSourceProvider = new DataSourceProvider("test");
            dataSource = dataSourceProvider.getDataSource();
        } catch (IOException e) {
            System.out.println("Ошибка создания провайдера: " + e.getMessage());
            throw e;
        }
        movieRepository = new MovieRepository(dataSource);
    }

    @Test
    public void testEmptyFindAll() {
        List<Movie> movies = movieRepository.findAll();
        Assertions.assertTrue(movies.isEmpty());
    }

    @Test
    public void testCreateNewAndFindAll() {
        Assertions.assertTrue(movieRepository.createNew(inception));
        Assertions.assertTrue(movieRepository.createNew(leon));
        List<Movie> movies = movieRepository.findAll();
        Assertions.assertEquals(2, movies.size());
        Assertions.assertTrue(movies.contains(inception));
        Assertions.assertTrue(movies.contains(leon));
    }

    @Test
    public void testRead() {
        movieRepository.createNew(inception);
        Movie movie = movieRepository.read(inception.getId());
        Assertions.assertEquals(inception, movie);

        Assertions.assertNull(movieRepository.read(99));
    }

    @Test
    public void testUpdate() {
        movieRepository.createNew(inception);

        inception.setRating(8.0);
        inception.setHasAwards(true);
        Map<String, Object> args = new HashMap<>();
        args.put("rating", 8.0);
        args.put("hasAwards", true);
        Movie movie = movieRepository.update(inception.getId(), args);
        Assertions.assertEquals(inception, movie);

        Assertions.assertNull(movieRepository.update(99, args));
    }

    @AfterEach
    public void clearTable() throws SQLException {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {

            statement.executeUpdate("TRUNCATE TABLE " + MovieRepository.TABLE_NAME);
        }
    }

    @AfterAll
    static void shutdown() throws SQLException {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {

            statement.executeUpdate("DROP TABLE " + MovieRepository.TABLE_NAME);
        }
    }

}
