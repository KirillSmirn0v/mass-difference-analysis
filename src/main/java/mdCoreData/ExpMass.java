package mdCoreData;

public class ExpMass {
    private int id;
    private double mass;

    public ExpMass(int id, double mass) {
        this.id = id;
        this.mass = mass;
    }

    public ExpMass(ExpMass expMass) {
        this.id = expMass.getId();
        this.mass = expMass.getMass();
    }

    public int getId() {
        return id;
    }

    public double getMass() {
        return mass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExpMass expMass = (ExpMass) o;

        return id == expMass.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
