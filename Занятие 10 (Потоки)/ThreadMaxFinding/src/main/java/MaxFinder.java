import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MaxFinder {
    /** Число элементов в массиве */
    private int number;

    /** Количество потоков */
    private int threadCount;

    /** Массив чисел */
    private int[] array;

    /** Сервис запуска задач */
    ExecutorService executorService;

    public MaxFinder(int number, int threadCount, int[] array) {
        this.number = number;
        this.threadCount = threadCount;
        this.array = array;
        executorService = Executors.newFixedThreadPool(threadCount);
    }

    public MaxFinder(int number, int threadCount) {
        this(number, threadCount, readArray(number));
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
    public int findMax() {
        List<Future<Integer>> futures = new ArrayList<>();
        return array[0];
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

        public Integer call() throws Exception {
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
}
