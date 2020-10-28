package storage_services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

public class CountSaverTest {
    // TODO: Тест когда файлов нет, должен возвращать 0
    // Тест когда файлы есть
    // Тест на запись

    @Test
    public void testGetCountsWithoutFiles() {
        CountSaver countSaver = new CountSaver("Folder1", "Folder2");
        Assertions.assertEquals(0, countSaver.getTaskCount());
        Assertions.assertEquals(0, countSaver.getExecutorCount());
    }

    @Test
    public void testGetCountsFromEmptyFiles() throws IOException {
        CountSaver countSaver = new CountSaver(System.getProperty("user.dir") + "\\", System.getProperty("user.dir") + "\\");

        File file = new File("TasksCount");
        file.createNewFile();
        Assertions.assertEquals(0, countSaver.getTaskCount());
        file.delete();

        file = new File("ExecutorsCount");
        file.createNewFile();
        Assertions.assertEquals(0, countSaver.getExecutorCount());
        file.delete();
    }
}
