package storage_services;

import executor.Executor;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import task.Task;

import java.io.File;
import java.io.IOException;

public class StorageManagementServiceTest {
    static String storage_path = System.getProperty("user.dir");
    private static StorageManagementService storageService = new StorageManagementService(storage_path);

    @Test
    public void testSaveAndFindTask() throws IOException, ClassNotFoundException {
        Task task = new Task("testSaveTaskProblem");
        storageService.saveObject(task);
        File task_file = new File(storageService.getTasksFolder() + task.getId());
        Task task_read = storageService.findTask(task.getId());
        Assertions.assertEquals(task, task_read);
        task_file.delete();
    }

    @Test
    public void testSaveAndFindExecutor() throws IOException, ClassNotFoundException {
        Executor executor = new Executor("testSaveExecutor");
        storageService.saveObject(executor);
        File exec_file = new File(storageService.getExecutorsFolder() + executor.getId());
        Executor exec_read = storageService.findExecutor(executor.getId());
        Assertions.assertEquals(executor, exec_read);
        exec_file.delete();
    }


    @AfterAll
    static void clear() {
        File folder = new File(storageService.getTasksFolder());
        folder.delete();
        folder = new File(storageService.getExecutorsFolder());
        folder.delete();
    }
}
