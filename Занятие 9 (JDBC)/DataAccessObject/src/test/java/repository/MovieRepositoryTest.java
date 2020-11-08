package repository;

import db.DataSourceProvider;
import model.Movie;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class MovieRepositoryTest {
    static MovieRepository movieRepository;
    static DataSourceProvider dataSourceProvider = null;

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
    public void testCreateNewAndFindAll() {
        Movie inception = new Movie("Inception", 150);
        Movie leon = new Movie("Leon", 120);
        movieRepository.createNew(inception);
        movieRepository.createNew(leon);

        List<Movie> movies = movieRepository.findAll();
        Assertions.assertEquals(2, movies.size());
        Assertions.assertTrue(movies.contains(inception));
        Assertions.assertTrue(movies.contains(leon));
    }

    @AfterAll
    static void shutdown() throws SQLException {
        try (Connection connection = dataSourceProvider.getDataSource().getConnection();
             Statement statement = connection.createStatement()) {

            statement.executeUpdate("DROP TABLE " + MovieRepository.TABLE_NAME);
        }
    }

}
