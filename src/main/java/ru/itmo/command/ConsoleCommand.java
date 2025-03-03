package ru.itmo.command;

import ru.itmo.exception.IncorrectInputException;
import ru.itmo.model.Matrix;

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
        Matrix matrix = null;
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
        for (int i = 0; i < matrix.getSize(); i++) {
            for (int j = 0; j < matrix.getSize(); j++) {
                System.out.print(matrix.getData()[i][j] + " ");
            }
            System.out.println();
        }
    }

    private Matrix readFromConsole() throws IOException, IncorrectInputException, NumberFormatException, ArrayIndexOutOfBoundsException {
        System.out.println("Введите размерность матрицы:");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line = reader.readLine().strip();

        int size = Integer.parseInt(line);
        if (size > 20 || size < 1) {
            throw new IncorrectInputException("Variable size not in required range(1 <= size <= 20)");
        }
        System.out.println("Введите строки матрицы:");
        String[][] stringMatrix = new String[size][size];
        for (int i = 0; i < size; i++) {
            String matrixLine = reader.readLine().strip();
            matrixLine = matrixLine.replaceAll(",", ".");
            stringMatrix[i] = matrixLine.split(" ");
        }

        double[][] matrix = new double[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = Double.parseDouble(stringMatrix[i][j].strip());
            }
        }
        reader.close();
        return new Matrix(size, matrix);
    }
}
