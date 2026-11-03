package model;

public interface Scalable {
    void scale(double factor);

    default String describe() {
        return "Масштабований об'єкт (Scalable)";
    }
}
