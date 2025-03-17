package ru.itmo.command;

import ru.itmo.algo.MathLibrary;
import ru.itmo.exception.IncorrectInputException;
import ru.itmo.model.Matrix;
import ru.itmo.util.MatrixProcessor;
import ru.itmo.util.MatrixReader;
import ru.itmo.util.PrettyPrinter;

import java.io.*;
import java.util.Arrays;

public class FileCommand implements Command {
    @Override
    public String getName() {
        return "file";
    }

    @Override
    public void execute() {
        Matrix matrix;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Введите имя файла: ");
            String fileName = reader.readLine().strip();

            System.out.println("Чтение данных с файла...");
            matrix = readFromFile(fileName);
        } catch (FileNotFoundException e) {
            System.out.println("Указанный файл не существует");
            return;
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
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Некорректное количество введенных данных");
            return;
        }
        MatrixProcessor.processMatrix(matrix);
    }

    private Matrix readFromFile(String fileName) throws IOException, IncorrectInputException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));

        int size = MatrixReader.readSize(reader);

        return MatrixReader.readMatrix(reader, size);
    }
}
