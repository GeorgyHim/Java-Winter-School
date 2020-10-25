package task;

import java.time.LocalDate;

public class Executor {
    /** Общее количество исполнителей */
    private static int count = 0;

    /** Префикс для кода задачи*/
    private static final String ID_PREFIX = "Executor-";

    /** ID */
    private String id;

    /** Имя */
    private String name;

    public Executor(String name) {
        this.id = ID_PREFIX + count++;
        this.name = name;
    }
}
