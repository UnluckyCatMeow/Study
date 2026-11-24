package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class AutomatonTest {

    // Завдання 2: усі звичайні випадки. Формат: вхідний рядок, очікуваний стан
    @ParameterizedTest(name = "\"{0}\" -> {1}")
    @CsvSource({
            "'',          S",     // порожній рядок
            "abc,         S",     // немає жодної T
            "T,           ONE",
            "TE,          TWO",
            "TES,         THREE",
            "abcTES,      THREE", // приклад із завдання
            "TEST,        F",
            "abcTESTabc,  F",     // приклад із завдання
            "TESTabc,     F",     // слово на початку
            "abcTEST,     F",     // слово в кінці
            "TESTTEST,    F",     // слово двічі
            "TX,          S",     // помилка в стані 1
            "TEX,         S",     // помилка в стані 2
            "TESX,        S",     // помилка в стані 3
            "TESXT,       ONE",   // після помилки почали заново
            "test,        S",     // маленькі літери не підходять
            "TETS,        S"      // літери переплутані
    })
    void testRun(String input, State expected) {
        Assertions.assertEquals(expected, Automaton.run(input));
    }

    // Завдання 3*: рядки зі словом TEST, на яких автомат за схемою НЕ доходив до F.
    // Причина: при помилці в стані 1 або 2 автомат повертався в S і "губив" літеру T,
    // хоча саме з неї починалось справжнє слово TEST.
    @ParameterizedTest(name = "\"{0}\" -> F")
    @CsvSource({
            "TTEST",      // друга T губилась у стані 1
            "TTTTEST",
            "TETEST",     // T після TE губилась у стані 2
            "abcTETEST",
            "abcTTESTabc",
            "xTETESTx"
    })
    void testStringsThatFailedBeforeFix(String input) {
        Assertions.assertEquals(State.F, Automaton.run(input));
    }
}
