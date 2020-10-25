package task;

/**
 * Задача
 */
public class Task {
    /** Общее количество созданных задач */
    private static int count = 0;

    /** Префикс для кода задачи*/
    private static final String CODE_PREFIX = "task.Task-";

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
        this.code = CODE_PREFIX + count++;
        this.status = TaskStatus.TODO;
        this.name = name;
    }

    public Task(String name, Executor executor) {
        this(name);
        this.executor = executor;
    }

    public Task(String name, String description, Executor executor) {
        this(name, executor);
        this.description = description;
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
}
