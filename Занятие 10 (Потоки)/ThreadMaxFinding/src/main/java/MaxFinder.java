import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;

public class MaxFinder {
    /** Число элементов в массиве */
    private int number;

    /** Массив чисел */
    private int[] array;

    /** Количество потоков */
    private int threadCount;

    private List<Future<Integer>> futures;

    public MaxFinder(int number, int[] array, int threadCount) throws Exception {
        if (array.length != number)
            throw new Exception("Wrong array length!");

        this.number = number;
        this.threadCount = threadCount;
        this.array = array;
        futures = new ArrayList<>();
    }

    public MaxFinder(int number, int threadCount) throws Exception {
        this(number, readArray(number), threadCount);
    }

    /**
     * Метод ввода массива
     */
    private static int[] readArray(int n) {
        int[] a = new int[n];
        Scanner in = new Scanner(System.in);
        for (int i = 0; i < n; i++)
            a[i] = in.nextInt();
        return a;
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

    public void setArray(int[] array) {
        this.number = array.length;
        this.array = array;
    }

    public int getThreadCount() {
        return threadCount;
    }

    public void setThreadCount(int threadCount) {
        this.threadCount = threadCount;
    }

    public int getNumber() {
        return number;
    }

    public List<Future<Integer>> getFutures() {
        return futures;
    }
}
