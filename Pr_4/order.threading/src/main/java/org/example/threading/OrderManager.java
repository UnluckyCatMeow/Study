package org.example.threading;

import org.example.exception.OrderProcessingException;
import org.example.model.Product;
import org.example.processing.OrderProcessor;
import org.example.storage.OrderRepository;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Керування потоками обробки замовлень.
 */
public class OrderManager {
    private static final int THREAD_COUNT = 3;

    private final OrderRepository repository;

    public OrderManager(OrderRepository repository) {
        this.repository = repository;
    }

    /** Паралельна обробка всіх замовлень у пулі з THREAD_COUNT потоків. */
    public void processOrdersConcurrently() {
        List<Product> products = repository.getProducts();

        try (ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT)) {
            for (Product product : products) {
                Runnable task = () -> {
                    OrderProcessor<Product> processor = new OrderProcessor<>(product);
                    if (processor.processSafely()) {
                        repository.markProcessed(product);
                    }
                };
                executor.submit(task);
            }
        }
        System.out.println("Успішно оброблено: " + repository.getProcessedOrders().size()
                + " з " + products.size());
    }

    public <T extends Product> void processByType(Class<T> type) {
        System.out.println("\n--- Обробка замовлень типу " + type.getSimpleName() + " ---");

        List<T> typedProducts = repository.getProductsByType(type);
        typedProducts.stream()
                .map(OrderProcessor::new)
                .forEach(OrderProcessor::processSafely);
    }

    public void filterAndProcessExpensiveOrders(double threshold) {
        System.out.println("\n--- Фільтрація та обробка дорогих товарів (> $" + threshold + ") ---");

        repository.getProducts().stream()
                .filter(p -> p.getPrice() > threshold)
                .map(OrderProcessor::new)
                .forEach(OrderProcessor::processSafely);
    }

    /** Демонстрація обробки виключення при роботі з узагальненими типами. */
    public <T extends Product> void tryProcessAs(Product product, Class<T> type) {
        System.out.println("\n--- Спроба обробити \"" + product.getName() + "\" як " + type.getSimpleName() + " ---");
        try {
            OrderProcessor<T> processor = OrderProcessor.forType(product, type);
            processor.processOrder();
        } catch (OrderProcessingException e) {
            System.err.println("Помилка типу: " + e.getMessage());
        }
    }
}
