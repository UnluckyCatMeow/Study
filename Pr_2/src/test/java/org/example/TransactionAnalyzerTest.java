package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

class TransactionAnalyzerTest {

    @Test
    public void testCalculateTotalBalance() {
        Transaction transaction1 = new Transaction("01-01-2023", 100.0, "Дохід");
        Transaction transaction2 = new Transaction("02-01-2023", -50.0, "Витрата");
        Transaction transaction3 = new Transaction("03-01-2023", 150.0, "Дохід");
        List<Transaction> transactions = Arrays.asList(transaction1, transaction2, transaction3);

        double result = TransactionAnalyzer.calculateTotalBalance(transactions);

        // Перевірка результату
        Assertions.assertEquals(200.0, result, "Розрахунок загального балансу неправильний");
    }

    @Test
    public void testCountTransactionsByMonth() {

        Transaction transaction1 = new Transaction("01-02-2023", 50.0, "Дохід");
        Transaction transaction2 = new Transaction("15-02-2023", -20.0, "Витрата");
        Transaction transaction3 = new Transaction("05-03-2023", 100.0, "Дохід");
        List<Transaction> transactions = Arrays.asList(transaction1, transaction2, transaction3);

        int countFeb = TransactionAnalyzer.countTransactionsByMonth(transactions, "02-2023");
        int countMar = TransactionAnalyzer.countTransactionsByMonth(transactions, "03-2023");

        Assertions.assertEquals(2, countFeb, "Кількість транзакцій за лютий неправильна");
        Assertions.assertEquals(1, countMar, "Кількість транзакцій за березень неправильна");
    }

    @Test
    public void testFindTopExpensesReturnsTenLargestSorted() {
        List<Transaction> transactions = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            transactions.add(new Transaction("01-01-2024", -100.0 * i, "Витрата " + i));
        }
        transactions.add(new Transaction("02-01-2024", 5000.0, "Зарплата"));
        transactions.add(new Transaction("03-01-2024", 99999.0, "Премія"));

        List<Transaction> top = TransactionAnalyzer.findTopExpenses(transactions);

        Assertions.assertEquals(10, top.size(), "Має бути рівно 10 витрат");
        Assertions.assertEquals(-1200.0, top.get(0).getAmount(), "Першою має бути найбільша витрата");
        Assertions.assertEquals(-300.0, top.get(9).getAmount(), "Останньою має бути 10-та за розміром витрата");
        for (int i = 1; i < top.size(); i++) {
            Assertions.assertTrue(top.get(i - 1).getAmount() <= top.get(i).getAmount(),
                    "Витрати мають бути відсортовані від найбільшої до меншої");
        }
        Assertions.assertTrue(top.stream().allMatch(t -> t.getAmount() < 0), "Доходів у списку бути не повинно");
    }

    @Test
    public void testFindTopExpensesWhenLessThanTen() {
        List<Transaction> transactions = Arrays.asList(
                new Transaction("01-01-2024", -10.0, "A"),
                new Transaction("02-01-2024", 500.0, "Дохід"),
                new Transaction("03-01-2024", -30.0, "B"));

        List<Transaction> top = TransactionAnalyzer.findTopExpenses(transactions);

        Assertions.assertEquals(2, top.size());
        Assertions.assertEquals("B", top.get(0).getDescription());
        Assertions.assertEquals("A", top.get(1).getDescription());
    }

    @Test
    public void testFindLargestAndSmallestExpenseInPeriod() {
        List<Transaction> transactions = Arrays.asList(
                new Transaction("30-11-2023", -9000.0, "Поза періодом"),
                new Transaction("01-12-2023", -500.0, "Сільпо"),
                new Transaction("15-12-2023", -3200.0, "Оренда квартири"),
                new Transaction("20-12-2023", 8000.0, "Зарплата"),
                new Transaction("31-12-2023", -120.0, "Аптека"),
                new Transaction("01-01-2024", -50.0, "Поза періодом"));
        LocalDate from = LocalDate.of(2023, 12, 1);
        LocalDate to = LocalDate.of(2023, 12, 31);

        Optional<Transaction> largest = TransactionAnalyzer.findLargestExpenseInPeriod(transactions, from, to);
        Optional<Transaction> smallest = TransactionAnalyzer.findSmallestExpenseInPeriod(transactions, from, to);

        Assertions.assertTrue(largest.isPresent());
        Assertions.assertEquals("Оренда квартири", largest.get().getDescription());
        Assertions.assertTrue(smallest.isPresent());
        Assertions.assertEquals("Аптека", smallest.get().getDescription(), "Межі періоду мають включатися");
    }

    @Test
    public void testExpensesInPeriodWhenNoExpenses() {
        List<Transaction> transactions = List.of(new Transaction("10-12-2023", 8000.0, "Зарплата"));
        LocalDate from = LocalDate.of(2023, 12, 1);
        LocalDate to = LocalDate.of(2023, 12, 31);

        Assertions.assertTrue(TransactionAnalyzer.findLargestExpenseInPeriod(transactions, from, to).isEmpty());
        Assertions.assertTrue(TransactionAnalyzer.findSmallestExpenseInPeriod(transactions, from, to).isEmpty());
    }

    @Test
    public void testCalculateExpensesByCategory() {
        List<Transaction> transactions = Arrays.asList(
                new Transaction("05-12-2023", -450.0, "Сільпо"),
                new Transaction("05-01-2024", -500.0, "Сільпо"),
                new Transaction("14-12-2023", -3200.0, "Оренда квартири"),
                new Transaction("10-12-2023", 8000.0, "Зарплата"));

        Map<String, Double> result = TransactionAnalyzer.calculateExpensesByCategory(transactions);

        Assertions.assertEquals(2, result.size(), "Доходи не повинні потрапляти у витрати");
        Assertions.assertEquals(950.0, result.get("Сільпо"), 0.001);
        Assertions.assertEquals(3200.0, result.get("Оренда квартири"), 0.001);
        Assertions.assertEquals("Оренда квартири", result.keySet().iterator().next(),
                "Категорії мають бути відсортовані від найбільшої суми");
    }

    @Test
    public void testCalculateExpensesByMonth() {
        List<Transaction> transactions = Arrays.asList(
                new Transaction("05-01-2024", -500.0, "Сільпо"),
                new Transaction("05-12-2023", -450.0, "Сільпо"),
                new Transaction("14-12-2023", -3200.0, "Оренда квартири"),
                new Transaction("10-12-2023", 8000.0, "Зарплата"));

        Map<YearMonth, Double> result = TransactionAnalyzer.calculateExpensesByMonth(transactions);

        Assertions.assertEquals(List.of(YearMonth.of(2023, 12), YearMonth.of(2024, 1)),
                List.copyOf(result.keySet()), "Місяці мають іти в хронологічному порядку");
        Assertions.assertEquals(3650.0, result.get(YearMonth.of(2023, 12)), 0.001);
        Assertions.assertEquals(500.0, result.get(YearMonth.of(2024, 1)), 0.001);
    }
}
