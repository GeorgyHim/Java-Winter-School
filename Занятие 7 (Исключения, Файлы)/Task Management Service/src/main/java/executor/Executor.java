package executor;

import java.io.Serializable;
import java.util.Objects;

public class Executor implements Serializable {
    /** Общее количество исполнителей */
    private static int count = 1;

    /** Префикс для кода задачи*/
    public static final String ID_PREFIX = "Executor-";

    /** ID */
    private String id;

    /** Имя */
    private String name;

    public Executor(String name) {
        this.id = ID_PREFIX + count++;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return String.format("(name = %s, id = %s)", name, id);
    }
}
