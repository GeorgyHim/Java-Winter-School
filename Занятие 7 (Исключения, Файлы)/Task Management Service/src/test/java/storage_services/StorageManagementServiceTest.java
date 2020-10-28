package storage_services;

import executor.Executor;
import org.junit.jupiter.api.*;
import task.Task;

import java.io.File;
import java.io.IOException;

public class StorageManagementServiceTest {

    @AfterEach
    public void flushCounts() {
        CountSaver emptyCountSaver = new CountSaver("Folder1", "Folder2");
        Executor.loadCount(emptyCountSaver);
        Task.loadCount(emptyCountSaver);
    }

    static String storagePath = System.getProperty("user.dir");
    private static StorageManagementService storageService = new StorageManagementService(storagePath);

    @Test
    public void testSaveAndFindTask() throws IOException, ClassNotFoundException {
        Task task = new Task("testSaveTaskProblem");
        storageService.saveObject(task);
        File taskFile = new File(storageService.getTasksFolder() + task.getId());
        Task taskRead = storageService.findTask(task.getId());
        Assertions.assertEquals(task, taskRead);
        flushCounts();
        taskFile.delete();
    }

    @Test
    public void testSaveAndFindExecutor() throws IOException, ClassNotFoundException {
        Executor executor = new Executor("testSaveExecutor");
        storageService.saveObject(executor);
        File execFile = new File(storageService.getExecutorsFolder() + executor.getId());
        Executor execRead = storageService.findExecutor(executor.getId());
        Assertions.assertEquals(executor, execRead);
        execFile.delete();
    }

    @Test
    public void testFindBadObjects() {
        Assertions.assertThrows(IOException.class, () -> storageService.findExecutor("Executor-99"));
        Assertions.assertThrows(IOException.class, () -> storageService.findTask("Task-99"));
        Assertions.assertThrows(IOException.class, () -> storageService.findTask("Some"));
        Assertions.assertThrows(IOException.class, () -> storageService.findExecutor("Some"));
    }

    @AfterAll
    static void delete() {
        deleteDirectory(new File(storagePath));
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
