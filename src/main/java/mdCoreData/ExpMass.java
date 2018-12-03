package mdCoreData;

public class ExpMass {
    private int id;
    private double mass;

    public ExpMass(int id, double mass) {
        this.id = id;
        this.mass = mass;
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

        if (id != expMass.id) return false;
        return Double.compare(expMass.mass, mass) == 0;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        temp = Double.doubleToLongBits(mass);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
