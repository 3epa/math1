package ru.itmo.algo;

import ru.itmo.model.Matrix;

public class GaussMethod {
    public static void compute(Matrix matrix) {
        double[][] data = matrixTriangulation(matrix);
        for (int i = 0; i < matrix.getSize(); i++) {
            for (int j = 0; j < matrix.getSize() + 1; j++) {
                System.out.print(data[i][j] + " ");
            }
            System.out.println();
        }
        double[] solution = findSolution(new Matrix(matrix.getSize(), data));
        for (int i = 0; i < matrix.getSize(); i++) {
            System.out.println("x_" + (i + 1) + "=" + solution[i]);
        }
    }

    private static double[][] matrixTriangulation(Matrix matrix) {
        double[][] data = matrix.getData();
        int detSign = 1;
        int size = matrix.getSize();
        for (int i = 0; i < size - 1; i++) {
            if (data[i][i] == 0) {
                for (int k = i + 1; k < size; k++) {
                    if (data[k][i] != 0) {
                        matrix.swapRows(i, k);
                        detSign = detSign * (-1);
                        break;
                    }
                }
            }
            for (int j = i + 1; j < size; j++) {
                double temp = data[j][i];
                for (int k = 0; k < size + 1; k++) {
                    data[j][k] = data[j][k] - data[i][k] * temp / data[i][i];
                }
            }
        }
        return data;
    }

    private static double[] findSolution(Matrix matrix) {
        int size = matrix.getSize();
        double[][] data = matrix.getData();
        double[] solution = new double[size];
        for (int i = size - 1; i >= 0; i--) {
            double s = 0;
            for (int j = size - 1; j > i; j--) {
                s = s + data[i][j]*solution[j];
            }
            double x = (data[i][size] - s) / data[i][i];
            solution[i] = x;
        }
        return solution;
    }
}
