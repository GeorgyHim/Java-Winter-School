import org.junit.jupiter.api.AfterAll;
import testflusher.TestFlusher;

import java.io.File;

public class TaskManagenentServiceTest extends TestFlusher {
    // TODO: метод getParams + флаги, manage + Неправильные команды, process**
    // TODO: updateData + не учитывает файлы **Count
    // TODO: findTask/Executor с разными id
    // TODO: Add добавляет и файл, и id в сет

    static String testStoragePath;
    static TaskManagementService taskService = new TaskManagementService(null, System.getProperty("user.dir") + "TaskManagenentServiceTest");




//    @AfterAll
//    static void clear() {
//        File folder = new File(taskService.storageService.getTasksFolder());
//        folder.delete();
//        folder = new File(storageManagementService.getExecutorsFolder());
//        folder.delete();
//    }
}
