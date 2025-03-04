package ru.itmo.algo;

import org.apache.commons.math3.linear.*;
import ru.itmo.model.Matrix;

public class MathLibrary {
    double[][] data;
    double[] vectorData;

    public MathLibrary(Matrix matrix) {
        double[][] data = new double[matrix.getSize()][matrix.getSize()];
        double[] vectorData = new double[matrix.getSize()];
        for (int i = 0; i < matrix.getSize(); i++) {
            for (int j = 0; j < matrix.getSize(); j++) {
                data[i][j] = matrix.getData()[i][j];
            }
            vectorData[i] = matrix.getData()[i][matrix.getSize()];
        }
        this.data = data;
        this.vectorData = vectorData;
    }

    public double getDeterminant() {
        RealMatrix realMatrix = MatrixUtils.createRealMatrix(data);

        LUDecomposition luDecomposition = new LUDecomposition(realMatrix);
        return luDecomposition.getDeterminant();
    }

    public double[] getSolution() {
        RealMatrix realMatrix = MatrixUtils.createRealMatrix(data);

        LUDecomposition luDecomposition = new LUDecomposition(realMatrix);
        RealVector vector = new ArrayRealVector(vectorData);
        RealVector solution = luDecomposition.getSolver().solve(vector);
        return solution.toArray();
    }
}
