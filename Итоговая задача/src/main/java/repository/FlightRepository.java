package repository;

import org.apache.derby.jdbc.EmbeddedDataSource;

/**
 * Репозиторийдля доступа к таблице с данными о рейсах ({@link model.Flight})
 */
public class FlightRepository {

    /** DataSource */
    private EmbeddedDataSource dataSource;
}
