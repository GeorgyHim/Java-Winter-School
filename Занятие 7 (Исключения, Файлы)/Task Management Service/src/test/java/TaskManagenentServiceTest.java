import executor.Executor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import storage_services.CountSaver;
import task.Task;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

public class TaskManagenentServiceTest {
    // TODO: manage, process**, updateData + не учитывает файлы **Count
    // TODO: findTask/Executor с разными по типу id
    // TODO: Add добавляет и файл, и id в сет

//    @BeforeEach
//    public void flushCounts() {
//        CountSaver emptyCountSaver = new CountSaver("Folder1", "Folder2");
//        Executor.loadCount(emptyCountSaver);
//        Task.loadCount(emptyCountSaver);
//    }

    static String testStoragePath = System.getProperty("user.dir") + "TaskManagenentServiceTest";
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



//    @AfterAll
//    static void clear() {
//        File folder = new File(taskService.storageService.getTasksFolder());
//        folder.delete();
//        folder = new File(storageManagementService.getExecutorsFolder());
//        folder.delete();
//    }
}
