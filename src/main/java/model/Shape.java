package model;

public abstract class Shape {
    private final String name;

    public Shape(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Назва фігури не може бути порожньою");
        }
        this.name = name.strip();
    }

    public String getName() {
        return name;
    }

    public abstract double area();
}
