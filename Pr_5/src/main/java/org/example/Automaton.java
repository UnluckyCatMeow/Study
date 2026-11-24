package org.example;

/**
 * Скінченний автомат, який шукає в рядку слово "TEST".
 */
public class Automaton {

    /** Проганяє автомат по рядку і повертає стан, у якому він опинився в кінці. */
    public static State run(String input) {
        State state = State.S;
        for (char c : input.toCharArray()) {
            state = next(state, c);
        }
        return state;
    }

    /** Один крок: з поточного стану по символу c переходимо в наступний стан. */
    public static State next(State state, char c) {
        return switch (state) {
            case S -> c == 'T' ? State.ONE : State.S;
            // Виправлення (завдання 3*): при помилці символ 'T' не викидаємо,
            // а вважаємо його початком нового слова -> стан ONE (на схемі тут було S)
            case ONE -> c == 'E' ? State.TWO : (c == 'T' ? State.ONE : State.S);
            case TWO -> c == 'S' ? State.THREE : (c == 'T' ? State.ONE : State.S);
            case THREE -> c == 'T' ? State.F : State.S;
            case F -> State.F; // слово вже знайдено, далі будь-який символ нічого не змінює
        };
    }
}
