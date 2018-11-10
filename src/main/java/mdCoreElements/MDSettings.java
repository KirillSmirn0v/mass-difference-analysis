package mdCoreElements;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MDSettings implements MDSettingsInterface {
    private static Pattern patternElements = Pattern.compile("^element:\\s+([A-Z][a-z]?)\\s+(\\d+\\.?\\d*)\\s+(\\d+)$");
    private static Pattern patternIonAdducts = Pattern.compile("^ion:\\s+(\\S+)\\s+(-?\\d+\\.?\\d*)\\s+(POS|NEG)$");

    private Set<Element> elements;
    private Map<String, Element> name2ElementMap;
    private Set<IonAdduct> ionAdducts;
    private Map<String, IonAdduct> name2IonAdductMap;

    public MDSettings() {
        elements = new HashSet<>();
        name2ElementMap = new HashMap<>();
        ionAdducts = new HashSet<>();
        name2IonAdductMap = new HashMap<>();
        setDefaults();
    }

    private MDSettings(Set<Element> elements, Map<String, Element> name2ElementMap, Set<IonAdduct> ionAdducts) {
        this.elements = elements;
        this.name2ElementMap = name2ElementMap;
        this.ionAdducts = ionAdducts;
    }

    @Override
    public void setDefaults() {
        Element carbon = new Element("C", 12.000000, 4);
        elements.add(carbon);
        name2ElementMap.put("C", carbon);

        Element hydrogen = new Element("H", 1.007825, 1);
        elements.add(hydrogen);
        name2ElementMap.put("H", hydrogen);

        Element oxygen = new Element("O", 15.994915, 2);
        elements.add(oxygen);
        name2ElementMap.put("O", oxygen);

        Element nitrogen = new Element("N", 14.003074, 3);
        elements.add(nitrogen);
        name2ElementMap.put("N", nitrogen);

        IonAdduct hydrogenation = new IonAdduct("[M+H]+", IonAdduct.IonSign.POSITIVE, 1.007276);
        ionAdducts.add(hydrogenation);
        name2IonAdductMap.put("[M+H]+", hydrogenation);

        IonAdduct sodiation = new IonAdduct("[M+Na]+", IonAdduct.IonSign.POSITIVE, 34.989221);
        ionAdducts.add(sodiation);
        name2IonAdductMap.put("[M+Na]+", sodiation);
    }

    @Override
    public void readSettingsFromFile(File file) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        Scanner scanner = new Scanner(fileInputStream).useDelimiter("\\Z");
        readElementsAndIonAdducts(scanner);
        fileInputStream.close();
    }

    @Override
    public Set<Element> getElements() {
        return elements;
    }

    @Override
    public Map<String, Element> getName2ElementMap() {
        return name2ElementMap;
    }

    @Override
    public Set<IonAdduct> getIonAdducts() {
        return ionAdducts;
    }

    @Override
    public Map<String, IonAdduct> getName2IonAdductMap() {
        return name2IonAdductMap;
    }

    @Override
    public MDSettingsInterface getCopy() {
        Set<Element> elementsCopy = new HashSet<>();
        Map<String, Element> name2ElementMapCopy = new HashMap<>();
        Set<IonAdduct> ionAdductsCopy = new HashSet<>();
        for (Element element : getElements()) {
            Element elementCopy = new Element(element);
            elementsCopy.add(elementCopy);
            name2ElementMapCopy.put(elementCopy.getName(), elementCopy);
        }
        for (IonAdduct ionAdduct : getIonAdducts()) {
            IonAdduct ionAdductCopy = new IonAdduct(ionAdduct);
            ionAdductsCopy.add(ionAdductCopy);
        }
        return new MDSettings(elementsCopy, name2ElementMapCopy, ionAdductsCopy);
    }

    private void readElementsAndIonAdducts(Scanner scanner) {
        elements = new HashSet<>();
        name2ElementMap = new HashMap<>();
        ionAdducts = new HashSet<>();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Matcher matcherElements = patternElements.matcher(line);
            Matcher matcherIonAdducts = patternIonAdducts.matcher(line);
            if (matcherElements.matches()) {
                String name = matcherElements.group(1);
                double mass = Double.parseDouble(matcherElements.group(2));
                int valency = Integer.parseInt(matcherElements.group(3));
                Element element = new Element(name, mass, valency);
                elements.add(element);
                name2ElementMap.put(name, element);
            } else if (matcherIonAdducts.matches()) {
                String name = matcherIonAdducts.group(1);
                double mass = Double.parseDouble(matcherIonAdducts.group(2));
                String ionSign = matcherIonAdducts.group(3);
                if (ionSign.equals("POS")) {
                    ionAdducts.add(new IonAdduct(name, IonAdduct.IonSign.POSITIVE, mass));
                } else if (ionSign.equals("NEG")){
                    ionAdducts.add(new IonAdduct(name, IonAdduct.IonSign.NEGATIVE, mass));
                }
            }
        }
    }
}
