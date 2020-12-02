package utils;

import model.Flight;
import org.apache.derby.jdbc.EmbeddedDataSource;

import java.sql.*;

public class TableCreator {

    /** DataSource */
    private EmbeddedDataSource dataSource;

    /** Имя таблицы в базе данных */
    private String tableName;

    public TableCreator(EmbeddedDataSource dataSource, String tableName) {
        this.dataSource = dataSource;
        this.tableName = tableName;
    }

    /**
     * Метод создания таблицы базы данных
     */
    public void createTable() {
        System.out.println(String.format("Start initializing \"%s\" table...", tableName.toUpperCase()));
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
                        tableName)
                );
                System.out.println("Table was successfully initialized");
            }
        }
        catch (SQLException e) {
            System.out.println("Error occurred during table initializing: " + e.getMessage());
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
        ResultSet resultSet = metaData.getTables(null, null, tableName.toUpperCase(), new String[]{"TABLE"});
        return resultSet.next();
    }

    public EmbeddedDataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(EmbeddedDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
