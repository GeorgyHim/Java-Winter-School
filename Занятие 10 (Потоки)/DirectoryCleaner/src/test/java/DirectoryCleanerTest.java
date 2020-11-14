import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DirectoryCleanerTest {
    private static String testDir = "src" + File.separator + "test" +  File.separator + "test_dir";
    private static Path testPath = Paths.get(testDir);
    private static int timeout = 1000;
    private DirectoryCleaner cleaner = new DirectoryCleaner(testDir, timeout);

    @BeforeAll
    public static void createTestDirectory() throws IOException {
        Files.createDirectories(testPath);
    }

    @Test
    public void testStartAndStopCleaner() {
        cleaner.startCleaning();
        synchronized (cleaner.getDirectoryPath()) {
            Assertions.assertTrue(cleaner.isThreadAlive());
        }
        cleaner.stopCleaning();
        Assertions.assertFalse(cleaner.isThreadAlive());
    }

    @Test
    public void testCleaning() throws InterruptedException, IOException {
        cleaner.startCleaning();
        Thread.sleep(timeout / 10);
        Assertions.assertTrue(cleaner.isDirEmpty());
        Path path = Paths.get(testDir, "myfile");
        Files.createFile(path);
        Assertions.assertFalse(cleaner.isDirEmpty());
        Thread.sleep(timeout + timeout / 3);
        Assertions.assertTrue(cleaner.isDirEmpty());
    }

    @AfterAll
    public static void deleteTestDirectory() throws IOException {
        Files.delete(testPath);
        Assertions.assertFalse(Files.exists(testPath));
    }
}
