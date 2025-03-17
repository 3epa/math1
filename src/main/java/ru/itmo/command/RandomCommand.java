package ru.itmo.command;

import ru.itmo.exception.IncorrectInputException;
import ru.itmo.model.Matrix;
import ru.itmo.util.MatrixProcessor;
import ru.itmo.util.MatrixReader;
import ru.itmo.util.PrettyPrinter;

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
            PrettyPrinter.printError("Внутренняя ошибка, попробуйте перезапустить приложение или проверить данные файла");
            return;
        } catch (IncorrectInputException e) {
            PrettyPrinter.printError("Данные не соответствуют требуемым ограничениям\n"+e.getMessage());
            return;
        } catch (NumberFormatException e) {
            PrettyPrinter.printError("Одно из введенных значений не является числом");
            return;
        }
        MatrixProcessor.processMatrix(matrix);
    }

    private Matrix generateRandomMatrix() throws IncorrectInputException, IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        PrettyPrinter.printHeader("Введите размерность матрицы:");
        int size = MatrixReader.readSize(reader);

        PrettyPrinter.printHeader("Генерация рандомной матрицы...");
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
