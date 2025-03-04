package ru.itmo.command;

import ru.itmo.algo.GaussMethod;
import ru.itmo.algo.MathLibrary;
import ru.itmo.exception.IncorrectInputException;
import ru.itmo.exception.NoSolutionExistsException;
import ru.itmo.model.Matrix;
import ru.itmo.util.PrettyMatrixOutput;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

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
        try {
            GaussMethod.compute(matrix);
        } catch (NoSolutionExistsException e) {
            System.out.println(e.getMessage());
            return;
        }
        MathLibrary mathLibrary = new MathLibrary(matrix);
        System.out.println("Определитель через библиотеку:");
        System.out.println(mathLibrary.getDeterminant());
        System.out.println("Решение через библиотеку:");
        System.out.println(Arrays.toString(mathLibrary.getSolution()));
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
