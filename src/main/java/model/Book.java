package model;

public class Book {
    private String title;
    String author; // default (package-private)
    protected int year;
    public boolean isAvailable;

    public static int totalBooks = 0;
    public final int id;

    // Конструктор 1: без параметрів
    public Book() {
        this.title = "Невідома назва";
        this.author = "Невідомий автор";
        this.year = 2000;
        this.isAvailable = false;
        
        totalBooks++;
        this.id = totalBooks;
    }

    // Конструктор 2: з параметрами (використовує this для розділення імені поля і параметра)
    public Book(String title, String author, int year, boolean isAvailable) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.isAvailable = isAvailable;
        
        totalBooks++;
        this.id = totalBooks;
    }

    // Конструктор 3: викликає інший конструктор через this(...)
    public Book(String title, String author) {
        this(title, author, 2026, true);
    }

    public void displayInfo() {
        System.out.printf("Книга [ID: %d]: '%s', Автор: %s, Рік: %d, Доступна: %b\n", 
            id, title, author, year, isAvailable);
    }
}
