package model;

public class Manager extends Employee {
    public String position = "Менеджер";

    public Manager(String name) {
        super(name);
        System.out.println("-> [Manager] конструктор");
    }

    @Override
    public String describe() {
        return "Менеджер " + getName();
    }

    @Override
    public void work() {
        super.work();
        System.out.println(getName() + " керує");
    }
}
