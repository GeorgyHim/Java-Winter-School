package db;

import org.apache.derby.jdbc.EmbeddedDataSource;

import java.io.FileInputStream;
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
     * @throws IOException  -   Ошибка инициализации
     */
    public DataSourceProvider(String type) throws IOException {
        String path = "src/main/resources/" + (type.equals("test") ? "test.properties" : "application.properties");
        loadProperties(path);
        createDataSource();
    }

    /**
     * Метод загрузки конфигурации
     * @throws IOException - ошибка загрузки конфигурации
     */
    private void loadProperties(String path) throws IOException {
        properties = new Properties();
        try {
            FileInputStream inputStream = new FileInputStream(path);
            properties.load(inputStream);
        }
        catch (Exception e) {
            System.out.println();
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
        dataSource.setUser("");
        dataSource.setPassword("");
        dataSource.setDatabaseName(properties.getProperty("dbname"));
        dataSource.setCreateDatabase("create");
    }

    public EmbeddedDataSource getDataSource() {
        if (dataSource == null)
            createDataSource();
        return dataSource;
    }
}
