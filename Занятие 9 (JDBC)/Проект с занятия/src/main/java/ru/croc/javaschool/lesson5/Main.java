package ru.croc.javaschool.lesson5;

import ru.croc.javaschool.lesson5.db.DataSourceProvider;
import ru.croc.javaschool.lesson5.model.Task;
import ru.croc.javaschool.lesson5.repository.TaskRepository;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        DataSourceProvider dataSourceProvider = null;
        try {
            dataSourceProvider = new DataSourceProvider();
        } catch (IOException e) {
            System.out.println("Ошибка создания провайдера: " + e.getMessage());
            throw e;
        }

        TaskRepository taskRepository = new TaskRepository(dataSourceProvider.getDataSource());
        // создадим новую задачу
        taskRepository.createNew(new Task(1, "Learn something new about Java"));
        // проверим, что запись добавлена в таблицу
        for (Task task : taskRepository.findAll()) {
            System.out.println(task);
        }
    }
}
