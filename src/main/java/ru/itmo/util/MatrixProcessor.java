package ru.itmo.util;

import ru.itmo.algo.AmountOfSolution;
import ru.itmo.algo.GaussMethod;
import ru.itmo.algo.MathLibrary;
import ru.itmo.exception.MatrixIsNotTriangleException;
import ru.itmo.model.Matrix;

public class MatrixProcessor {

    public static void processMatrix(Matrix matrix) {
        processMatrixWithCustom(matrix);
        processMatrixWithLibrary(matrix);
    }

    public static void processMatrixWithLibrary(Matrix matrix) {
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

    public static void processMatrixWithCustom(Matrix matrix) {
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
    }
}