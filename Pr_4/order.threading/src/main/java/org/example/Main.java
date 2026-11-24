package org.example;

import org.example.model.Clothing;
import org.example.model.Electronics;
import org.example.storage.OrderRepository;
import org.example.threading.OrderManager;
// import org.example.storage.internal.ProductFactory; // не скомпілюється: пакет не експортується модулем order.storage
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) {
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out), true, StandardCharsets.UTF_8));
        System.setErr(new PrintStream(new FileOutputStream(FileDescriptor.err), true, StandardCharsets.UTF_8));
        System.out.println("Ініціалізація багатомодульної багатопотокової системи управління замовленнями...");

        OrderRepository repository = new OrderRepository();
        repository.generateFakeData(6);

        repository.addProduct(Electronics.builder()
                .id("broken-1")
                .name("Бракований ноутбук")
                .price(0)
                .brand("NoName")
                .warrantyMonths(0)
                .build());

        OrderManager manager = new OrderManager(repository);

        System.out.println("\n[Старт] Паралельна обробка всіх замовлень:");
        manager.processOrdersConcurrently();

        manager.processByType(Electronics.class);
        manager.processByType(Clothing.class);

        manager.filterAndProcessExpensiveOrders(100.0);

        // Одяг не можна обробити як електроніку — буде оброблене виключення
        Clothing clothing = repository.getProductsByType(Clothing.class).getFirst();
        manager.tryProcessAs(clothing, Electronics.class);

        System.out.println("\n[Завершення] Усі замовлення опрацьовано.");
    }
}
