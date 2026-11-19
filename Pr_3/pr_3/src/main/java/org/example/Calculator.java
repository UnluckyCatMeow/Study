package org.example;

public class Calculator {

    private void check(double a, double b) throws InvalidInputException {
        if (Double.isNaN(a) || Double.isInfinite(a) || Double.isNaN(b) || Double.isInfinite(b)) {
            throw new InvalidInputException("Число має бути скінченним (не NaN і не Infinity)");
        }
    }

    public double add(double a, double b) throws InvalidInputException {
        check(a, b);
        return a + b;
    }

    public double subtract(double a, double b) throws InvalidInputException {
        check(a, b);
        return a - b;
    }

    public double multiply(double a, double b) throws InvalidInputException {
        check(a, b);
        return a * b;
    }

    public double divide(double a, double b) throws InvalidInputException {
        check(a, b);
        if (b == 0) {
            throw new ArithmeticException("Ділення на нуль неможливе");
        }
        return a / b;
    }
}