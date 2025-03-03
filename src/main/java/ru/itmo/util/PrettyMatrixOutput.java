package ru.itmo.util;

import ru.itmo.model.Matrix;

public class PrettyMatrixOutput {
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
}
