package storage_services;

import executor.Executor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import task.Task;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class CountSaverTest {

    @BeforeEach
    public void flushCounts() {
        CountSaver emptyCountSaver = new CountSaver("Folder1", "Folder2");
        Executor.loadCount(emptyCountSaver);
        Task.loadCount(emptyCountSaver);
    }

    @Test
    public void testGetCountsWithoutFiles() {
        CountSaver countSaver = new CountSaver("Folder1", "Folder2");
        Assertions.assertEquals(0, countSaver.getTaskCount());
        Assertions.assertEquals(0, countSaver.getExecutorCount());
    }

    private void createFileAndRead(String name, CountSaver countSaver, String type, int value) throws IOException {
        File file = new File(name);
        file.createNewFile();
        if (value > 0) {
            try (FileOutputStream outputStream = new FileOutputStream(file)) {
                outputStream.write(value);
            }
        }
        int count = type.equals("task") ? countSaver.getTaskCount() : countSaver.getExecutorCount();
        Assertions.assertEquals(value, count);
        if (type.equals("task")) {
            Task.loadCount(countSaver);
            Assertions.assertEquals(value, Task.getCount());
        }
        else {
            Executor.loadCount(countSaver);
            Assertions.assertEquals(value, Executor.getCount());
        }
        flushCounts();
        file.delete();
    }

    private void createFileAndWrite(String name, CountSaver countSaver, String type) throws IOException {
        File file = new File(name);
        file.createNewFile();
        if (type.equals("task")) {
            new Task("Problem");
            countSaver.saveTaskCount();
        }
        else {
            new Executor("Gosha");
            new Executor("Alex");
            countSaver.saveExecutorCount();
        }
        int count = type.equals("task") ? 1 : 2;
        try (FileInputStream inputStream = new FileInputStream(file)) {
            Assertions.assertEquals(count, inputStream.read());
        }
        flushCounts();
        file.delete();
    }

    private CountSaver getCountSaverInCurrentDir() {
        return new CountSaver(System.getProperty("user.dir") + "\\", System.getProperty("user.dir") + "\\");
    }

    @Test
    public void testGetCountsFromEmptyFiles() throws IOException {
        CountSaver countSaver = getCountSaverInCurrentDir();
        createFileAndRead("TasksCount", countSaver, "task", 0);
        createFileAndRead("ExecutorsCount", countSaver, "executor", 0);
    }

    @Test
    public void testGetCounts() throws IOException {
        CountSaver countSaver = getCountSaverInCurrentDir();
        createFileAndRead("TasksCount", countSaver, "task", 5);
        createFileAndRead("ExecutorsCount", countSaver, "executor", 7);
    }

    @Test
    public void testWriteCounts() throws IOException {
        CountSaver countSaver = getCountSaverInCurrentDir();
        createFileAndWrite("TasksCount", countSaver, "task");
        createFileAndWrite("ExecutorsCount", countSaver, "executor");
    }
}
