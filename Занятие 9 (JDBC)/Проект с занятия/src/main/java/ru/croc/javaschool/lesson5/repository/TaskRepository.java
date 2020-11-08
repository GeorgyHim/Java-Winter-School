package ru.croc.javaschool.lesson5.repository;

import org.apache.derby.jdbc.EmbeddedDataSource;
import ru.croc.javaschool.lesson5.model.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Репозиторий для доступа к таблице с данными о задачах (Task).
 *
 * @author VKhlybov
 */
public class TaskRepository {

    private static final String TABLE_NAME = "task";

    private EmbeddedDataSource dataSource;

    public TaskRepository(EmbeddedDataSource dataSource) {
        this.dataSource = dataSource;
        initTable();
    }

    /**
     * Метод инициализации БД.
     *
     * Оказывается, SQL диалект, используемый JavaDB, не умеет в "IF NOT EXISTS" :(
     * Поэтому пришлось найти небольшой workaround, который предварительно проверяет существование таблицы в базе.
     */
    private void initTable() {
        System.out.println(String.format("Start initializing %s table", TABLE_NAME));
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            DatabaseMetaData databaseMetadata = connection.getMetaData();
            ResultSet resultSet = databaseMetadata.getTables(
                    null,
                    null,
                    // Несмотря на то, что мы создаем таблицу в нижнем регистре (и дальше к ней так же обращаемся),
                    // поиск мы осуществляем в верхнем. Такие вот приколы
                    TABLE_NAME.toUpperCase(),
                    new String[]{"TABLE"});
            if (resultSet.next()) {
                System.out.println("Table has already been initialized");
            } else {
                statement.executeUpdate(
                        "CREATE TABLE "
                                + TABLE_NAME
                                + " ("
                                + "id INTEGER PRIMARY KEY, "
                                + "title VARCHAR(255)"
                                + ")");
                System.out.println("Table was successfully initialized");
            }
        } catch (SQLException e) {
            System.out.println("Error occurred during table initializing: " + e.getMessage());
        } finally {
            System.out.println("=========================");
        }
    }

    /**
     * Метод поиска всех задач в БД.
     *
     * @return список всех созданных задач
     */
    public List<Task> findAll() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_NAME);
            List<Task> taskList = new ArrayList<>();
            while (resultSet.next()) {
                taskList.add(new Task(resultSet.getInt("id"), resultSet.getString("title")));
            }
            return taskList;
        } catch (Exception e) {
            System.out.println("Ошибка выполнения запроса: " + e.getMessage());
        }
        return new ArrayList<>();
    }

    /**
     * Метод создания записи в БД о новой задаче.
     *
     * @param task задача
     */
    public void createNew(Task task) {
        String sqlQuery = "INSERT INTO " + TABLE_NAME + " VALUES (?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setString(1, task.getId().toString());
            statement.setString(2, task.getTitle());
            statement.execute();
        } catch (Exception e) {
            System.out.println("Ошибка выполнения запроса: " + e.getMessage());
        }
    }
}
