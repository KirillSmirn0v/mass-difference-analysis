package mdPostProcessor;

import mdCoreElements.Element;
import mdGraphConstruction.MassWrapper;

import java.util.List;
import java.util.Map;

public class MassProcessed {
    private List<MassWrapper> massWrappers;
    private Map<Element, Integer> formula;
    private double mass;

    public MassProcessed(List<MassWrapper> massWrappers, Map<Element, Integer> formula, double mass) {
        this.massWrappers = massWrappers;
        this.formula = formula;
        this.mass = mass;
    }

    public List<MassWrapper> getMassWrappers() {
        return massWrappers;
    }

    public Map<Element, Integer> getFormula() {
        return formula;
    }

    public double getMass() {
        return mass;
    }
}
