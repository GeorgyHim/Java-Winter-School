package task;

import saver.CountSaver;
import executor.Executor;

import java.io.Serializable;

/**
 * Задача
 */
public class Task implements Serializable {
    /** Версия класса для сериализации */
    private static final long serialVersionUID = 1L;

    /** Общее количество созданных задач */
    private static int count = 0;

    /** Префикс для кода задачи*/
    public static final String ID_PREFIX = "Task-";

    /** Код задачи */
    private String id;

    /** Наименование задачи */
    private String name;

    /**  Описание */
    private String description;

    /** Испольнитель */
    private Executor executor;

    /** Статус */
    private TaskStatus status;

    public Task(String name) {
        this(name, "", null, TaskStatus.TODO);
    }

    public Task(String name, Executor executor) {
        this(name, "", executor, TaskStatus.TODO);
    }

    public Task(String name, String description, Executor executor) {
        this(name, description, executor, TaskStatus.TODO);
    }

    public Task(String name, String description, Executor executor, TaskStatus status) {
        this.id = ID_PREFIX + ++count;
        this.name = name;
        this.description = description;
        this.executor = executor;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Executor getExecutor() {
        return executor;
    }

    public void setExecutor(Executor executor) {
        this.executor = executor;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public static int getCount() {
        return count;
    }

    public void loadCount(CountSaver countSaver) {
        count = countSaver.getTaskCount();
    }

    @Override
    public String toString() {
        return "Task(" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", executor=" + executor +
                ", status=" + status +
                ')';
    }
}
