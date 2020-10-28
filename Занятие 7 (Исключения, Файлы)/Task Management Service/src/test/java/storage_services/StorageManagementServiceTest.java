package storage_services;

import executor.Executor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import task.Task;

import java.io.File;
import java.io.IOException;

public class StorageManagementServiceTest {
    String storage_path = System.getProperty("user.dir");
    private StorageManagementService storageManagementService = new StorageManagementService(storage_path);

    @Test
    public void testSaveAndFindTask() throws IOException, ClassNotFoundException {
        Task task = new Task("testSaveTaskProblem");
        storageManagementService.saveObject(task);
        File task_file = new File(storageManagementService.getTasksFolder() + task.getId());
        Task task_read = storageManagementService.findTask(task.getId());
        Assertions.assertEquals(task, task_read);
        task_file.delete();
    }

    @Test
    public void testSaveAndFindExecutor() throws IOException, ClassNotFoundException {
        Executor executor = new Executor("testSaveExecutor");
        storageManagementService.saveObject(executor);
        File exec_file = new File(storageManagementService.getExecutorsFolder() + executor.getId());
        Executor exec_read = storageManagementService.findExecutor(executor.getId());
        Assertions.assertEquals(executor, exec_read);
        exec_file.delete();
    }
}
