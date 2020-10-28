package testflusher;

import executor.Executor;
import org.junit.jupiter.api.BeforeAll;
import storage_services.CountSaver;

public class TestFlusher {

    @BeforeAll
    public static void flushCounts() {
        CountSaver emptyCountSaver = new CountSaver("Folder1", "Folder2");
        Executor.loadCount(emptyCountSaver);
        Executor.loadCount(emptyCountSaver);
    }
}
