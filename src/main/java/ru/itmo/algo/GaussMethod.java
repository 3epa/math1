package ru.itmo.algo;

import ru.itmo.exception.MatrixIsNotTriangleException;
import ru.itmo.model.Matrix;

public class GaussMethod {
    public Matrix matrixTriangulation(Matrix matrix) {
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
        matrix.setTriangle(true);
        return matrix;
    }

    public double[] findSolution(Matrix matrix) {
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

    public double findDeterminant(Matrix matrix) throws MatrixIsNotTriangleException {
        if (!matrix.isTriangle()) {
            throw new MatrixIsNotTriangleException("Матрица не приведена к треугольному виду, невозможно найти определитель");
        }
        double determinant = 1;
        for (int i = 0; i < matrix.getSize(); i++) {
            determinant = determinant * matrix.getData()[i][i];
        }
        return determinant * matrix.getDetSign();
    }

    public int findRang(Matrix matrix, boolean isBColumn) throws MatrixIsNotTriangleException {
        if (!matrix.isTriangle()) {
            throw new MatrixIsNotTriangleException("Матрица не приведена к треугольному виду, невозможно найти определитель");
        }
        int counter = 0;
        for (int i = 0; i < matrix.getSize(); i++) {
            boolean flag = true;
            if (isBColumn) {
                for (int j = 0; j < matrix.getSize()+1; j++) {
                    if (matrix.getData()[i][j] != 0) {
                        flag = false;
                    }
                }
            } else {
                for (int j = 0; j < matrix.getSize(); j++) {
                    if (matrix.getData()[i][j] != 0) {
                        flag = false;
                    }
                }
            }
            if (flag) {
                counter++;
            }
        }
        return matrix.getSize() - counter;
    }

    public AmountOfSolution findAmountOfSolution(Matrix matrix) throws MatrixIsNotTriangleException {
        int rang1 = findRang(matrix, true);
        int rang2 = findRang(matrix, false);
        if (rang1 != rang2) {
            return AmountOfSolution.ZERO;
        }

        if (rang1 < matrix.getSize()) {
            return AmountOfSolution.INFINITY;
        }

        return AmountOfSolution.ONE;
    }
    public double[] findResiduals(Matrix matrix, double[] x) {
        double[] residuals = new double[matrix.getSize()];
        for (int i = 0; i < matrix.getSize(); i++) {
            double s = 0;
            for (int j = 0; j < matrix.getSize(); j++) {
                s = s + matrix.getData()[i][j] * x[j];
            }
            residuals[i] = s-matrix.getData()[i][matrix.getSize()];
        }
        return residuals;
    }

    public boolean isSolutionCorrect(double[] r) {
        double s = 0;
        double eps = 0.0001;
        for (double v : r) {
            s = s + v * v;
        }
        return Math.sqrt(s) < eps;
    }
}
