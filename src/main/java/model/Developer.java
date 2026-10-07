package model;

public class Developer extends Employee {
    public String position = "Розробник";
    private final String specialty;

    public Developer(String name, String specialty) {
        super(name);
        System.out.println("-> [Developer] конструктор");
        this.specialty = specialty;
    }

    public String getSpecialty() {
        return specialty;
    }

    @Override
    public String describe() {
        return "Розробник " + getName() + " (спеціалізація: " + specialty + ")";
    }

    @Override
    public void work() {
        super.work();
        System.out.println(getName() + " пише та тестує код (" + specialty + ").");
    }
}
