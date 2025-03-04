package ru.itmo.algo;

import ru.itmo.model.Matrix;
import ru.itmo.util.PrettyMatrixOutput;

public class GaussMethod {
    public static void compute(Matrix matrix) {
        double[][] data = matrixTriangulation(matrix);
        System.out.println("Матрица, приведенная к треугольному виду:");
        PrettyMatrixOutput.printMatrix(matrix);
        Matrix matrix1 = new Matrix(matrix.getSize()-1, matrix.getData());
        AmountOfSolution amountOfSolution = findAmountOfSolution(matrix, matrix1);
        if (amountOfSolution != AmountOfSolution.ONE) {
            System.out.println("Система имеет не ровно одно решение, а " + amountOfSolution);
            return;
        }
        System.out.println("Определитель матрицы:");
        System.out.println(findDeterminant(matrix));
        double[] solution = findSolution(new Matrix(matrix.getSize(), data));
        for (int i = 0; i < matrix.getSize(); i++) {
            System.out.println("x_" + (i + 1) + "=" + solution[i]);
        }
    }

    private static double[][] matrixTriangulation(Matrix matrix) {
        double[][] data = matrix.getData();
        int size = matrix.getSize();
        for (int i = 0; i < size - 1; i++) {
            if (data[i][i] == 0) {
                for (int k = i + 1; k < size; k++) {
                    if (data[k][i] != 0) {
                        matrix.swapRows(i, k);
                        matrix.changeDetSign();
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
                s = s + data[i][j] * solution[j];
            }
            double x = (data[i][size] - s) / data[i][i];
            solution[i] = x;
        }
        return solution;
    }

    private static double findDeterminant(Matrix matrix) {
        double determinant = 1;
        for (int i = 0; i < matrix.getSize(); i++) {
            determinant = determinant * matrix.getData()[i][i];
        }
        return determinant * matrix.getDetSign();
    }

    private static int findRang(Matrix matrix) {
        int counter = 0;
        for (int i = 0; i < matrix.getSize(); i++) {
            boolean flag = true;
            for (int j = 0; j < matrix.getSize(); j++) {
                if (matrix.getData()[i][j] != 0) {
                    flag = false;
                }
            }
            if (flag) {
                counter++;
            }
        }
        return matrix.getSize() - counter;
    }

    private static AmountOfSolution findAmountOfSolution(Matrix matrix1, Matrix matrix2) {
        if (findRang(matrix1) != findRang(matrix2)) {
            return AmountOfSolution.ZERO;
        }

        if (findRang(matrix1) == findRang(matrix2) && findRang(matrix1) < matrix1.getSize()) {
            return AmountOfSolution.INFINITY;
        }

        return AmountOfSolution.ONE;
    }
}
