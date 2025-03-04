package ru.itmo.model;

public class Matrix {
    private final int size;
    private final double[][] data;
    private int detSign = 1;

    public Matrix(int size, double[][] data) {
        this.size = size;
        this.data = data;
    }

    public int getDetSign() {
        return detSign;
    }

    public void changeDetSign() {
        this.detSign = -detSign;
    }

    public int getSize() {
        return size;
    }

    public double[][] getData() {
        return data;
    }

    public void swapRows(int firstRow, int secondRow) {
        double[] temp = this.data[firstRow];
        this.data[firstRow] = this.data[secondRow];
        this.data[secondRow] = temp;
    }
}
