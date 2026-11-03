package model;

public interface Drawable {
    void draw();

    default String describe() {
        return "Графічний об'єкт (Drawable)";
    }
}
