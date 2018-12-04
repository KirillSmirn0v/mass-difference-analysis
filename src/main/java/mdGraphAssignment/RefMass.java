package mdGraphAssignment;

import mdCoreElements.Element;
import mdCoreElements.IonAdduct;

import java.util.Map;

public class RefMass {
    private Map<Element, Integer> formula;
    private double mass;
    private IonAdduct ionAdduct;

    public RefMass(Map<Element, Integer> formula, double mass, IonAdduct ionAdduct) {
        this.formula = formula;
        this.ionAdduct = ionAdduct;
        this.mass = mass - ionAdduct.getMass();
    }

    public Map<Element, Integer> getFormula() {
        return formula;
    }

    public double getMass() {
        return mass;
    }

    public IonAdduct getIonAdduct() {
        return ionAdduct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RefMass refMass = (RefMass) o;

        if (formula != null ? !formula.equals(refMass.formula) : refMass.formula != null) return false;
        return ionAdduct != null ? ionAdduct.equals(refMass.ionAdduct) : refMass.ionAdduct == null;
    }

    @Override
    public int hashCode() {
        int result = formula != null ? formula.hashCode() : 0;
        result = 31 * result + (ionAdduct != null ? ionAdduct.hashCode() : 0);
        return result;
    }
}
