package mdGraphConstruction;

import mdCoreData.ExpMass;
import mdCoreElements.IonAdduct;

public class MassWrapper {
    private ExpMass expMass;
    private IonAdduct ionAdduct;
    private double mass;

    public MassWrapper(ExpMass expMass, IonAdduct ionAdduct) {
        this.expMass = expMass;
        this.ionAdduct = ionAdduct;
        this.mass = expMass.getMass() - ionAdduct.getMass();
    }

    public ExpMass getExpMass() {
        return expMass;
    }

    public IonAdduct getIonAdduct() {
        return ionAdduct;
    }

    public double getMass() {
        return mass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MassWrapper that = (MassWrapper) o;

        if (expMass != null ? !expMass.equals(that.expMass) : that.expMass != null) return false;
        return ionAdduct != null ? ionAdduct.equals(that.ionAdduct) : that.ionAdduct == null;
    }

    @Override
    public int hashCode() {
        int result = expMass != null ? expMass.hashCode() : 0;
        result = 31 * result + (ionAdduct != null ? ionAdduct.hashCode() : 0);
        return result;
    }
}
