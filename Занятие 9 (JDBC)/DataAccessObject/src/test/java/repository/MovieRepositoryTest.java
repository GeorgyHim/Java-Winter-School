package repository;

import org.junit.jupiter.api.BeforeAll;

import java.io.IOException;

public class MovieRepositoryTest {
    static MovieRepository movieRepository;

    @BeforeAll
    static void init() {
        DataSourceProvider dataSourceProvider = null;
        try {
            dataSourceProvider = new DataSourceProvider();
        } catch (IOException e) {
            System.out.println("Ошибка создания провайдера: " + e.getMessage());
            throw e;
        }

        movieRepository = new MovieRepository(dataSourceProvider.getDataSource());
    }
}
