package org.example;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class TransactionReportGenerator {

    public static final double AMOUNT_PER_SYMBOL = 1000.0;
    public static final char SYMBOL = '*';

    public static void printBalanceReport(double totalBalance) {
        System.out.println("Загальний баланс: " + totalBalance);
    }

    public static void printTransactionsCountByMonth(String monthYear, int count) {
        System.out.println("Кількість транзакцій за " + monthYear + ": " + count);
    }

    public static void printTopExpensesReport(List<Transaction> topExpenses) {
        System.out.println("10 найбільших витрат:");
        for (Transaction expense : topExpenses) {
            System.out.println(expense.getDescription() + ": " + expense.getAmount());
        }
    }

    // Самостійна робота

    public static void printLargestAndSmallestExpenses(LocalDate from, LocalDate to,
                                                       Optional<Transaction> largest,
                                                       Optional<Transaction> smallest) {
        System.out.println("Витрати за період " + from.format(TransactionAnalyzer.DATE_FORMATTER)
                + " — " + to.format(TransactionAnalyzer.DATE_FORMATTER) + ":");
        System.out.println("  Найбільша: " + largest.map(TransactionReportGenerator::formatExpense)
                .orElse("витрат немає"));
        System.out.println("  Найменша: " + smallest.map(TransactionReportGenerator::formatExpense)
                .orElse("витрат немає"));
    }

    public static void printExpensesByCategoryReport(Map<String, Double> expensesByCategory) {
        System.out.print(buildExpensesByCategoryReport(expensesByCategory));
    }

    public static void printExpensesByMonthReport(Map<YearMonth, Double> expensesByMonth) {
        System.out.print(buildExpensesByMonthReport(expensesByMonth));
    }

    public static String buildExpensesByCategoryReport(Map<String, Double> expensesByCategory) {
        StringBuilder report = new StringBuilder("Витрати по категоріях (" + legend() + "):\n");
        for (Map.Entry<String, Double> entry : expensesByCategory.entrySet()) {
            report.append(formatRow(entry.getKey(), entry.getValue()));
        }
        return report.toString();
    }

    public static String buildExpensesByMonthReport(Map<YearMonth, Double> expensesByMonth) {
        StringBuilder report = new StringBuilder("Витрати по місяцях (" + legend() + "):\n");
        for (Map.Entry<YearMonth, Double> entry : expensesByMonth.entrySet()) {
            String month = entry.getKey().format(TransactionAnalyzer.MONTH_YEAR_FORMATTER);
            report.append(formatRow(month, entry.getValue()));
        }
        return report.toString();
    }

    public static String buildBar(double amount) {
        if (amount <= 0) {
            return "";
        }
        int count = (int) Math.max(1, Math.round(amount / AMOUNT_PER_SYMBOL));
        return String.valueOf(SYMBOL).repeat(count);
    }

    private static String formatRow(String label, double amount) {
        return String.format("  %-22s | %s %.2f%n", label, buildBar(amount), amount);
    }

    private static String legend() {
        return "'" + SYMBOL + "' = " + (int) AMOUNT_PER_SYMBOL + " грн";
    }

    private static String formatExpense(Transaction t) {
        return t.getDescription() + " (" + t.getDate() + "): " + t.getAmount();
    }
}
