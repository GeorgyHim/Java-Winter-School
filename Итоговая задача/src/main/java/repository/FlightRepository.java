package repository;

import model.Flight;
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
}
