package storage_services;

import executor.Executor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import task.Task;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class StorageManagementServiceTest {
    // TODO - Test:   findExecutor, findTask
    String storage_path = System.getProperty("user.dir");
    private StorageManagementService storageManagementService = new StorageManagementService(storage_path);

    @Test
    public void testSaveTask() throws IOException, ClassNotFoundException {
        Task task = new Task("testSaveTaskProblem");
        storageManagementService.saveObject(task);
        File task_file = new File(storageManagementService.getTasksFolder() + task.getId());
        try (ObjectInputStream serializer = new ObjectInputStream(new FileInputStream(task_file))) {
            Task task_read = (Task) serializer.readObject();
            Assertions.assertEquals(task, task_read);
        }
        finally {
            task_file.delete();
        }
    }

    @Test
    public void testSaveExecutor() throws IOException, ClassNotFoundException {
        Executor executor = new Executor("testSaveExecutor");
        storageManagementService.saveObject(executor);
        File exec_file = new File(storageManagementService.getExecutorsFolder() + executor.getId());
        try (ObjectInputStream serializer = new ObjectInputStream(new FileInputStream(exec_file))) {
            Executor exec_read = (Executor) serializer.readObject();
            Assertions.assertEquals(executor, exec_read);
        }
        finally {
            exec_file.delete();
        }
    }
}
