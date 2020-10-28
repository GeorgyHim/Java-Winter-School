import executor.Executor;
import org.junit.jupiter.api.BeforeEach;
import storage_services.CountSaver;
import task.Task;

import java.io.ByteArrayInputStream;
import java.io.File;

public class TaskManagenentServiceTest {
    // TODO: метод getParams + флаги, manage + Неправильные команды, process**
    // TODO: updateData + не учитывает файлы **Count
    // TODO: findTask/Executor с разными id
    // TODO: Add добавляет и файл, и id в сет

    @BeforeEach
    public static void flushCounts() {
        CountSaver emptyCountSaver = new CountSaver("Folder1", "Folder2");
        Executor.loadCount(emptyCountSaver);
        Task.loadCount(emptyCountSaver);
    }

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
