package org.example.processing.internal;

import org.example.exception.OrderProcessingException;
import org.example.model.Product;

public final class OrderValidator {
    private OrderValidator() {
    }

    public static void validate(Product product) throws OrderProcessingException {
        if (product == null) {
            throw new OrderProcessingException("Помилка: Товар не може бути null!");
        }
        if (product.getName() == null || product.getName().isBlank()) {
            throw new OrderProcessingException("Помилка: у товару " + product.getId() + " немає назви");
        }
        if (product.getPrice() <= 0) {
            throw new OrderProcessingException("Помилка: некоректна ціна товару " + product.getName()
                    + ": " + product.getPrice());
        }
    }
}
