package app;

import domain.Order;
import domain.OrderItem;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

public class Main {
    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

        Order order = new Order();
        OrderItem item1 = new OrderItem("Клавіатура", 1200.00);
        OrderItem item2 = new OrderItem("Миша", 500.00, 2);

        order.addItem(item1);
        order.addItem(item2);

        System.out.println("Замовлення #" + order.getId());
        for (OrderItem item : order.getItems()) {
            System.out.printf(Locale.US, "+ %s %.2f x %d%n",
                    item.getTitle(), item.getUnitPrice(), item.getQuantity());
        }
        System.out.printf(Locale.US, "Разом: %.2f%n", order.getTotalAmount());

        try {
            order.getItems().clear();
        } catch (UnsupportedOperationException e) {
            System.out.printf(Locale.US, "спроба змінити перелік позицій ззовні -> на замовлення не впливає, Разом: %.2f%n", order.getTotalAmount());
        }

        try {
            OrderItem invalidItem = new OrderItem("Некоректний", -10.0, 1);
            order.addItem(invalidItem);
        } catch (IllegalArgumentException e) {
            System.out.println("спроба додати позицію з ціною -10 -> відхилено");
        }

        try {
            item1.setQuantity(-5);
        } catch (IllegalArgumentException e) {
            System.out.printf(Locale.US, "спроба встановити кількість -5 для позиції #%d -> відхилено%n", item1.getId());
        }
    }
}
