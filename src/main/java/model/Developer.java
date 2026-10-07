package model;

public class Developer extends Employee {
    public String position = "Розробник";

    public Developer(String name) {
        super(name);
        System.out.println("-> [Developer] конструктор");
    }

    @Override
    public String describe() {
        return "Розробник " + getName();
    }

    @Override
    public void work() {
        super.work();
        System.out.println(getName() + " пише код");
    }
}
