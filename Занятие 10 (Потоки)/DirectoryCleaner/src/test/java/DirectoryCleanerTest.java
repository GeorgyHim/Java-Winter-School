import org.junit.jupiter.api.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DirectoryCleanerTest {
    private static String testDir = "src/test/test_dir";
    private static Path testPath = Paths.get(testDir);
    private DirectoryCleaner cleaner = new DirectoryCleaner(testDir, 2000);

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
        synchronized (cleaner.getDirectoryPath()) {
            Assertions.assertFalse(cleaner.isThreadAlive());
        }
    }

    @Test
    public void testCleaning() {
        System.out.println("Sambady");
    }

    @AfterAll
    public static void deleteTestDirectory() throws IOException {
        Files.delete(testPath);
    }
}
