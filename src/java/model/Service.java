package model;

public class Service {

    private int id;
    private String name;
    private String description;
    private double monthlyFee;
    private boolean active;

    // -----------------------------
    // Constructors
    // -----------------------------
    public Service() {
        this.active = true; // default: active
    }

    public Service(int id, String name, String description, double monthlyFee, boolean active) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.monthlyFee = monthlyFee;
        this.active = active;
    }

    public Service(String name, String description, double monthlyFee) {
        this.name = name;
        this.description = description;
        this.monthlyFee = monthlyFee;
        this.active = true;
    }

    // -----------------------------
    // Getters & Setters
    // -----------------------------
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getMonthlyFee() {
        return monthlyFee;
    }

    public void setMonthlyFee(double monthlyFee) {
        this.monthlyFee = monthlyFee;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    // -----------------------------
    // Alias Methods for Compatibility
    // -----------------------------
    public double getPrice() {
        return monthlyFee;
    }

    public void setPrice(double price) {
        this.monthlyFee = price;
    }

    // -----------------------------
    // Debug Print
    // -----------------------------
    @Override
    public String toString() {
        return "Service{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", monthlyFee=" + monthlyFee +
                ", active=" + active +
                '}';
    }
}
