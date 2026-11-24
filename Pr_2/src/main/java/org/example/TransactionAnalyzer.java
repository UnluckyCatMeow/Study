package org.example;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class TransactionAnalyzer {

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    public static final DateTimeFormatter MONTH_YEAR_FORMATTER = DateTimeFormatter.ofPattern("MM-yyyy");

    public static LocalDate parseDate(String date) {
        return LocalDate.parse(date, DATE_FORMATTER);
    }

    public static double calculateTotalBalance(List<Transaction> transactions) {
        double balance = 0;
        for (Transaction transaction : transactions) {
            balance += transaction.getAmount();
        }
        return balance;
    }

    public static int countTransactionsByMonth(List<Transaction> transactions, String monthYear) {
        int count = 0;
        for (Transaction transaction : transactions) {
            String transactionMonthYear = parseDate(transaction.getDate()).format(MONTH_YEAR_FORMATTER);
            if (transactionMonthYear.equals(monthYear)) {
                count++;
            }
        }
        return count;
    }

    public static List<Transaction> findTopExpenses(List<Transaction> transactions) {
        return transactions.stream()
                .filter(t -> t.getAmount() < 0)                        // лише витрати (від'ємні значення)
                .sorted(Comparator.comparing(Transaction::getAmount))  // сортування: -9100 раніше за -450
                .limit(10)                                             // перші 10 записів
                .collect(Collectors.toList());
    }

    //Самостійна робота
    public static List<Transaction> findExpensesInPeriod(List<Transaction> transactions,
                                                         LocalDate from, LocalDate to) {
        return transactions.stream()
                .filter(t -> t.getAmount() < 0)
                .filter(t -> {
                    LocalDate date = parseDate(t.getDate());
                    return !date.isBefore(from) && !date.isAfter(to);
                })
                .collect(Collectors.toList());
    }

    public static Optional<Transaction> findLargestExpenseInPeriod(List<Transaction> transactions,
                                                                   LocalDate from, LocalDate to) {
        return findExpensesInPeriod(transactions, from, to).stream()
                .min(Comparator.comparing(Transaction::getAmount));
    }

    public static Optional<Transaction> findSmallestExpenseInPeriod(List<Transaction> transactions,
                                                                    LocalDate from, LocalDate to) {
        return findExpensesInPeriod(transactions, from, to).stream()
                .max(Comparator.comparing(Transaction::getAmount));
    }

    public static Map<String, Double> calculateExpensesByCategory(List<Transaction> transactions) {
        Map<String, Double> sums = transactions.stream()
                .filter(t -> t.getAmount() < 0)
                .collect(Collectors.groupingBy(Transaction::getDescription,
                        Collectors.summingDouble(t -> -t.getAmount())));

        return sums.entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (a, b) -> a, LinkedHashMap::new));
    }

    public static Map<YearMonth, Double> calculateExpensesByMonth(List<Transaction> transactions) {
        return transactions.stream()
                .filter(t -> t.getAmount() < 0)
                .collect(Collectors.groupingBy(t -> YearMonth.from(parseDate(t.getDate())),
                        TreeMap::new,
                        Collectors.summingDouble(t -> -t.getAmount())));
    }
}
