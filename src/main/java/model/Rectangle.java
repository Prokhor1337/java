package model;

public class Rectangle extends Shape implements Drawable, Scalable {
    private double width;
    private double height;

    public Rectangle(double width, double height) {
        super("Прямокутник");
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Сторони прямокутника повинні бути більшими за 0");
        }
        this.width = width;
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    @Override
    public double area() {
        return width * height;
    }

    @Override
    public void draw() {
        System.out.printf("Малювання прямокутника (%.2f x %.2f)%n", width, height);
    }

    @Override
    public void scale(double factor) {
        if (factor <= 0) {
            throw new IllegalArgumentException("Коефіцієнт масштабування повинен бути більшим за 0");
        }
        this.width *= factor;
        this.height *= factor;
    }

    @Override
    public String describe() {
        return Drawable.super.describe() + " | " + Scalable.super.describe();
    }
}
