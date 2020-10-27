package task;

import executor.Executor;

import java.util.Objects;

/**
 * Задача
 */
public class Task {
    /** Общее количество созданных задач */
    private static int count = 0;

    /** Префикс для кода задачи*/
    public static final String CODE_PREFIX = "Task-";

    /** Код задачи */
    private String code;

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
        this.code = CODE_PREFIX + count++;
        this.name = name;
        this.description = description;
        this.executor = executor;
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    @Override
    public String toString() {
        return "Task(" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", executor=" + executor +
                ", status=" + status +
                ')';
    }
}
