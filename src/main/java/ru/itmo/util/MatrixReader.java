package ru.itmo.util;

import ru.itmo.exception.IncorrectInputException;
import ru.itmo.model.Matrix;

import java.io.BufferedReader;
import java.io.IOException;

public class MatrixReader {

    public static Matrix readMatrix(BufferedReader reader, int size) throws IOException {
        String[][] stringMatrix = new String[size][size + 1];
        for (int i = 0; i < size; i++) {
            String matrixLine = reader.readLine().strip();
            matrixLine = matrixLine.replaceAll(",", ".");
            stringMatrix[i] = matrixLine.split(" ");
        }

        double[][] matrix = new double[size][size + 1];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size + 1; j++) {
                matrix[i][j] = Double.parseDouble(stringMatrix[i][j].strip());
            }
        }
        return new Matrix(size, matrix);
    }
    public static int readSize(BufferedReader reader) throws IOException, IncorrectInputException {
        int size = Integer.parseInt(reader.readLine().strip());
        if (size > 20 || size < 1) {
            throw new IncorrectInputException("Размер не находится в требуемом диапазоне( {1, ..., 20})");
        }
        return size;
    }
}