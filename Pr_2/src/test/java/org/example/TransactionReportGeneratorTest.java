package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.YearMonth;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

class TransactionReportGeneratorTest {

    @Test
    public void testBuildBar() {
        Assertions.assertEquals("***", TransactionReportGenerator.buildBar(3000));
        Assertions.assertEquals("***", TransactionReportGenerator.buildBar(3200));  // округлення вниз
        Assertions.assertEquals("****", TransactionReportGenerator.buildBar(3600)); // округлення вгору
        Assertions.assertEquals("*", TransactionReportGenerator.buildBar(120));     // мала сума — один символ
        Assertions.assertEquals("", TransactionReportGenerator.buildBar(0));
    }

    @Test
    public void testBuildExpensesByCategoryReport() {
        Map<String, Double> data = new LinkedHashMap<>();
        data.put("Оренда квартири", 3200.0);
        data.put("Сільпо", 950.0);

        String report = TransactionReportGenerator.buildExpensesByCategoryReport(data);

        Assertions.assertTrue(report.contains("Оренда квартири"));
        Assertions.assertTrue(report.contains("| *** 3200"));
        Assertions.assertTrue(report.contains("| * 950"));
    }

    @Test
    public void testBuildExpensesByMonthReport() {
        Map<YearMonth, Double> data = new TreeMap<>();
        data.put(YearMonth.of(2024, 1), 500.0);
        data.put(YearMonth.of(2023, 12), 3650.0);

        String report = TransactionReportGenerator.buildExpensesByMonthReport(data);

        Assertions.assertTrue(report.indexOf("12-2023") < report.indexOf("01-2024"));
        Assertions.assertTrue(report.contains("| **** 3650"));
    }
}
