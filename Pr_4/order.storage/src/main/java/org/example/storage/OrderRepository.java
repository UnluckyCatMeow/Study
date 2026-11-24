package org.example.storage;

import org.example.model.Product;
import org.example.storage.internal.ProductFactory;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class OrderRepository {
    private final List<Product> products = new CopyOnWriteArrayList<>();
    private final List<Product> processedOrders = new CopyOnWriteArrayList<>();
    private final ProductFactory factory = new ProductFactory(); // внутрішній клас модуля

    public void generateFakeData(int count) {
        for (int i = 0; i < count; i++) {
            products.add(i % 2 == 0 ? factory.createElectronics() : factory.createClothing());
        }
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public List<Product> getProducts() {
        return List.copyOf(products);
    }

    /**
     * Узагальнений метод: повертає лише товари вказаного типу (Electronics, Clothing ...).
     * T обмежений Product, тому передати, наприклад, String.class неможливо.
     */
    public <T extends Product> List<T> getProductsByType(Class<T> type) {
        if (type == null) {
            throw new IllegalArgumentException("Тип товару не може бути null");
        }
        return products.stream()
                .filter(type::isInstance) // посилання на метод
                .map(type::cast)          // безпечне приведення типу
                .toList();
    }

    /** Викликається з різних потоків — тому потокобезпечна колекція. */
    public void markProcessed(Product product) {
        processedOrders.add(product);
    }

    public List<Product> getProcessedOrders() {
        return List.copyOf(processedOrders);
    }
}
