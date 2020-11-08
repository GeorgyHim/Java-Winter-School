package repository;

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

            if (tableExists(connection.getMetaData()))
                System.out.println("Table has already been initialized");
            else {
                // TODO:
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean tableExists(DatabaseMetaData metaData) throws SQLException {
        ResultSet resultSet = metaData.getTables(
                null, null, TABLE_NAME.toUpperCase(), new String[]{"TABLE"}
        );
        return resultSet.next();
    }
}
