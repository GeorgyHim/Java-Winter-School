package db;

import org.apache.derby.jdbc.EmbeddedDataSource;

import java.io.IOException;
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
     *
     * @throws IOException  -   Ошибка инициализации
     */
    public DataSourceProvider() throws IOException {
        loadProperties();
        createDataSource();
    }

    /**
     * Метод загрузки конфигурации
     *
     * @throws IOException - ошибка загрузки конфигурации
     */
    private void loadProperties() throws IOException {
        properties = new Properties();
        try {
            properties.load(
                    Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties")
            );
        }
        catch (Exception e) {
            System.out.println("Error occurred during loading properties");
            throw e;
        }
    }

    /**
     * Метод создания DataSource и при необходимости - самой БД
     */
    private void createDataSource() {
        if (dataSource != null)
            return;

        dataSource = new EmbeddedDataSource();
        dataSource.setUser(properties.getProperty("user"));
        dataSource.setPassword(properties.getProperty("password"));
        dataSource.setDatabaseName(properties.getProperty("dbname"));
        dataSource.setCreateDatabase("create");
    }

    public EmbeddedDataSource getDataSource() {
        if (dataSource == null)
            createDataSource();
        return dataSource;
    }
}