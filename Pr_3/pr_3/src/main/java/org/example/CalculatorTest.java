package org.example;

public class CalculatorTest {

    private static int passed = 0;
    private static int failed = 0;

    private interface Action {
        void run() throws Exception;
    }

    private static void check(String name, boolean condition) {
        if (condition) {
            passed++;
            System.out.println("ПРОЙДЕНО: " + name);
        } else {
            failed++;
            System.out.println("ПРОВАЛЕНО: " + name);
        }
    }

    private static void checkThrows(String name, Class<? extends Exception> expected, Action action) {
        try {
            action.run();
            failed++;
            System.out.println("ПРОВАЛЕНО: " + name + " (виключення не виникло)");
        } catch (Exception e) {
            if (expected.isInstance(e)) {
                passed++;
                System.out.println("ПРОЙДЕНО: " + name);
            } else {
                failed++;
                System.out.println("ПРОВАЛЕНО: " + name + " (інше виключення: " + e.getClass().getSimpleName() + ")");
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Calculator calculator = new Calculator();

        check("Додавання 2 + 3 = 5", calculator.add(2, 3) == 5.0);
        check("Віднімання 2 - 3 = -1", calculator.subtract(2, 3) == -1.0);
        check("Множення 2 * 3 = 6", calculator.multiply(2, 3) == 6.0);
        check("Ділення 5 / 2 = 2.5", calculator.divide(5, 2) == 2.5);

        checkThrows("Ділення на нуль", ArithmeticException.class,
                () -> calculator.divide(5, 0));

        checkThrows("NaN викликає InvalidInputException", InvalidInputException.class,
                () -> calculator.add(Double.NaN, 1));

        checkThrows("Infinity викликає InvalidInputException", InvalidInputException.class,
                () -> calculator.multiply(1, Double.POSITIVE_INFINITY));

        checkThrows("Рядок замість числа", NumberFormatException.class,
                () -> Double.parseDouble("abc"));

        System.out.println();
        System.out.println("Пройдено: " + passed + ", провалено: " + failed);
    }
}