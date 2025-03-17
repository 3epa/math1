package ru.itmo.util;

import ru.itmo.model.Matrix;

public class PrettyPrinter {

    public static final String ANSI_RESET = "\u001B[0m";

    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";

    public static void printMatrix(Matrix matrix) {
        int size = matrix.getSize();
        double[][] data = matrix.getData();

        int maxLength = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size + 1; j++) {
                int length = String.valueOf(data[i][j]).length();
                if (length > maxLength) {
                    maxLength = length;
                }
            }
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size + 1; j++) {
                System.out.printf("%-" + (maxLength + 2) + "s", data[i][j]);
            }
            System.out.println();
        }
    }
    public static void printDeterminant(double determinant) {
        printHeader("Определитель матрицы:");
        System.out.println(determinant);
    }

    public static void printSolution(double[] solution) {
        printHeader("Решение:");
        for (int i = 0; i < solution.length; i++)  {
            System.out.println("x_" + (i + 1) + "=" + solution[i]);
        }
    }

    public static void printResiduals(double[] residuals) {
        printHeader("Вектор невязки:");
        for (int i = 0; i < residuals.length; i++) {
            System.out.println("r_" + (i + 1) + "=" + residuals[i]);
        }
    }
    public static void printError(String message) {
        System.out.println(ANSI_RED + message + ANSI_RESET);
    }

    public static void printHeader(String message) {
        System.out.println(ANSI_YELLOW + message + ANSI_RESET);
    }

    public static void printMainHeader(String message) {
        System.out.println(ANSI_GREEN + message + ANSI_RESET);
    }
}
