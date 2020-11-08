package repository;

import model.Movie;
import org.apache.derby.jdbc.EmbeddedDataSource;

import java.sql.*;

/**
 * Репозиторий для доступа к таблице с данными о фильмах (Movie)
 */
public class MovieRepository {

    /** Название таблицы в БД */
    private static final String TABLE_NAME = "movie";

    /** DataSource */
    private EmbeddedDataSource dataSource;

    public MovieRepository(EmbeddedDataSource dataSource) {
        this.dataSource = dataSource;
        initTable();
    }

    /**
     * Метод инициализации таблицы базы данных
     */
    private void initTable() {
        System.out.println(String.format("Start initializing %s table", TABLE_NAME));
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement() ) {

            if (checkTableExists(connection.getMetaData()))
                System.out.println("Table has already been initialized");
            else {
                statement.executeUpdate(
                        String.format("CREATE TABLE %s (id INTEGER PRIMARY KEY, title VARCHAR(255), " +
                                        "releaseDate DATE, duration INTEGER, rating DOUBLE(3, 1), hasAwards BOOL)",
                                TABLE_NAME)
                );
                System.out.println("Table was successfully initialized");
            }
        }
        catch (SQLException e) {
            System.out.println("Error occured during table initializing: " + e.getMessage());
        }
        finally {
            System.out.println("==============================================");
        }
    }

    /**
     * Метод проверки существования таблицы в базе
     *
     * @param metaData  -   метаданные базы
     * @return          -   Существует ли таблица TABLE_NAME
     * @throws SQLException - Ошибка получения таблиц
     */
    private boolean checkTableExists(DatabaseMetaData metaData) throws SQLException {
        ResultSet resultSet = metaData.getTables(
                null, null, TABLE_NAME.toUpperCase(), new String[]{"TABLE"}
        );
        return resultSet.next();
    }

    /**
     * Метод создания записи о новом фильме в БД
     *
     * @param movie   -   Фильм
     */
    public void createNew(Movie movie) {
        String query = "INSERT INTO " + TABLE_NAME + " VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, movie.getId());
            statement.setString(2, movie.getTitle());
            statement.setObject(3, movie.getReleaseDate());
            statement.setInt(4, movie.getDuration());
            statement.setDouble(5, movie.getRating());
            statement.setBoolean(6, movie.hasAwards());
            statement.execute();
        }
        catch (Exception e) {
            System.out.println("Ошибка выполнения запроса: " + e.getMessage());
        }
    }
}
