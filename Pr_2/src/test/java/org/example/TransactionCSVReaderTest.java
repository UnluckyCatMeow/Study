package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.List;

class TransactionCSVReaderTest {

    @Test
    public void testParseLine() {
        Transaction transaction = TransactionCSVReader.parseLine("05-12-2023,-450,Сільпо");

        Assertions.assertNotNull(transaction);
        Assertions.assertEquals("05-12-2023", transaction.getDate());
        Assertions.assertEquals(-450.0, transaction.getAmount());
        Assertions.assertEquals("Сільпо", transaction.getDescription());
    }

    @Test
    public void testParseLineSkipsHeaderAndInvalidLines() {
        Assertions.assertNull(TransactionCSVReader.parseLine("Дата,Сума,Категорія"));
        Assertions.assertNull(TransactionCSVReader.parseLine(""));
        Assertions.assertNull(TransactionCSVReader.parseLine("05-12-2023,-450"));
    }

    @Test
    public void testParseTransactionsFromLines() {
        List<String> lines = List.of(
                "Дата,Сума,Категорія",
                "05-12-2023,-450,Сільпо",
                "",
                "10-12-2023,8000,Зарплата");

        List<Transaction> transactions = TransactionCSVReader.parseTransactions(lines);

        Assertions.assertEquals(List.of(
                new Transaction("05-12-2023", -450.0, "Сільпо"),
                new Transaction("10-12-2023", 8000.0, "Зарплата")), transactions);
    }

    @Test
    public void testReadTransactionsFromCsvFile() throws URISyntaxException {
        // Файл лежить у src/test/resources
        Path path = Path.of(getClass().getResource("/test-transactions.csv").toURI());

        List<Transaction> transactions = TransactionCSVReader.readTransactions(path.toString());

        Assertions.assertEquals(5, transactions.size(), "Заголовок і порожній рядок мають бути пропущені");
        Assertions.assertEquals(new Transaction("05-12-2023", -450.0, "Сільпо"), transactions.get(0));
        Assertions.assertEquals(new Transaction("14-12-2023", -3200.0, "Оренда квартири"), transactions.get(4));
    }

    @Test
    public void testReadTransactionsFromCustomDataSource() {
        DataSource source = () -> List.of("01-01-2024,-100,Кафе", "02-01-2024,200,Подарунок");

        List<Transaction> transactions = TransactionCSVReader.readTransactions(source);

        Assertions.assertEquals(2, transactions.size());
        Assertions.assertEquals("Кафе", transactions.get(0).getDescription());
    }

    @Test
    public void testReadTransactionsFromMissingFileReturnsEmptyList() {
        List<Transaction> transactions = TransactionCSVReader.readTransactions("no-such-file.csv");

        Assertions.assertTrue(transactions.isEmpty());
    }
}
