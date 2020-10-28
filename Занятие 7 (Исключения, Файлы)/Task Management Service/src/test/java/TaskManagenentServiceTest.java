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





//    @AfterAll
//    static void clear() {
//        File folder = new File(taskService.storageService.getTasksFolder());
//        folder.delete();
//        folder = new File(storageManagementService.getExecutorsFolder());
//        folder.delete();
//    }
}
