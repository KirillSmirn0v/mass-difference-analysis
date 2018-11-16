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
}
