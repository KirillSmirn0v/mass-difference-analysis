package mdGraphElements;

import mdCoreElements.Element;
import utils.MDUtils;

import java.util.Map;

public class MassDifference {
    private int id;
    private String name;
    private Map<Element, Integer> formula;
    private double mass;

    public MassDifference(int id, String name, Map<Element, Integer> formula) {
        this.id = id;
        this.name = name;
        this.formula = formula;
        this.mass = MDUtils.getMassFromFormula(formula);
    }

    public int getId() {
        return id;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MassDifference that = (MassDifference) o;

        return formula.equals(that.formula);
    }

    @Override
    public int hashCode() {
        return formula.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Element, Integer> entry : formula.entrySet()) {
            sb.append(entry.getKey().getName());
            int amount = entry.getValue();
            if (amount > 1) {
                sb.append(amount);
            }
        }
        return sb.toString();
    }
}
