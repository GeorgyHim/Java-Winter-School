package executor;

import storage_services.CountSaver;

import java.io.Serializable;
import java.util.Objects;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Executor executor = (Executor) o;
        return id.equals(executor.id) &&
                Objects.equals(name, executor.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Executor(" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ')';
    }
}
