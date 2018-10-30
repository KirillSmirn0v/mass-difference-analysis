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
}
