import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

public class MaxFinderTest {

    @Test
    public void testEqualParts() throws ExecutionException, InterruptedException {
        int[] array = new int[]{2, 1, 5, 4, 6, 3};
        MaxFinder finder = new MaxFinder(array, 3);
        Assertions.assertEquals(6, finder.findMax());
        List<Future<Integer>> futures = finder.getFutures();
        Assertions.assertEquals(2, futures.get(0).get());
        Assertions.assertEquals(5, futures.get(1).get());
        Assertions.assertEquals(6, futures.get(2).get());
    }

    @Test
    public void testDifferentParts() throws ExecutionException, InterruptedException {
        int[] array = new int[]{2, 1, 5, 7, 6, 4, 3};
        MaxFinder finder = new MaxFinder(array, 3);
        Assertions.assertEquals(7, finder.findMax());
        List<Future<Integer>> futures = finder.getFutures();
        Assertions.assertEquals(5, futures.get(0).get());
        Assertions.assertEquals(7, futures.get(1).get());
        Assertions.assertEquals(3, futures.get(2).get());
    }

    @Test
    public void testLastEmptyPart() throws ExecutionException, InterruptedException {
        int[] array = IntStream.rangeClosed(1, 69).map(operand -> Math.abs(40 - operand)).toArray();
        MaxFinder finder = new MaxFinder(array, 10);
        Assertions.assertEquals(39, finder.findMax());
        List<Future<Integer>> futures = finder.getFutures();
        Assertions.assertEquals(array[68], futures.get(9).get());
    }

    @Test
    public void testNumberLessThanThreadCount() throws ExecutionException, InterruptedException {
        int[] array = new int[]{2, 5, 1};
        MaxFinder finder = new MaxFinder(array, 4);
        Assertions.assertEquals(5, finder.findMax());
        List<Future<Integer>> futures = finder.getFutures();
        Assertions.assertEquals(2, futures.get(0).get());
        Assertions.assertEquals(5, futures.get(1).get());
        Assertions.assertEquals(1, futures.get(2).get());
        Assertions.assertEquals(1, futures.get(3).get());
    }

    @Test
    public void testLargeArray() throws ExecutionException, InterruptedException {
        int[] array = IntStream.rangeClosed(1, 10_000_000).map(operand -> Math.abs(4_000_000 - operand)).toArray();
        MaxFinder finder = new MaxFinder(array, 10);
        Assertions.assertEquals(6_000_000, finder.findMax());
    }
}
