package org.example;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class TransactionCSVReader {

    private static final String SEPARATOR = ",";

    public static List<Transaction> readTransactions(String filePath) {
        DataSource source = filePath.startsWith("http://") || filePath.startsWith("https://")
                ? new UrlDataSource(filePath)
                : new FileDataSource(Path.of(filePath));
        return readTransactions(source);
    }

    public static List<Transaction> readTransactions(DataSource source) {
        try {
            return parseTransactions(source.readLines());
        } catch (IOException e) {
            System.err.println("Не вдалося прочитати дані: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public static List<Transaction> parseTransactions(List<String> lines) {
        List<Transaction> transactions = new ArrayList<>();
        for (String line : lines) {
            Transaction transaction = parseLine(line);
            if (transaction != null) {
                transactions.add(transaction);
            }
        }
        return transactions;
    }

    public static Transaction parseLine(String line) {
        if (line == null) {
            return null;
        }
        line = line.replace("﻿", "").trim(); // прибираємо BOM і зайві пробіли
        if (line.isEmpty()) {
            return null;
        }
        String[] values = line.split(SEPARATOR, 3);
        if (values.length < 3) {
            return null;
        }
        try {
            double amount = Double.parseDouble(values[1].trim());
            return new Transaction(values[0].trim(), amount, values[2].trim());
        } catch (NumberFormatException e) {
            return null; // заголовок або некоректний рядок
        }
    }
}
