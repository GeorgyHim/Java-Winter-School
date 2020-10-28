//package storage_services;
//
//import executor.Executor;
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import task.Task;
//
//import java.io.File;
//import java.io.IOException;
//
//public class StorageManagementServiceTest {
//
//    @BeforeAll
//    public static void flushCounts() {
//        CountSaver emptyCountSaver = new CountSaver("Folder1", "Folder2");
//        Executor.loadCount(emptyCountSaver);
//        Task.loadCount(emptyCountSaver);
//    }
//
//    static String storagePath = System.getProperty("user.dir");
//    private static StorageManagementService storageService = new StorageManagementService(storagePath);
//
//    @Test
//    public void testSaveAndFindTask() throws IOException, ClassNotFoundException {
//        Task task = new Task("testSaveTaskProblem");
//        storageService.saveObject(task);
//        File taskFile = new File(storageService.getTasksFolder() + task.getId());
//        Task taskRead = storageService.findTask(task.getId());
//        Assertions.assertEquals(task, taskRead);
//        taskFile.delete();
//    }
//
//    @Test
//    public void testSaveAndFindExecutor() throws IOException, ClassNotFoundException {
//        Executor executor = new Executor("testSaveExecutor");
//        storageService.saveObject(executor);
//        File execFile = new File(storageService.getExecutorsFolder() + executor.getId());
//        Executor execRead = storageService.findExecutor(executor.getId());
//        Assertions.assertEquals(executor, execRead);
//        execFile.delete();
//    }
//
//
//    @AfterAll
//    static void clear() {
//        File folder = new File(storageService.getTasksFolder());
//        folder.delete();
//        folder = new File(storageService.getExecutorsFolder());
//        folder.delete();
//    }
//}
