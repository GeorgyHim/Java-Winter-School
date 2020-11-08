package ru.croc.javaschool.lesson5.db;

import org.apache.derby.jdbc.EmbeddedDataSource;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Класс-провайдер для доступа к БД (JavaDB).
 *
 * @author VKhlybov
 */
public class DataSourceProvider {

    /**
     * DataSource.
     */
    private EmbeddedDataSource dataSource;

    /**
     * Параметры конфигурации из файла application.properties.
     */
    private Map<String, String> properties = new HashMap<>();

    /**
     * Консутркутор.
     *
     * @throws IOException ошибка инициализации
     */
    public DataSourceProvider() throws IOException {
        loadProperties();
    }

    /**
     * Метод загрузки настроек конфигурации в память.
     *
     * @throws IOException ошибка получения настроек
     */
    private void loadProperties() throws IOException {
        Properties properties = new Properties();
        try {
            properties.load(
                    Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties"));
            for (Map.Entry<Object, Object> entry : properties.entrySet()) {
                this.properties.put((String) entry.getKey(), (String) entry.getValue());
            }
        } catch (Exception e) {
            System.out.println("Error occurred during loading properties");
            throw e;
        }
    }

    /**
     * Метод получения экземпляра DataSource'а.
     *
     * @return data source object
     */
    public EmbeddedDataSource getDataSource() {
        if (dataSource == null) {
            dataSource = new EmbeddedDataSource();
            dataSource.setUser("");
            dataSource.setPassword("");
            dataSource.setDatabaseName(properties.get("dbname"));
            dataSource.setCreateDatabase("create");
        }

        return dataSource;
    }
}
