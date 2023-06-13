package myApp;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class MyApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите данные через пробел: (ФИ0 00.00.1900 номер пол(м или ж): ");
        String input = scanner.nextLine().trim();
        String[] data = input.split("\\s+");

        if (data.length < 6) {
            System.err.println("Ошибка: введено мало данных");
            return;
        }
        else if (data.length > 6){
            System.err.println("Ошибка: введено много данных");
            return;
        }

        String lastName = data[0];
        String firstName = data[1];
        String middleName = data[2];
        String dateOfBirth = data[3];
        String phoneNumber = data[4];
        String gender = data[5];

        try {
            validateData(lastName, firstName, middleName, dateOfBirth, phoneNumber, gender);
        } catch (InvalidDataException e) {
            System.err.println(e.getMessage());
            return;
        }

        String filename = lastName + ".txt";
        try (FileWriter writer = new FileWriter(filename, true)) {
            writer.write(lastName + " " + firstName + " " + middleName + " " + dateOfBirth + " " + phoneNumber + " " + gender + "\n");
            System.out.println("Данные успешно записаны в файл " + filename);
        } catch (IOException e) {
            System.err.println("Ошибка записи в файл: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void validateData(String lastName, String firstName, String middleName,
                                     String dateOfBirth, String phoneNumber, String gender) throws InvalidDataException {
        if (!dateOfBirth.matches("\\d{2}\\.\\d{2}\\.\\d{4}")) {
            throw new InvalidDataException("Ошибка: неверный формат даты рождения");
        }
        if (!phoneNumber.matches("\\d+")) {
            throw new InvalidDataException("Ошибка: номер телефона должен быть целым беззнаковым числом");
        }
        if (!gender.matches("[мжМЖ]")) {
            throw new InvalidDataException("Ошибка: пол должен быть задан символом 'м' или 'ж'");
        }
    }

    private static class InvalidDataException extends Exception {
        public InvalidDataException(String message) {
            super(message);
        }
    }
}
