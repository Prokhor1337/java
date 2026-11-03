package model;

public class Circle extends Shape implements Drawable, Scalable {
    private double radius;

    public Circle(double radius) {
        super("Коло");
        if (radius <= 0) {
            throw new IllegalArgumentException("Радіус повинен бути більшим за 0");
        }
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public double area() {
        return Math.PI * radius * radius;
    }

    @Override
    public void draw() {
        System.out.printf("Малювання кола (радіус = %.2f)%n", radius);
    }

    @Override
    public void scale(double factor) {
        if (factor <= 0) {
            throw new IllegalArgumentException("Коефіцієнт масштабування повинен бути більшим за 0");
        }
        this.radius *= factor;
    }

    @Override
    public String describe() {
        return Drawable.super.describe() + " | " + Scalable.super.describe();
    }
}
