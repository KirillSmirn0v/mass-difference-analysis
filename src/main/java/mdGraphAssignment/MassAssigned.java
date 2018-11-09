package mdGraphAssignment;

import mdCoreElements.Element;
import mdGraphConstruction.MassWrapper;

import java.util.HashMap;
import java.util.Map;

public class MassAssigned {
    private MassWrapper massWrapper;
    private Map<Element, Integer> formula;
    private double mass;
    private boolean isModifiable;

    public MassAssigned(MassWrapper massWrapper, boolean isModifiable) {
        this.massWrapper = massWrapper;
        this.formula = new HashMap<>();
        this.mass = 0.0;
        this.isModifiable = isModifiable;
    }

    public MassWrapper getMassWrapper() {
        return massWrapper;
    }

    public Map<Element, Integer> getFormula() {
        return formula;
    }

    public double getMass() {
        return mass;
    }
}
