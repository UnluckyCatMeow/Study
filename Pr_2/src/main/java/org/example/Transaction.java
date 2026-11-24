package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    private String date;        // дата у форматі dd-MM-yyyy
    private double amount;      // сума: "+" дохід, "-" витрата
    private String description; // категорія / опис
}
