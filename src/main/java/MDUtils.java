import java.util.Map;

public class MDUtils {

    public static double getMassFromFormula(Map<Element, Integer> formula) {
        double mass = 0.0;
        for (Map.Entry<Element, Integer> entry : formula.entrySet()) {
            Element element = entry.getKey();
            int amount = entry.getValue();
            mass += amount * element.getMass();
        }
        return mass;
    }
}
