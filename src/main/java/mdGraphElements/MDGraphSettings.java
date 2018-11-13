package mdGraphElements;

import mdCoreElements.Element;
import mdCoreElements.IonAdduct;
import mdCoreElements.MDSettings;
import mdCoreElements.MDSettingsInterface;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MDGraphSettings implements MDGraphSettingsInterface {
    private static Pattern patternElementCounts = Pattern.compile("([A-Z][a-z]?)(\\d*)");

    private MDSettingsInterface mdSettings;
    private Set<MassDifference> massDifferences;
    private double edgeCreationError;

    public MDGraphSettings(MDSettingsInterface mdSettings) {
        this.mdSettings = mdSettings;
        this.massDifferences = new HashSet<>();
        setDefaults();
    }

    @Override
    public void setDefaults() {
        // set the default list of mass differences
        Set<Element> elements = mdSettings.getElements();
        Iterator<Element> elementIterator = elements.iterator();
        int id = 1;
        while (elementIterator.hasNext()) {
            Element element = elementIterator.next();
            Map<Element, Integer> formula = new HashMap<>();
            if (element.getValency() % 2 == 0) {
                formula.put(element, 1);
            } else {
                formula.put(element, 2);
            }
            MassDifference massDifference = new MassDifference(id, element.getName(), formula);
            massDifferences.add(massDifference);
        }

        // set the default value for 'edge creation error'
        edgeCreationError = 0.1;
    }

    @Override
    public void readSettingsFromFile(File file) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        Scanner scanner = new Scanner(fileInputStream).useDelimiter("\\Z");

        readMassDifferences(scanner);
        fileInputStream.close();
    }

    private void readMassDifferences(Scanner scanner) {
        this.massDifferences = new HashSet<>();
        Map<String, Element> name2ElementMap = mdSettings.getName2ElementMap();
        Set<String> elementNames = name2ElementMap.keySet();

        int id = 1;
        outerLoop: while (scanner.hasNext()) {
            Map<Element, Integer> formula = new HashMap<>();
            String line = scanner.nextLine();
            Matcher matcher = patternElementCounts.matcher(line);

            while (matcher.find()) {
                String name = matcher.group(1);
                int count = 1;
                if (!matcher.group(2).isEmpty()) {
                    count = Integer.parseInt(matcher.group(2));
                }
                if (elementNames.contains(name)) {
                    Element element = name2ElementMap.get(name);
                    if (formula.containsKey(element)) {
                        formula.put(element, formula.get(element) + count);
                    } else {
                        formula.put(name2ElementMap.get(name), count);
                    }
                } else {
                    continue outerLoop;
                }
            }

            MassDifference massDifference = new MassDifference(id, line, formula);
            this.massDifferences.add(massDifference);
            id++;
        }
        scanner.close();
    }

    @Override
    public void setEdgeCreationError(double edgeCreationError) {
        this.edgeCreationError = edgeCreationError;
    }

    @Override
    public Set<Element> getElements() {
        return mdSettings.getElements();
    }

    @Override
    public Set<IonAdduct> getIonAdducts() {
        return mdSettings.getIonAdducts();
    }

    @Override
    public MDSettingsInterface getMDSettings() {
        return mdSettings;
    }

    @Override
    public Set<MassDifference> getMassDifferences() {
        return massDifferences;
    }

    @Override
    public double getEdgeCreationError() {
        return edgeCreationError;
    }
}
