package org.example;

public class Main {
    public static void main(String[] args) {
        String[] inputs = {"abcTESTabc", "abcTES", "hello", "TTEST", "TETEST"};

        for (String input : inputs) {
            System.out.println("\"" + input + "\" -> " + Automaton.run(input));
        }
    }
}
