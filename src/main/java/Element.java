public class Element {
    private String name;
    private double mass;
    private int valency;

    public Element(String name, double mass, int valency) {
        this.name = name;
        this.mass = mass;
        this.valency = valency;
    }

    public String getName() {
        return name;
    }

    public double getMass() {
        return mass;
    }

    public int getValency() {
        return valency;
    }
}
