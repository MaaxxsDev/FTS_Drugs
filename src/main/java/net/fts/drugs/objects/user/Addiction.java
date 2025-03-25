package net.fts.drugs.objects.user;

public class Addiction {

    String name;
    double strength;

    public Addiction(double strength, String name) {
        this.strength = strength;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public double getStrength() {
        return strength;
    }
    public void setStrength(double strength) {
        this.strength = strength;
    }
}
