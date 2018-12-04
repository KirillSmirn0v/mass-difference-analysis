package mdCoreElements;

public class Element {
    private String name;
    private double mass;
    private int valency;

    public Element(String name, double mass, int valency) {
        this.name = name;
        this.mass = mass;
        this.valency = valency;
    }

    public Element(Element element) {
        this(element.getName(), element.getMass(), element.getValency());
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Element element = (Element) o;

        if (valency != element.valency) return false;
        return name != null ? name.equals(element.name) : element.name == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + valency;
        return result;
    }
}
