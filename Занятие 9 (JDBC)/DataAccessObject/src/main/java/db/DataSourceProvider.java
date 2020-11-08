package db;

import org.apache.derby.jdbc.EmbeddedDataSource;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Класс-провайдер для доступа к БД (JavaDB)
 */
public class DataSourceProvider {

    /** DataSource */
    private EmbeddedDataSource dataSource;

    /** Параметры конфигурации */
    private Properties properties;

    /**
     * Конструктор
     * @throws IOException  -   Ошибка инициализации
     */
    public DataSourceProvider() throws IOException {
        loadProperties();
        createDataSource();
    }

    private void loadProperties() throws IOException {
        properties = new Properties();
        try {
            FileInputStream inputStream = new FileInputStream("application.properties");
            properties.load(inputStream);
        }
        catch (Exception e) {
            System.out.println();
            throw e;
        }
    }

    private void createDataSource() {
        if (dataSource != null)
            return;

        dataSource = new EmbeddedDataSource();
        dataSource.setUser("");
        dataSource.setPassword("");
        dataSource.setDatabaseName(properties.getProperty("dbname"));
        // TODO: Проверить и создать БД
    }

    public EmbeddedDataSource getDataSource() {
        if (dataSource == null)
            createDataSource();
        return dataSource;
    }
}
