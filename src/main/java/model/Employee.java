package model;

public class Employee {
    public String position = "Працівник";
    private final String name;

    public Employee(String name) {
        System.out.println("-> [Employee] конструктор");
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String describe() {
        return "Працівник " + name;
    }

    public void work() {
        System.out.println(name + " виконує базові робочі обов'язки.");
    }
}
