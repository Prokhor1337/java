package domain;

public class OrderItem {
    private static int idCounter = 0;

    private final int id;
    private final String title;
    private final double unitPrice;
    private int quantity;

    public OrderItem(String title, double unitPrice, int quantity) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Назва позиції не може бути порожньою");
        }
        if (unitPrice <= 0) {
            throw new IllegalArgumentException("Ціна за одиницю повинна бути більшою за 0");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Кількість повинна бути більшою за 0");
        }
        this.id = ++idCounter;
        this.title = title.strip();
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    public OrderItem(String title, double unitPrice) {
        this(title, unitPrice, 1);
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Кількість повинна бути більшою за 0");
        }
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return unitPrice * quantity;
    }

    void internalValidate() {
        if (unitPrice <= 0 || quantity <= 0) {
            throw new IllegalStateException("Некоректний стан позиції");
        }
    }
}
