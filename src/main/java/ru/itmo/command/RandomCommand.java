package ru.itmo.command;

import ru.itmo.algo.GaussMethod;
import ru.itmo.exception.IncorrectInputException;
import ru.itmo.model.Matrix;
import ru.itmo.util.PrettyMatrixOutput;
import org.apache.commons.math3.linear.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RandomCommand implements Command {
    @Override
    public String getName() {
        return "random";
    }

    @Override
    public void execute() {
        Matrix matrix;
        try {
            matrix = generateRandomMatrix();
        } catch (IOException e) {
            System.out.println("Внутренняя ошибка, попробуйте перезапустить приложение или проверить данные файла");
            return;
        } catch (IncorrectInputException e) {
            System.out.println("Данные не соответствуют требуемым ограничениям");
            System.out.println(e.getMessage());
            return;
        } catch (NumberFormatException e) {
            System.out.println("Одно из введенных значений не является числом");
            return;
        }
        System.out.println("Изначальная матрица: ");
        PrettyMatrixOutput.printMatrix(matrix);
        GaussMethod.compute(matrix);
        double[][] data = new double[matrix.getSize()][matrix.getSize()];
        double[] vectorData = new double[matrix.getSize()];
        for (int i = 0; i < matrix.getSize(); i++) {
            for (int j = 0; j < matrix.getSize(); j++) {
                data[i][j] = matrix.getData()[i][j];
            }
            vectorData[i] = matrix.getData()[i][matrix.getSize()];
        }
        RealMatrix realMatrix = MatrixUtils.createRealMatrix(data);

        LUDecomposition luDecomposition = new LUDecomposition(realMatrix);

        System.out.println("Определитель через библиотеку:");
        System.out.println(luDecomposition.getDeterminant());
        System.out.println("Решение через библиотеку:");
        RealVector vector = new ArrayRealVector(vectorData);
        RealVector solution = luDecomposition.getSolver().solve(vector);
        System.out.println(solution);

    }

    private Matrix generateRandomMatrix() throws IncorrectInputException, IOException {
        System.out.println("Введите размерность матрицы:");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));


        int size = Integer.parseInt(reader.readLine().strip());
        if (size > 20 || size < 1) {
            throw new IncorrectInputException("Variable size not in required range(1 <= size <= 20)");
        }
        System.out.println("Генерация рандомной матрицы...");
        double[][] matrix = new double[size][size + 1];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size + 1; j++) {
                matrix[i][j] = Math.random() * 100 - 50;
            }
        }
        reader.close();
        return new Matrix(size, matrix);
    }
}
