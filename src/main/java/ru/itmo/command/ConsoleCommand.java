package ru.itmo.command;

import ru.itmo.algo.AmountOfSolution;
import ru.itmo.algo.GaussMethod;
import ru.itmo.algo.MathLibrary;
import ru.itmo.exception.IncorrectInputException;
import ru.itmo.exception.MatrixIsNotTriangleException;
import ru.itmo.model.Matrix;
import ru.itmo.util.PrettyPrinter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


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
            PrettyPrinter.printError("Внутренняя ошибка, попробуйте перезапустить приложение");
            return;
        } catch (IncorrectInputException e) {
            PrettyPrinter.printError("Данные не соответствуют требуемым ограничениям\n" + e.getMessage());
            return;
        } catch (NumberFormatException e) {
            PrettyPrinter.printError("Одно из введенных значений не является числом");
            return;
        } catch (ArrayIndexOutOfBoundsException e) {
            PrettyPrinter.printError("Некорректное количество введенных данных");
            return;
        }
        PrettyPrinter.printHeader("Изначальная матрица:");
        PrettyPrinter.printMatrix(matrix);

        PrettyPrinter.printMainHeader("Мой метод Гаусса:");
        GaussMethod gaussMethod = new GaussMethod();

        // Приводим матрицу к треугольному виду
        PrettyPrinter.printHeader("Матрица, приведенная к треугольному виду:");
        Matrix triangularMatrix = gaussMethod.matrixTriangulation(matrix);
        PrettyPrinter.printMatrix(matrix);

        // Ищем количество решений
        try {
            AmountOfSolution amountOfSolution = gaussMethod.findAmountOfSolution(triangularMatrix);
            if (amountOfSolution != AmountOfSolution.ONE) {
                PrettyPrinter.printError("Система имеет не ровно одно решение, а " + amountOfSolution);
                return;
            }
        } catch (MatrixIsNotTriangleException e) {
            PrettyPrinter.printError(e.getMessage());
            return;
        }

        // Ищем определитель
        double determinant;
        try {
            determinant = gaussMethod.findDeterminant(triangularMatrix);
        } catch (MatrixIsNotTriangleException e) {
            PrettyPrinter.printError(e.getMessage());
            return;
        }
        PrettyPrinter.printDeterminant(determinant);

        // Ищем решение системы
        double[] solution = gaussMethod.findSolution(triangularMatrix);
        PrettyPrinter.printSolution(solution);

        // Считаем погрешность вычислений
        double[] residuals = gaussMethod.findResiduals(matrix, solution);
        PrettyPrinter.printResiduals(residuals);

        if (!gaussMethod.isSolutionCorrect(residuals)) {
            PrettyPrinter.printError("Не удалось достигнуть требуемой точности вычислений");
        }
        PrettyPrinter.printMainHeader("Использование библиотеки:");
        MathLibrary mathLibrary = new MathLibrary(matrix);
        try {
            double libraryDeterminant = mathLibrary.getDeterminant();
            PrettyPrinter.printDeterminant(libraryDeterminant);
        } catch (Exception e) {
            PrettyPrinter.printError("Сторонней библиотеке не удалось найти определитель");
        }
        try {
            double[] librarySolution = mathLibrary.getSolution();
            PrettyPrinter.printSolution(librarySolution);
        } catch (Exception e) {
            PrettyPrinter.printError("Сторонней библиотеке не удалось найти решение СЛАУ");
        }

    }

    private Matrix readFromConsole() throws IOException, IncorrectInputException, NumberFormatException, ArrayIndexOutOfBoundsException {
        PrettyPrinter.printMainHeader("Введите размерность матрицы:");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int size = Integer.parseInt(reader.readLine().strip());
        if (size > 20 || size < 1) {
            throw new IncorrectInputException("Размер не находится в требуемоемом диапазоне( {1, ..., 20})");
        }

        PrettyPrinter.printMainHeader("Введите строки матрицы:");
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
}
