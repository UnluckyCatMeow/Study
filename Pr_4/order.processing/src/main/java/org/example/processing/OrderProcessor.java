package org.example.processing;

import org.example.exception.OrderProcessingException;
import org.example.model.Product;
import org.example.processing.internal.OrderValidator;

/**
 * Обробник замовлення. T обмежений Product (T extends Product),
 * тому створити OrderProcessor<String> неможливо — помилка компіляції.
 */
public class OrderProcessor<T extends Product> {
    private final T product;

    public OrderProcessor(T product) {
        this.product = product;
    }

    public void processOrder() throws OrderProcessingException {
        OrderValidator.validate(product);

        System.out.printf("[Потік: %s] Успішно оброблено замовлення: %s (Ціна: $%.2f)%n",
                Thread.currentThread().getName(),
                product.getName(),
                product.getPrice());
    }

    /**
     * Обробка без checked-виключення назовні: зручно передавати як посилання на метод,
     * наприклад processors.forEach(OrderProcessor::processSafely).
     * Повертає true, якщо обробка пройшла успішно.
     */
    public boolean processSafely() {
        try {
            processOrder();
            return true;
        } catch (OrderProcessingException e) {
            System.err.println("[Потік: " + Thread.currentThread().getName() + "] " + e.getMessage());
            return false;
        }
    }

    public T getProduct() {
        return product;
    }

    /**
     * Узагальнений метод з обробкою виключення, специфічного для generics:
     * через стирання типів (type erasure) перевірка типу можлива лише під час виконання,
     * і невдале приведення дає ClassCastException, який ми перетворюємо на своє виключення.
     */
    public static <T extends Product> OrderProcessor<T> forType(Product product, Class<T> type)
            throws OrderProcessingException {
        try {
            return new OrderProcessor<>(type.cast(product));
        } catch (ClassCastException e) {
            throw new OrderProcessingException("Товар \"" + product.getName() + "\" ("
                    + product.getClass().getSimpleName() + ") не є " + type.getSimpleName(), e);
        }
    }
}
