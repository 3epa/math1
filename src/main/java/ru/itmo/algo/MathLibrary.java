package ru.itmo.algo;
import org.apache.commons.math3.linear.*;

public class MathLibrary {
    public static double getDeterminant(double[][] matrix) {
        return new LUDecomposition(MatrixUtils.createRealMatrix(matrix)).getDeterminant();
    }
}
