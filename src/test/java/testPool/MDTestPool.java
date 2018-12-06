package testPool;

import mdCoreData.ExpMass;
import mdCoreElements.Element;
import mdCoreElements.IonAdduct;
import mdGraphAssignment.MassAssigned;
import mdGraphAssignment.RefMass;
import mdGraphConstruction.MassWrapper;
import mdGraphElements.MassDifference;
import utils.MDUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MDTestPool {
    private Map<String, Element> elementMap = new TreeMap<>();
    private Map<String, IonAdduct> ionAdductMap = new TreeMap<>();
    private Map<String, ExpMass> expMassMap = new TreeMap<>();
    private Map<String, MassWrapper> massWrapperMap = new TreeMap<>();
    private Map<String, MassDifference> massDifferenceMap = new TreeMap<>();
    private Map<String, RefMass> refMassMap = new TreeMap<>();
    private Map<String, MassAssigned> massAssignedMap = new TreeMap<>();

    private static MDTestPool mdTestPool = new MDTestPool();

    private MDTestPool() {
        initialize();
    }

    public static MDTestPool getInstance() {
        return mdTestPool;
    }

    public Set<Element> getElementPool(String ... elementNames) {
        Set<Element> elements = new HashSet<>();
        for (String elementName : elementNames) {
            if (elementMap.keySet().contains(elementName)) {
                elements.add(elementMap.get(elementName));
            }
        }
        return elements;
    }

    public Set<IonAdduct> getIonAdductPool(String ... adductNames) {
        Set<IonAdduct> adducts = new HashSet<>();
        for (String adductName : adductNames) {
            if (ionAdductMap.keySet().contains(adductName)) {
                adducts.add(ionAdductMap.get(adductName));
            }
        }
        return adducts;
    }

    public List<ExpMass> getExpMassPool(String ... expMassNames) {
        List<ExpMass> expMasses = new ArrayList<>();
        for (String expMassName : expMassNames) {
            if (expMassMap.keySet().contains(expMassName)) {
                expMasses.add(expMassMap.get(expMassName));
            }
        }
        return expMasses;
    }

    public List<MassWrapper> getMassWrapperPool(String ... massWrapperNames) {
        List<MassWrapper> massWrappers = new ArrayList<>();
        for (String massWrapperName : massWrapperNames) {
            if (massWrapperMap.keySet().contains(massWrapperName)) {
                massWrappers.add(massWrapperMap.get(massWrapperName));
            }
        }
        return massWrappers;
    }

    public Set<MassDifference> getMassDifferencePool(String ... massDifferenceNames) {
        Set<MassDifference> massDifferences = new HashSet<>();
        for (String massDifferenceName : massDifferenceNames) {
            if (massDifferenceMap.keySet().contains(massDifferenceName)) {
                massDifferences.add(massDifferenceMap.get(massDifferenceName));
            }
        }
        return massDifferences;
    }

    public List<RefMass> getRefMassPool(String ... refMassNames) {
        List<RefMass> refMasses = new ArrayList<>();
        for (String refMassName : refMassNames) {
            if (refMassMap.keySet().contains(refMassName)) {
                refMasses.add(refMassMap.get(refMassName));
            }
        }
        return refMasses;
    }

    public List<MassAssigned> getMassAssignedPool(String ... massAssignedNames) {
        List<MassAssigned> massAssignedList = new ArrayList<>();
        for (String massAssignedName : massAssignedNames) {
            if (massAssignedMap.keySet().contains(massAssignedName)) {
                massAssignedList.add(massAssignedMap.get(massAssignedName));
            }
        }
        return massAssignedList;
    }

    private void initialize() {
        // elements
        elementMap.put("C", new Element("C", 12.000000, 4));
        elementMap.put("H", new Element("H", 1.0078250, 1));
        elementMap.put("O", new Element("O", 15.994915, 2));
        elementMap.put("N", new Element("N", 14.003074, 3));

        // ion adducts
        ionAdductMap.put("[M+H]+", new IonAdduct("[M+H]+", IonAdduct.IonSign.POSITIVE, 1.007276));
        ionAdductMap.put("[M+Na]+", new IonAdduct("[M+Na]+", IonAdduct.IonSign.POSITIVE, 22.989221));
        ionAdductMap.put("[M-H]-", new IonAdduct("[M-H]-", IonAdduct.IonSign.NEGATIVE, -1.007276));
        ionAdductMap.put("[M+Cl]-", new IonAdduct("[M+Cl]-", IonAdduct.IonSign.NEGATIVE, 34.969401));

        // experimental masses
        List<String> formulaStrings = new ArrayList<>();
        formulaStrings.add("C6H12O6");   // id =  1 [M+H]+, id =  2 [M+Na]+, id =  3 [M-H]-, id =  4 [M+Cl]-
        formulaStrings.add("C7H14O6");   // id =  5 [M+H]+, id =  6 [M+Na]+, id =  7 [M-H]-, id =  8 [M+Cl]-
        formulaStrings.add("C7H14O7");   // id =  9 [M+H]+, id = 10 [M+Na]+, id = 11 [M-H]-, id = 12 [M+Cl]-
        formulaStrings.add("C8H16O6");   // id = 13 [M+H]+, id = 14 [M+Na]+, id = 15 [M-H]-, id = 16 [M+Cl]-
        formulaStrings.add("C7H14O8");   // id = 17 [M+H]+, id = 18 [M+Na]+, id = 19 [M-H]-, id = 20 [M+Cl]-
        formulaStrings.add("C6H12O7");   // id = 21 [M+H]+, id = 22 [M+Na]+, id = 23 [M-H]-, id = 24 [M+Cl]-
        formulaStrings.add("C6H12O8");   // id = 25 [M+H]+, id = 26 [M+Na]+, id = 27 [M-H]-, id = 28 [M+Cl]-
        formulaStrings.add("C6H11O6N");  // id = 29 [M+H]+, id = 30 [M+Na]+, id = 31 [M-H]-, id = 32 [M+Cl]-
        formulaStrings.add("C6H10O6N2"); // id = 33 [M+H]+, id = 34 [M+Na]+, id = 35 [M-H]-, id = 36 [M+Cl]-
        int idExpMass = 1;
        for (String formulaString : formulaStrings) {
            Map<Element, Integer> formula = string2Formula(formulaString);
            double formulaMass = MDUtils.getMassFromFormula(formula);
            for (IonAdduct ionAdduct : ionAdductMap.values()) {
                expMassMap.put(
                    formulaString + "_" + ionAdduct.getName(),
                    new ExpMass(idExpMass++, formulaMass + ionAdduct.getMass())
                );
            }
        }

        // mass wrappers and assigned masses
        Set<String> ionAdductMapKeys = ionAdductMap.keySet();
        Set<String> expMassMapKeys = expMassMap.keySet();
        for (String expMassMapKey : expMassMapKeys) {
            for (String ionAdductMapKey : ionAdductMapKeys) {
                String key = expMassMapKey + "_" + ionAdductMapKey;

                // mass wrappers
                MassWrapper massWrapper = new MassWrapper(expMassMap.get(expMassMapKey), ionAdductMap.get(ionAdductMapKey));
                massWrapperMap.put(key, massWrapper);

                // assigned masses
                MassAssigned massAssigned = new MassAssigned(massWrapper, true);
                massAssigned.setFormula(string2Formula(expMassMapKey.split("_")[0]));
                massAssigned.setAssigned(true);
                massAssignedMap.put(key, massAssigned);
            }
        }

        // mass differences
        formulaStrings = new ArrayList<>();
        formulaStrings.add("C");
        formulaStrings.add("O");
        formulaStrings.add("N2");
        formulaStrings.add("CH2");
        formulaStrings.add("NH-1");
        formulaStrings.add("H2");
        formulaStrings.add("C2H5OH");
        formulaStrings.add("NH3");
        for (String formulaString : formulaStrings) {
            massDifferenceMap.put(
                formulaString,
                new MassDifference(formulaString, string2Formula(formulaString))
            );
        }

        // reference masses
        formulaStrings = new ArrayList<>();
        formulaStrings.add("C6H12O6");
        formulaStrings.add("C6H12O7");
        for (String formulaString : formulaStrings) {
            Map<Element, Integer> formula = string2Formula(formulaString);
            for (IonAdduct ionAdduct : ionAdductMap.values()) {
                double mass = MDUtils.getMassFromFormula(formula) + ionAdduct.getMass();
                refMassMap.put(
                        formulaString + "_" + ionAdduct.getName(),
                        new RefMass(formula, mass, ionAdduct)
                );
            }
        }
    }

    public Map<Element, Integer> string2Formula(String name) {
        Map<Element, Integer> formula = new HashMap<>();
        Pattern patternElements = Pattern.compile("([A-Z][a-z]?)(-?\\d*)");
        Matcher matcherElements = patternElements.matcher(name);
        while (matcherElements.find()) {
            String elementName = matcherElements.group(1);
            int elementCount = 1;
            if (!matcherElements.group(2).isEmpty()) {
                elementCount = Integer.parseInt(matcherElements.group(2));
            }
            Element element = elementMap.get(elementName);
            if (formula.containsKey(element)) {
                formula.put(element, formula.get(element) + elementCount);
            } else {
                formula.put(element, elementCount);
            }
        }
        return formula;
    }
}
