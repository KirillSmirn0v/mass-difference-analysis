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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MassProcessed that = (MassProcessed) o;

        if (id != that.id) return false;
        if (massWrappers != null ? !massWrappers.equals(that.massWrappers) : that.massWrappers != null) return false;
        return formula != null ? formula.equals(that.formula) : that.formula == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (massWrappers != null ? massWrappers.hashCode() : 0);
        result = 31 * result + (formula != null ? formula.hashCode() : 0);
        return result;
    }
}
