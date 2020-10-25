package com.company;
import java.util.Scanner;
import java.util.Locale;

public class ByteFormat {
    static String[] Units = {"B", "KB", "MB", "GB", "TB", "PB", "EB"};

    public static void printBytes(long size) {
        int order = 0; // Порядок единицы измерения
        double x = size;
        while (x >= 1024) {
            x /= 1024;
            order++;
        }
        System.out.println(String.format(Locale.ENGLISH,"%.1f", x) + " " + Units[order]);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        long size = scanner.nextLong();
        printBytes(size);
    }
}
