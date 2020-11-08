package repository;

import db.DataSourceProvider;
import model.Movie;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class MovieRepositoryTest {
    static MovieRepository movieRepository;
    static DataSourceProvider dataSourceProvider = null;
    static Movie inception = new Movie("Inception", 150);
    static Movie leon = new Movie("Leon", 120);

    @BeforeAll
    static void init() throws IOException {
        try {
            dataSourceProvider = new DataSourceProvider("test");
        } catch (IOException e) {
            System.out.println("Ошибка создания провайдера: " + e.getMessage());
            throw e;
        }
        movieRepository = new MovieRepository(dataSourceProvider.getDataSource());
    }

    @Test
    public void testEmptyFindAll() {
        List<Movie> movies = movieRepository.findAll();
        Assertions.assertTrue(movies.isEmpty());
    }

    @Test
    public void testCreateNewAndFindAll() {
        movieRepository.createNew(inception);
        movieRepository.createNew(leon);
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
    }

    @AfterEach
    public void clearTable() throws SQLException {
        try (Connection connection = dataSourceProvider.getDataSource().getConnection();
             Statement statement = connection.createStatement()) {

            statement.executeUpdate("TRUNCATE TABLE " + MovieRepository.TABLE_NAME);
        }
    }

    @AfterAll
    static void shutdown() throws SQLException {
        try (Connection connection = dataSourceProvider.getDataSource().getConnection();
             Statement statement = connection.createStatement()) {

            statement.executeUpdate("DROP TABLE " + MovieRepository.TABLE_NAME);
        }
    }

}
