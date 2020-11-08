package repository;

import db.DataSourceProvider;
import model.Movie;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

public class MovieRepositoryTest {
    static MovieRepository movieRepository;

    @BeforeAll
    static void init() throws IOException {
        DataSourceProvider dataSourceProvider = null;
        try {
            dataSourceProvider = new DataSourceProvider();
        } catch (IOException e) {
            System.out.println("Ошибка создания провайдера: " + e.getMessage());
            throw e;
        }

        movieRepository = new MovieRepository(dataSourceProvider.getDataSource());
    }

    @Test
    public void testCreateNew() {
        Movie inception = new Movie("Inception", 150);
        Movie leon = new Movie("Leon", 120);
        movieRepository.createNew(inception);
        movieRepository.createNew(leon);

        List<Movie> movies = movieRepository.findAll();
        for (Movie movie : movies) {
            System.out.println(movie);
        }
        Assertions.assertEquals(2, movies.size());
        Assertions.assertTrue(movies.contains(inception));
        Assertions.assertTrue(movies.contains(leon));
    }
}
