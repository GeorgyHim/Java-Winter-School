package repository;

import model.Flight;
import model.FlightStatus;
import org.apache.derby.jdbc.EmbeddedDataSource;

import java.sql.*;

/**
 * Репозиторийдля доступа к таблице с данными о рейсах ({@link model.Flight})
 */
public class FlightRepository {

    /** DataSource */
    private EmbeddedDataSource dataSource;

    /**
     * Конструктор
     *
     * @param dataSource - DataSource
     */
    public FlightRepository(EmbeddedDataSource dataSource) {
        this.dataSource = dataSource;
        initTable();
    }

    /**
     * Метод инициализации таблицы базы данных
     */
    private void initTable() {
        System.out.println(String.format("Start initializing %s table", Flight.TABLE_NAME));
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement() ) {

            if (checkTableExists(connection.getMetaData()))
                System.out.println("Table has already been initialized");
            else {
                statement.executeUpdate(String.format(
                        "CREATE TABLE %s (" +
                        "id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY(Start with 1, Increment by 1), " +
                        "number VARCHAR(10), " +
                        "cityFrom VARCHAR(100) NOT NULL, " +
                        "cityTo VARCHAR(100) NOT NULL, " +
                        "airline VARCHAR(200), " +
                        "departureTime TIMESTAMP , " +
                        "arrivalTime TIMESTAMP , " +
                        "status SMALLINT)",
                        Flight.TABLE_NAME)
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
     * @param metaData      -   метаданные базы
     * @return              -   Существует ли таблица TABLE_NAME
     * @throws SQLException -   Ошибка получения таблиц
     */
    private boolean checkTableExists(DatabaseMetaData metaData) throws SQLException {
        ResultSet resultSet = metaData.getTables(
                null, null, Flight.TABLE_NAME.toUpperCase(), new String[]{"TABLE"}
        );
        return resultSet.next();
    }

    /**
     * Вспомогательный метод для создания объекта {@link Flight} через ResultSet
     *
     * @param resultSet     -   ResultSet
     * @return              -   Объект {@link Flight}
     * @throws SQLException -   Ошибка получения значения
     */
    private Flight getFlight(ResultSet resultSet) throws SQLException {
        return new Flight(
                resultSet.getInt("id"), resultSet.getString("number"), resultSet.getString("cityFrom"),
                resultSet.getString("cityTo"), resultSet.getString("airline"),
                resultSet.getTimestamp("departureTime").toLocalDateTime(),
                resultSet.getTimestamp("arrivalTime").toLocalDateTime(),
                FlightStatus.getStatus(resultSet.getInt("status"))
        );
    }

    /**
     * Метод создания записи о новом рейсе в БД
     *
     * @param flight    -   Рейс
     * @return          -   Успешно ли выполнилась операция
     */
    public boolean createNew(Flight flight) {
        String query = "INSERT INTO " + Flight.TABLE_NAME +" VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, flight.getNumber());
            statement.setString(2, flight.getCityFrom());
            statement.setString(3, flight.getCityTo());
            statement.setString(4, flight.getAirline());
            statement.setTimestamp(5, Timestamp.valueOf(flight.getDepartureTime()));
            statement.setTimestamp(6, Timestamp.valueOf(flight.getArrivalTime()));
            statement.setInt(7, flight.getStatus().ordinal());
            statement.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Error occured during instance insertion: " + e.getMessage());
            return false;
        }
    }

    /**
     * Метод получения объекта {@link Flight} из БД по указанному идентификатору
     *
     * @param id    -   идентификатор
     * @return      -   соответствующий объект {@link Flight}
     */
    public Flight getById(int id) {
        String query = "SELECT * FROM " + Flight.TABLE_NAME + " WHERE id = " + id;
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(query);
            if (!resultSet.next()) {
                System.out.println("Instance with given id not found");
                return null;
            }
            return getFlight(resultSet);
        } catch (SQLException e) {
            System.out.println("Error occured during instance finding: " + e.getMessage());
            return null;
        }
    }

    /**
     * Метод обновления объекта {@link Flight} в БД
     *
     * @param flight    -   Рейс
     */
    public boolean update(Flight flight) {
        StringBuilder query = new StringBuilder("UPDATE " + Flight.TABLE_NAME + " SET ");
        query.append("number = ").append(flight.getNumber()).append(", ")
                .append("cityFrom = ").append(flight.getCityFrom()).append(", ")
                .append("cityTo = ").append(flight.getCityTo()).append(", ")
                .append("airline = ").append(flight.getAirline()).append(", ")
                .append("departureTime = ").append(Timestamp.valueOf(flight.getDepartureTime())).append(", ")
                .append("arrivalTime = ").append(Timestamp.valueOf(flight.getArrivalTime())).append(", ")
                .append("status = ").append(flight.getStatus().ordinal())
                .append(" WHERE id = ").append(flight.getId()).append(";");
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {

            statement.execute(query.toString());
            return true;
        } catch (SQLException e) {
            System.out.println("Error occured during updating instance: " + e.getMessage());
            return false;
        }
    }
}
