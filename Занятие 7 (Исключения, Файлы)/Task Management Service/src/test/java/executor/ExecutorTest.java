package executor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ExecutorTest {
    @Test
    public void testCounting() {
        Executor ex1 = new Executor("Gosha");
        Assertions.assertEquals("Executor-1", ex1.getId());
        Assertions.assertEquals("Gosha", ex1.getName());

        Executor ex2 = new Executor("Alex");
        Assertions.assertEquals("Executor-2", ex2.getId());
        Assertions.assertEquals("Alex", ex2.getName());

        Assertions.assertEquals(2, Executor.getCount());
    }
}
