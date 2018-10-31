package mdGraphElements;

import abstracts.GraphSettingsInterface;
import mdCoreElements.Element;
import mdCoreElements.MDSettings;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MDGraphSettings implements GraphSettingsInterface, Observer {
    private static Pattern patternElementCounts = Pattern.compile("([A-Z][a-z]?)(\\d*)");

    private MDSettings mdSettings;
    private Set<MassDifference> massDifferences;
    private double edgeCreationError;

    public MDGraphSettings(MDSettings mdSettings) {
        this.mdSettings = mdSettings;
        this.massDifferences = new HashSet<>();
        mdSettings.addObserver(this);
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
                int count = Integer.parseInt(matcher.group(2));
                if (elementNames.contains(name)) {
                    formula.put(name2ElementMap.get(name), count);
                } else {
                    continue outerLoop;
                }
            }

            MassDifference massDifference = new MassDifference(id, line, formula);
            id++;
        }
        scanner.close();
    }

    public void setEdgeCreationError(double edgeCreationError) {
        this.edgeCreationError = edgeCreationError;
    }

    public Set<MassDifference> getMassDifferences() {
        return massDifferences;
    }

    public double getEdgeCreationError() {
        return edgeCreationError;
    }

    @Override
    public void update(Observable o, Object arg) {
        Set<Element> elements = mdSettings.getElements();
        Predicate<MassDifference> predicate = massDifference ->
            !elements.containsAll(massDifference.getFormula().keySet());
        massDifferences.removeIf(predicate);
    }
}
