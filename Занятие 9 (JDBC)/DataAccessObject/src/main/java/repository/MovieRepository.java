package repository;

import model.Movie;
import org.apache.derby.jdbc.EmbeddedDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Репозиторий для доступа к таблице с данными о фильмах (Movie)
 */
public class MovieRepository {

    /** Название таблицы в БД */
    public static final String TABLE_NAME = "movie";

    /** DataSource */
    private EmbeddedDataSource dataSource;

    /**
     * Конструктор
     *
     * @param dataSource    -   DataSource
     */
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
                                        "releaseDate DATE, duration INTEGER, rating DECIMAL(3, 1), hasAwards BOOLEAN )",
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
     * Метод поиска всех фильмов в БД
     * @return - список всех фильмов
     */
    public List<Movie> findAll() {
        List<Movie> movies = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_NAME);
            while (resultSet.next()) {
                movies.add(getMovie(resultSet));
            }
        }
        catch (SQLException e) {
            System.out.println("Ошибка выполнения запроса: " + e.getMessage());
        }
        return movies;
    }

    /**
     * Вспомогательный метод для создания объекта {@link Movie} через ResultSet
     *
     * @param resultSet     -   ResultSet
     * @return              -   Объект {@link Movie}
     * @throws SQLException -   Ошибка получения значения
     */
    private Movie getMovie(ResultSet resultSet) throws SQLException {
        return new Movie(
                resultSet.getInt(1), resultSet.getString(2), resultSet.getDate(3).toLocalDate(),
                resultSet.getInt(4), resultSet.getDouble(5), resultSet.getBoolean(6)
        );
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
            statement.setDate(3, Date.valueOf(movie.getReleaseDate()));
            statement.setInt(4, movie.getDuration());
            statement.setDouble(5, movie.getRating());
            statement.setBoolean(6, movie.hasAwards());
            statement.execute();
        }
        catch (Exception e) {
            System.out.println("Ошибка выполнения запроса: " + e.getMessage());
        }
    }

    /**
     * Метод для получения объекта {@link Movie} из базы данных по указанному id
     *
     * @param id    -   идентификатор
     * @return      -   соответствующий объект {@link Movie}
     */
    public Movie read(int id) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                System.out.println("Указанный объект не найден");
                return null;
            } else
                return getMovie(resultSet);
        } catch (SQLException e) {
            System.out.println("Ошибка выполнения запроса: " + e.getMessage());
            return null;
        }
    }


    // TODO: Остальные CRUD-операции
}
