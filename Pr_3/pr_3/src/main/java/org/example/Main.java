package org.example;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Calculator calculator = new Calculator();

        try {
            System.out.print("Введіть перше число: ");
            double a = Double.parseDouble(scanner.nextLine());

            System.out.print("Введіть друге число: ");
            double b = Double.parseDouble(scanner.nextLine());

            System.out.print("Введіть операцію (+, -, *, /): ");
            String operation = scanner.nextLine();

            double result = switch (operation) {
                case "+" -> calculator.add(a, b);
                case "-" -> calculator.subtract(a, b);
                case "*" -> calculator.multiply(a, b);
                case "/" -> calculator.divide(a, b);
                default -> throw new IllegalArgumentException("Невідома операція: " + operation);
            };

            System.out.println("Результат: " + result);
        } catch (NumberFormatException e) {
            System.out.println("Помилка: введено не число. Дробову частину пишіть через крапку.");
        } catch (InvalidInputException e) {
            System.out.println("Помилка вхідних даних: " + e.getMessage());
        } catch (ArithmeticException e) {
            System.out.println("Математична помилка: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка: " + e.getMessage());
        } finally {
            System.out.println("Обробку запиту завершено.");
        }
    }
}