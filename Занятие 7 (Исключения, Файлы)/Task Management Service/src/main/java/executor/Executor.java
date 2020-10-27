package executor;

import storage_services.CountSaver;

import java.io.Serializable;

public class Executor implements Serializable {
    /** Общее количество исполнителей */
    private static int count = 0;

    /** Префикс для кода задачи*/
    public static final String ID_PREFIX = "Executor-";

    /** ID */
    private String id;

    /** Имя */
    private String name;

    public Executor(String name) {
        this.id = ID_PREFIX + ++count;
        this.name = name;
    }

    public static void loadCount(CountSaver countSaver) {
        count = countSaver.getExecutorCount();
    }

    public String getId() {
        return id;
    }

    public static int getCount() {
        return count;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return String.format("(name = %s, id = %s)", name, id);
    }
}
