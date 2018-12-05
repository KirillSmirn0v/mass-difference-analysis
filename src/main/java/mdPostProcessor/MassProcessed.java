package mdPostProcessor;

import mdCoreElements.Element;
import mdGraphConstruction.MassWrapper;

import java.util.List;
import java.util.Map;

public class MassProcessed {
    private int id;
    private List<MassWrapper> massWrappers;
    private Map<Element, Integer> formula;
    private double mass;

    public MassProcessed(int id, List<MassWrapper> massWrappers, Map<Element, Integer> formula, double mass) {
        this.id = id;
        this.massWrappers = massWrappers;
        this.formula = formula;
        this.mass = mass;
    }

    public int getId() {
        return id;
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
