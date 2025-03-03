package ru.itmo.model;

public class Matrix {
    private final int size;
    private final double[][] matrix;

    public Matrix(int size, double[][] matrix) {
        this.size = size;
        this.matrix = matrix;
    }

    public int getSize() {
        return size;
    }

    public double[][] getMatrix() {
        return matrix;
    }

    public void swapRows(int firstRow, int secondRow) {
        double[] temp = this.matrix[firstRow];
        this.matrix[firstRow] = this.matrix[secondRow];
        this.matrix[secondRow] = temp;
    }
}
