package mdGraphAssignment;

import mdCoreElements.Element;
import mdGraphConstruction.MassWrapper;
import utils.MDUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MassAssigned {
    private MassWrapper massWrapper;
    private Map<Element, Integer> formula;
    private double mass;
    private boolean isModifiable;
    private boolean isAssigned;

    public MassAssigned(MassWrapper massWrapper, boolean isModifiable) {
        this.massWrapper = massWrapper;
        this.formula = new HashMap<>();
        this.mass = 0.0;
        this.isModifiable = isModifiable;
        this.isAssigned = false;
    }

    public void setAssigned(boolean isAssigned) {
        this.isAssigned = isAssigned;
    }

    public void setFormula(Map<Element, Integer> formula) {
        this.formula = formula;
        this.mass = MDUtils.getMassFromFormula(formula);
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

    public boolean isModifiable() {
        return isModifiable;
    }

    public boolean isAssigned() {
        return isAssigned;
    }
}
