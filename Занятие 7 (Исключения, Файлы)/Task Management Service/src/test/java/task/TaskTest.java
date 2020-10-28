package task;

import executor.Executor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import storage_services.CountSaver;

public class TaskTest {

    @BeforeEach
    public void flushCounts() {
        CountSaver emptyCountSaver = new CountSaver("Folder1", "Folder2");
        Executor.loadCount(emptyCountSaver);
        Task.loadCount(emptyCountSaver);
    }

    @Test
    public void testCounting() {
        Task task1 = new Task("Problem");
        Assertions.assertEquals("Task-1", task1.getId());
        Assertions.assertEquals("Problem", task1.getName());
        Assertions.assertEquals("", task1.getDescription());
        Assertions.assertEquals(TaskStatus.TODO, task1.getStatus());
        Assertions.assertNull(task1.getExecutor());

        Executor executor = new Executor("Gosha");
        Task task2 = new Task("Complex Problem",  "", executor);
        Assertions.assertEquals("Task-2", task2.getId());
        Assertions.assertEquals("Complex Problem", task2.getName());
        Assertions.assertEquals("", task2.getDescription());
        Assertions.assertEquals(TaskStatus.TODO, task2.getStatus());
        Assertions.assertEquals(executor, task2.getExecutor());

        Assertions.assertEquals(2, Task.getCount());
    }
}
