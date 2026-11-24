package org.example;

import java.io.IOException;
import java.util.List;

/**
 Відповідає ТІЛЬКИ за отримання рядків — звідки завгодно (інтернет, файл, ...).
 */
@FunctionalInterface
public interface DataSource {
    List<String> readLines() throws IOException;
}
