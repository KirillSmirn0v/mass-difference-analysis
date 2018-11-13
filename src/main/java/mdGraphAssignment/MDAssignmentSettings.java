package mdGraphAssignment;

import mdCoreElements.Element;
import mdCoreElements.IonAdduct;
import mdCoreElements.MDSettingsInterface;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MDAssignmentSettings implements MDAssignmentSettingsInterface {
    private static Pattern patternRefMasses = Pattern.compile("reference:\\s+([A-Za-z0-9]+)\\s+(\\d+\\.?\\d*)\\s+(\\S+)");
    private static Pattern patternElements = Pattern.compile("([A-Z][a-z]?)(\\d*)");
    private static Pattern patternParameters = Pattern.compile("([a-z\\s]+):\\s+(\\d+\\.?\\d*)");

    private MDSettingsInterface mdSettings;
    private List<RefMass> refMasses;
    private double maxAssignmentError;
    private double maxDiffError;
    private int maxEdgeInconsistencies;
    private int maxSameIterations;

    public MDAssignmentSettings(MDSettingsInterface mdSettings) {
        this.mdSettings = mdSettings;
        setDefaults();
    }

    private void setDefaults() {
        refMasses = new ArrayList<>();
        maxAssignmentError = 5.0;
        maxDiffError = 0.3;
        maxEdgeInconsistencies = 5;
        maxSameIterations = 10;
    }

    @Override
    public void readSettingsFromFile(File file) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        Scanner scanner = new Scanner(fileInputStream).useDelimiter("\\Z");

        readSettings(scanner);
        fileInputStream.close();
    }

    private void readSettings(Scanner scanner) {
        refMasses = new ArrayList<>();
        Map<String, Element> name2ElementMap = mdSettings.getName2ElementMap();
        Map<String, IonAdduct> name2IonAdductMap = mdSettings.getName2IonAdductMap();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Matcher matcherRefMasses = patternRefMasses.matcher(line);
            Matcher matcherParameters = patternParameters.matcher(line);
            if (matcherRefMasses.matches()) {
                String formulaName = matcherRefMasses.group(1);
                String formulaMass = matcherRefMasses.group(2);
                String formulaAdduct = matcherRefMasses.group(3);
                readFormula(formulaName, formulaMass, formulaAdduct, name2ElementMap, name2IonAdductMap);
            } else if (matcherParameters.matches()) {
                String parName = matcherParameters.group(1);
                String parValue = matcherParameters.group(2);
                readParameter(parName, parValue);
            }
        }
        scanner.close();
    }

    private void readFormula(String formulaName, String formulaMass, String formulaAdduct, Map<String, Element> name2ElementMap, Map<String, IonAdduct> name2IonAdductMap) {
        Matcher matcherElements = patternElements.matcher(formulaName);
        Map<Element, Integer> refFormula = new HashMap<>();
        while (matcherElements.find()) {
            String elementName = matcherElements.group(1);
            int elementCount = 1;
            if (!matcherElements.group(2).isEmpty()) {
                elementCount = Integer.parseInt(matcherElements.group(2));
            }
            if (name2ElementMap.keySet().contains(elementName)) {
                Element element = name2ElementMap.get(elementName);
                if (refFormula.containsKey(element)) {
                    refFormula.put(element, refFormula.get(element) + elementCount);
                } else {
                    refFormula.put(name2ElementMap.get(elementName), elementCount);
                }
            } else {
                return;
            }
        }
        double refMass = Double.parseDouble(formulaMass);
        if (name2IonAdductMap.keySet().contains(formulaAdduct)) {
            IonAdduct refIonAdduct = name2IonAdductMap.get(formulaAdduct);
            refMasses.add(new RefMass(refFormula, refMass, refIonAdduct));
        }
    }

    private void readParameter(String parName, String parValue) {
        switch (parName) {
            case "max assignment error":
                maxAssignmentError = Double.parseDouble(parValue);
                break;
            case "max difference error":
                maxDiffError = Double.parseDouble(parValue);
                break;
            case "max edge inconsistencies":
                maxEdgeInconsistencies = Integer.parseInt(parValue);
                break;
            case "max same iterations":
                maxSameIterations = Integer.parseInt(parValue);
                break;
            default:
                break;
        }

    }

    @Override
    public List<RefMass> getRefMasses() {
        return refMasses;
    }

    @Override
    public double getMaxAssignmentError() {
        return maxAssignmentError;
    }

    @Override
    public double getMaxDiffError() {
        return maxDiffError;
    }

    @Override
    public int getMaxEdgeInconsistencies() {
        return maxEdgeInconsistencies;
    }

    @Override
    public int getMaxSameIterations() {
        return maxSameIterations;
    }
}
