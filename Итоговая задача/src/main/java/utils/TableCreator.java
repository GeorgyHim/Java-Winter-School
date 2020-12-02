package utils;

import org.apache.derby.jdbc.EmbeddedDataSource;

import java.sql.*;

public class TableCreator {

    /**
     * Метод создания таблицы базы данных
     */
    public static void createTable(EmbeddedDataSource dataSource, String tableName) {
        System.out.println(String.format("Start initializing \"%s\" table...", tableName.toUpperCase()));
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement() ) {

            if (checkTableExists(connection.getMetaData(), tableName))
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
    private static boolean checkTableExists(DatabaseMetaData metaData, String tableName) throws SQLException {
        ResultSet resultSet = metaData.getTables(null, null, tableName.toUpperCase(), new String[]{"TABLE"});
        return resultSet.next();
    }
}
