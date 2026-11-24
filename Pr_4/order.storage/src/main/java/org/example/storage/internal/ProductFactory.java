package org.example.storage.internal;

import net.datafaker.Faker;
import org.example.model.Clothing;
import org.example.model.Electronics;

import java.util.UUID;

/**
 * Внутрішній клас модуля order.storage: створює випадкові товари через Builder + Datafaker.
 * Пакет не експортується в module-info.java, тому інші модулі не бачать цей клас
 * (спроба імпорту з order.threading дасть помилку компіляції).
 */
public class ProductFactory {
    private final Faker faker = new Faker();

    public Electronics createElectronics() {
        return Electronics.builder()
                .id(UUID.randomUUID().toString())
                .name(faker.device().modelName())
                .price(faker.number().randomDouble(2, 50, 2000))
                .brand(faker.device().manufacturer())
                .warrantyMonths(faker.number().numberBetween(6, 24))
                .build();
    }

    public Clothing createClothing() {
        return Clothing.builder()
                .id(UUID.randomUUID().toString())
                .name(faker.color().name() + " "
                        + faker.options().option("T-Shirt", "Jeans", "Jacket", "Hoodie", "Sweater", "Dress"))
                .price(faker.number().randomDouble(2, 10, 300))
                .size(faker.options().option("S", "M", "L", "XL"))
                .material(faker.commerce().material())
                .build();
    }
}
