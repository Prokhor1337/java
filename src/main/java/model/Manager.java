package model;

public class Manager extends Employee {
    public String position = "Менеджер";
    private final String department;

    public Manager(String name, String department) {
        super(name);
        System.out.println("-> [Manager] конструктор");
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }

    @Override
    public String describe() {
        return "Менеджер " + getName() + " (відділ: " + department + ")";
    }

    @Override
    public void work() {
        super.work();
        System.out.println(getName() + " координує роботу відділу " + department + ".");
    }
}
