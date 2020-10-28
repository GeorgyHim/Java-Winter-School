import executor.Executor;
import executor.NoExecutorException;
import org.junit.jupiter.api.*;
import storage_services.CountSaver;
import task.NoTaskException;
import task.Task;
import task.TaskStatus;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

public class TaskManagenentServiceTest {
    // TODO: manage, process**
    // TODO: findTask/Executor с разными по типу id

    @AfterEach
    public void flushCounts() {
        CountSaver emptyCountSaver = new CountSaver("Folder1", "Folder2");
        Executor.loadCount(emptyCountSaver);
        Task.loadCount(emptyCountSaver);
    }

    static String testStoragePath = System.getProperty("user.dir") + "\\TaskManagenentServiceTest\\";
    static TaskManagementService taskService = new TaskManagementService(System.in, testStoragePath);

    @Test
    public void testWrongCommands() {
        String wrong = "Wrong Command!";
        Assertions.assertEquals(wrong, taskService.manage("some"));
        Assertions.assertEquals(wrong, taskService.manage("add -te"));
        Assertions.assertEquals(wrong, taskService.manage("add -k"));
        Assertions.assertEquals(wrong, taskService.manage("list -te"));
        Assertions.assertEquals(wrong, taskService.manage("list -k"));
        Assertions.assertEquals(wrong, taskService.manage("changestatuss"));
        Assertions.assertEquals(wrong, taskService.manage("changestatus some"));
        Assertions.assertEquals(wrong, taskService.manage("changestatus bad wrong"));
        Assertions.assertEquals(wrong, taskService.manage("changeexecutor some"));
        Assertions.assertEquals(wrong, taskService.manage("changeexecutor bad 322"));
    }

    @Test
    public void testAddExecutor() throws NoExecutorException, IOException, ClassNotFoundException {
        taskService.setIn(new ByteArrayInputStream("Gosha Him".getBytes()));
        Assertions.assertEquals("Executor added and saved", taskService.manage("add -e"));
        Executor executor = taskService.findExecutor("Executor-1");
        Assertions.assertEquals("Gosha Him", executor.getName());
    }

    @Test
    public void testAddTaskWithoutExecutor() throws ClassNotFoundException, IOException, NoTaskException {
        taskService.setIn(new ByteArrayInputStream("Big Problem\n\nSome description".getBytes()));
        Assertions.assertEquals("Task added and saved", taskService.manage("add -t"));
        Task task = taskService.findTask("Task-1");
        Assertions.assertEquals("Big Problem", task.getName());
        Assertions.assertEquals("Some description", task.getDescription());
        Assertions.assertEquals(TaskStatus.TODO, task.getStatus());
        Assertions.assertNull(task.getExecutor());
    }

    @Test
    public void testAddTaskWithExecutor() {

    }



    @AfterAll
    static void clear() {
        deleteDirectory(new File(testStoragePath));
    }

    private static void deleteDirectory(File directory) {
        File[] allContents = directory.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file);
            }
        }
        directory.delete();
    }
}
