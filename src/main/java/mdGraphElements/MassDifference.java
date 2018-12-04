package mdGraphElements;

import mdCoreElements.Element;
import utils.MDUtils;

import java.util.HashMap;
import java.util.Map;

public class MassDifference {
    private String name;
    private Map<Element, Integer> formula;
    private double mass;

    public MassDifference(String name, Map<Element, Integer> formula) {
        this.name = name;
        this.formula = formula;
        this.mass = MDUtils.getMassFromFormula(formula);
    }

    public MassDifference(MassDifference massDifference) {
        Map<Element, Integer> formula = massDifference.getFormula();
        Map<Element, Integer> formulaCopy = new HashMap<>();
        for (Map.Entry<Element, Integer> entry : formula.entrySet()) {
            formulaCopy.put(entry.getKey(), entry.getValue());
        }
        this.name = massDifference.getName();
        this.formula = formulaCopy;
        this.mass = massDifference.getMass();
    }

    public String getName() {
        return name;
    }

    public Map<Element, Integer> getFormula() {
        return formula;
    }

    public double getMass() {
        return mass;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Element, Integer> entry : formula.entrySet()) {
            sb.append(entry.getKey().getName());
            int amount = entry.getValue();
            if (amount != 1) {
                sb.append(amount);
            }
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MassDifference that = (MassDifference) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return formula != null ? formula.equals(that.formula) : that.formula == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (formula != null ? formula.hashCode() : 0);
        return result;
    }
}
