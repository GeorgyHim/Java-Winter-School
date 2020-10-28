package storage_services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class CountSaverTest {
    // Тест на запись

    @Test
    public void testGetCountsWithoutFiles() {
        CountSaver countSaver = new CountSaver("Folder1", "Folder2");
        Assertions.assertEquals(0, countSaver.getTaskCount());
        Assertions.assertEquals(0, countSaver.getExecutorCount());
    }

    private void createFileAndTest0(String name, CountSaver countSaver, String type, int value) throws IOException {
        File file = new File(name);
        file.createNewFile();
        if (value > 0) {
            try (FileOutputStream outputStream = new FileOutputStream(file)) {
                outputStream.write(value);
            }
        }
        int count = type.equals("task") ? countSaver.getTaskCount() : countSaver.getExecutorCount();
        Assertions.assertEquals(value, count);
        file.delete();
    }

    @Test
    public void testGetCountsFromEmptyFiles() throws IOException {
        CountSaver countSaver = new CountSaver(System.getProperty("user.dir") + "\\", System.getProperty("user.dir") + "\\");
        createFileAndTest0("TasksCount", countSaver, "task", 0);
        createFileAndTest0("ExecutorsCount", countSaver, "executor", 0);
    }

    @Test
    public void testGetCounts() throws IOException {
        CountSaver countSaver = new CountSaver(System.getProperty("user.dir") + "\\", System.getProperty("user.dir") + "\\");
        createFileAndTest0("TasksCount", countSaver, "task", 5);
        createFileAndTest0("TasksCount", countSaver, "task", 7);
    }
}
