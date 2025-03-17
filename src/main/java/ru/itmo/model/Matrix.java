package ru.itmo.model;

public class Matrix {
    private final int size;
    private final double[][] data;
    private int detSign = 1;
    private boolean isTriangle = false;

    public Matrix(int size, double[][] data) {
        this.size = size;
        this.data = data;
    }

    public Matrix copy() {
        double[][] copiedData = new double[size][size + 1];
        for (int i = 0; i < size; i++) {
            System.arraycopy(data[i], 0, copiedData[i], 0, size + 1);
        }
        return new Matrix(size, copiedData);
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

    public boolean isNotTriangle() {
        return !isTriangle;
    }

    public void setTriangle(boolean triangle) {
        isTriangle = triangle;
    }
}
