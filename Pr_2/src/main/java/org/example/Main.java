package org.example;

import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String filePath = "https://informer.com.ua/dut/java/pr2.csv";

        List<Transaction> transactions = TransactionCSVReader.readTransactions(filePath);
        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }
        System.out.println();

        double totalBalance = TransactionAnalyzer.calculateTotalBalance(transactions);
        TransactionReportGenerator.printBalanceReport(totalBalance);

        String monthYear = "01-2024";
        int transactionsCount = TransactionAnalyzer.countTransactionsByMonth(transactions, monthYear);
        TransactionReportGenerator.printTransactionsCountByMonth(monthYear, transactionsCount);

        List<Transaction> topExpenses = TransactionAnalyzer.findTopExpenses(transactions);
        TransactionReportGenerator.printTopExpensesReport(topExpenses);
        System.out.println();

        LocalDate from = LocalDate.of(2023, 12, 1);
        LocalDate to = LocalDate.of(2023, 12, 31);
        TransactionReportGenerator.printLargestAndSmallestExpenses(from, to,
                TransactionAnalyzer.findLargestExpenseInPeriod(transactions, from, to),
                TransactionAnalyzer.findSmallestExpenseInPeriod(transactions, from, to));
        System.out.println();

        // Самостійна робота: текстові звіти з символами '*'
        TransactionReportGenerator.printExpensesByCategoryReport(
                TransactionAnalyzer.calculateExpensesByCategory(transactions));
        System.out.println();
        TransactionReportGenerator.printExpensesByMonthReport(
                TransactionAnalyzer.calculateExpensesByMonth(transactions));
    }
}
