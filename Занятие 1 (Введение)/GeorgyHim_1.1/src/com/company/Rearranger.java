package com.company;
import java.util.Scanner;

public class Rearranger {
    static int size;
    static int[] numbers;

    public static void Swap(int i, int j) {
        int temp = numbers[i];
        numbers[i] = numbers[j];
        numbers[j] = temp;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
	    size = scanner.nextInt();
	    numbers = new int[size];
	    for (int i = 0; i < size; i++) {
	        numbers[i] = scanner.nextInt();
        }

	    // Нахождение минимума и максимума
	    int max = numbers[0];
	    int min = numbers[0];
	    int idxofmax = 0;
	    int idxofmin = 0;
	    for (int i = 1; i < size; i++) {
	        if (numbers[i] < min) {
	            min = numbers[i];
	            idxofmin = i;
            }
	        if (numbers[i] > max) {
	            max = numbers[i];
	            idxofmax = i;
            }
        }

	    // Перестановка элементов массива
        Swap(0, idxofmin);
        if (idxofmax == 0) {
            idxofmax = idxofmin;
        }
	    Swap(size - 1, idxofmax);

	    for (int i = 0; i < size; i++) {
            System.out.print(numbers[i] + " ");
        }
    }
}
