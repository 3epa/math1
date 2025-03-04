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

public class ConsoleCommand implements Command {
    @Override
    public String getName() {
        return "console";
    }

    @Override
    public void execute() {
        Matrix matrix;
        try {
            matrix = readFromConsole();
        } catch (IOException e) {
            System.out.println("Внутренняя ошибка, попробуйте перезапустить приложение");
            return;
        } catch (IncorrectInputException e) {
            System.out.println("Данные не соответствуют требуемым ограничениям");
            System.out.println(e.getMessage());
            return;
        } catch (NumberFormatException e) {
            System.out.println("Одно из введенных значений не является числом");
            return;
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Некорректное количество введенных данных");
            return;
        }
        System.out.println("Изначальная матрица: ");
        PrettyMatrixOutput.printMatrix(matrix);
        System.out.println("Мой метод Гаусса:");
        try {
            GaussMethod.compute(matrix);
        } catch (NoSolutionExistsException e) {
            System.out.println(e.getMessage());
            return;
        }
        printDeterminantAndSolution(matrix);
    }

    private Matrix readFromConsole() throws IOException, IncorrectInputException, NumberFormatException, ArrayIndexOutOfBoundsException {
        System.out.println("Введите размерность матрицы:");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int size = Integer.parseInt(reader.readLine().strip());
        if (size > 20 || size < 1) {
            throw new IncorrectInputException("Variable size not in required range(1 <= size <= 20)");
        }
        System.out.println("Введите строки матрицы:");
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
        reader.close();
        return new Matrix(size, matrix);
    }

    private void printDeterminantAndSolution(Matrix matrix) {
        MathLibrary mathLibrary = new MathLibrary(matrix);
        try {
            System.out.println("Определитель через библиотеку:");
            System.out.println(mathLibrary.getDeterminant());
        } catch (Exception e) {
            System.out.println("Сторонней библиотеке не удалось найти определитель");
        }
        try {
            System.out.println("Решение через библиотеку:");
            System.out.println(Arrays.toString(mathLibrary.getSolution()));
        } catch (Exception e) {
            System.out.println("Сторонней библиотеке не удалось найти решение СЛАУ");
        }
    }
}
