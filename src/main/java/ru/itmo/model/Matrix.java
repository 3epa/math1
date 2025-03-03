package ru.itmo.model;

public class Matrix {
    private final int size;
    private final double[][] data;

    public Matrix(int size, double[][] data) {
        this.size = size;
        this.data = data;
    }

    public int getSize() {
        return size;
    }

    public double[][] getMatrix() {
        return data;
    }

    public void swapRows(int firstRow, int secondRow) {
        double[] temp = this.data[firstRow];
        this.data[firstRow] = this.data[secondRow];
        this.data[secondRow] = temp;
    }
}
