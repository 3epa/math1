package ru.itmo.command;


import ru.itmo.exception.IncorrectInputException;

import ru.itmo.model.Matrix;
import ru.itmo.util.MatrixProcessor;
import ru.itmo.util.MatrixReader;
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
        MatrixProcessor.processMatrix(matrix);
    }

    private Matrix readFromConsole() throws IOException, IncorrectInputException, NumberFormatException, ArrayIndexOutOfBoundsException {
        PrettyPrinter.printMainHeader("Введите размерность матрицы:");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int size = MatrixReader.readSize(reader);

        PrettyPrinter.printMainHeader("Введите строки матрицы:");
        return MatrixReader.readMatrix(reader, size);
    }
}
