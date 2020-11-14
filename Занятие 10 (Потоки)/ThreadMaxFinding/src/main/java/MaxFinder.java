import java.util.Scanner;

public class MaxFinder {
    /** Число элементов в массиве */
    private int number;

    /** Количество потоков */
    private int threadCount;

    /** Массив чисел */
    private int[] array;

    public MaxFinder(int number, int threadCount, int[] array) {
        this.number = number;
        this.threadCount = threadCount;
        this.array = array;
    }

    public MaxFinder(int number, int threadCount) {
        this.number = number;
        this.threadCount = threadCount;
        readArray();
    }

    /**
     * Метод ввода массива
     */
    private void readArray() {
        array = new int[number];
        Scanner in = new Scanner(System.in);
        for (int i = 0; i < number; i++)
            array[i] = in.nextInt();
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

    public void setNumber(int number) {
        this.number = number;
    }
}
