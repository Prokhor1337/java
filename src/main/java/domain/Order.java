package domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Order {
    private static int orderCounter = 0;

    private final int id;
    private final List<OrderItem> items = new ArrayList<>();

    public Order() {
        this.id = ++orderCounter;
    }

    public int getId() {
        return id;
    }

    public void addItem(OrderItem item) {
        if (item == null) {
            throw new IllegalArgumentException("Позиція замовлення не може бути null");
        }
        item.internalValidate();
        items.add(item);
        internalAudit();
    }

    public List<OrderItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public double getTotalAmount() {
        double sum = 0.0;
        for (OrderItem item : items) {
            sum += item.getTotalPrice();
        }
        return sum;
    }

    void internalAudit() {
        for (OrderItem item : items) {
            item.internalValidate();
        }
    }
}
