package utils;

import mdCoreElements.Element;

import javax.print.attribute.IntegerSyntax;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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

    public static Map<Element, Integer> addSecondFormulaToFirst(Map<Element, Integer> formula1, Map<Element, Integer> formula2) {
        Map<Element, Integer> formula = new HashMap<>();

        Set<Element> elements = formula1.keySet();
        for (Map.Entry<Element, Integer> entry : formula2.entrySet()) {
            Element otherElement = entry.getKey();
            if (elements.contains(otherElement)) {
                formula.put(otherElement, formula1.get(otherElement) + formula2.get(otherElement));
            } else {
                formula.put(otherElement, formula2.get(otherElement));
            }
        }
        return formula1;
    }

    public static Map<Element, Integer> subtractSecondFormulaFromFirst(Map<Element, Integer> formula1, Map<Element, Integer> formula2) {
        Map<Element, Integer> formula = new HashMap<>();

        Set<Element> elements = formula1.keySet();
        for (Map.Entry<Element, Integer> entry : formula2.entrySet()) {
            Element otherElement = entry.getKey();
            if (elements.contains(otherElement)) {
                formula.put(otherElement, formula1.get(otherElement) - formula2.get(otherElement));
            } else {
                formula.put(otherElement, -formula2.get(otherElement));
            }
        }
        return formula;
    }

    public static boolean isValidFormula(Map<Element, Integer> formula) {
        boolean isPositive = formula.values().stream().anyMatch(x -> x >= 0);
        int leftSide = formula.entrySet().stream().mapToInt(x -> x.getValue() * x.getKey().getValency()).sum();
        int rightSide = 2 * (formula.values().stream().mapToInt(x -> x).sum() - 1);
        boolean isSeniorRule = leftSide >= rightSide;
        return isPositive && isSeniorRule;
    }

    public static double getPPMError(double mass, double refMass) {
        return (mass/refMass - 1.0) * 1000000;
    }
}

