import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;

public class MaxFinder {
    /** Число элементов в массиве */
    private final int number;

    /** Массив чисел */
    private final int[] array;

    /** Количество потоков */
    private final int threadCount;

    /** Список объектов {@link Future} для проверки в тестах*/
    private List<Future<Integer>> futures;

    public MaxFinder(int[] array, int threadCount) {
        this.number = array.length;
        this.threadCount = threadCount;
        this.array = array;
        futures = new ArrayList<>();
    }

    /**
     * Метод нахождения максимального элемента с использованием многопоточности
     * @return - максимальный элемент массива
     */
    public int findMax() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        futures = new ArrayList<>();
        for (int i = 0; i < threadCount; i++)
            futures.add(executorService.submit(new PartialMaxFinder(i)));

        int max = array[0];
        for (Future<Integer> future : futures)
            max = Math.max(max, future.get());
        return max;
    }

    private class PartialMaxFinder implements Callable<Integer> {
        int index;

        public PartialMaxFinder(int index) {
            this.index = index;
        }

        /**
         *
         * @return - Количество элементов на каждую задачу, округленное в большую сторону
         */
        private int part() {
            return (number / threadCount) + (number % threadCount != 0 ? 1 : 0);
        }

        public Integer call() {
            int startPos = part() * index;
            if (startPos >= number)
                return array[number - 1];

            int endPos = Math.min((index + 1) * part() - 1, number - 1);
            int max = array[startPos];
            for (int i = startPos + 1; i <= endPos; i++)
                max = Math.max(max, array[i]);
            return max;
        }
    }

    public int getThreadCount() {
        return threadCount;
    }

    public int getNumber() {
        return number;
    }

    public List<Future<Integer>> getFutures() {
        return futures;
    }
}
