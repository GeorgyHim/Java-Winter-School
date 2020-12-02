package repository;

import model.Flight;
import model.helper.FlightStatus;
import org.apache.derby.jdbc.EmbeddedDataSource;
import utils.TableCreator;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
        TableCreator tableCreator = new TableCreator(dataSource, Flight.TABLE_NAME);
        tableCreator.createTable();
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

    public List<Flight> findAll() {
        String query = "SELECT * FROM " + Flight.TABLE_NAME;
        List<Flight> flights = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                flights.add(getFlight(resultSet));
            }
        } catch (SQLException e) {
            System.out.println("Error occurred during finding all instances: " + e.getMessage());
        }
        return flights;
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
             PreparedStatement statement = connection.prepareStatement(query, new String[]{"ID"})) {

            prepareStatementByFlight(statement, flight);
            int success = statement.executeUpdate();
            if (success == 0) {
                throw new SQLException();
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                generatedKeys.next();
                flight.setId(generatedKeys.getInt(1));
            }
            return true;
        } catch (SQLException e) {
            System.out.println("Error occurred during instance insertion: " + e.getMessage());
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
            System.out.println("Error occurred during instance finding: " + e.getMessage());
            return null;
        }
    }


    /**
     * Метод обновления объекта {@link Flight} в БД
     *
     * @param flight    -   Рейс
     * @return          -   Успешно ли выполнилась операция
     */
    public boolean update(Flight flight) {
        String query = "UPDATE " + Flight.TABLE_NAME + " SET " +
                "number = ? , " +
                "cityFrom = ? , " +
                "cityTo = ? , " +
                "airline = ? , " +
                "departureTime = ? , " +
                "arrivalTime = ? , " +
                "status = ? " +
                "WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            prepareStatementByFlight(statement, flight);
            statement.setInt(8, flight.getId());
            statement.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Error occurred during updating instance: " + e.getMessage());
            return false;
        }
    }

    /**
     * Метод удаления записи из БД по идентификатору
     *
     * @param id    -   идентификатор
     * @return      -   Успешно ли выполнилась операция
     */
    public boolean deleteById(int id) {
        String query = "DELETE FROM " + Flight.TABLE_NAME + " WHERE id = " + id;
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {

            statement.execute(query);
            return true;
        } catch (SQLException e) {
            System.out.println("Error occurred during deleting instance: " + e.getMessage());
            return false;
        }
    }

    /**
     * Метод удаления записи из БД по переданному объекту рейса
     *
     * @param flight    -   рейс
     * @return          -   Успешно ли выполнилась операция
     */
    public boolean delete(Flight flight) {
        return deleteById(flight.getId());
    }

    private void prepareStatementByFlight(PreparedStatement statement, Flight flight) throws SQLException {
        statement.setString(1, flight.getNumber());
        statement.setString(2, flight.getCityFrom());
        statement.setString(3, flight.getCityTo());
        statement.setString(4, flight.getAirline());
        statement.setTimestamp(5, Timestamp.valueOf(flight.getDepartureTime()));
        statement.setTimestamp(6, Timestamp.valueOf(flight.getArrivalTime()));
        statement.setInt(7, flight.getStatus().ordinal());
    }
}
