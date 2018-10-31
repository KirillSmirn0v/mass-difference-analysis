package mdCoreElements;

import abstracts.SettingsInterface;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MDSettings extends Observable implements SettingsInterface {
    private static Pattern patternElements = Pattern.compile("^element:\\s+([A-Z][a-z]?)\\s+(\\d+\\.?\\d*)\\s+(\\d+)$");
    private static Pattern patternIonAdducts = Pattern.compile("^ion:\\s+(\\S+)\\s+(\\d+\\.?\\d*)\\s+(POS|NEG)$");

    private Set<Element> elements;
    private Map<String, Element> name2ElementMap;
    private Set<IonAdduct> ionAdducts;

    public MDSettings() {
        elements = new HashSet<>();
        name2ElementMap = new HashMap<>();
        ionAdducts = new HashSet<>();
        setDefaults();
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

        ionAdducts.add(new IonAdduct("[M+H]+", IonAdduct.IonSign.POSITIVE, 1.007276));
        ionAdducts.add(new IonAdduct("[M+Na]+", IonAdduct.IonSign.POSITIVE, 34.989221));
    }

    @Override
    public void readSettingsFromFile(File file) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        Scanner scanner;

        scanner = new Scanner(fileInputStream).useDelimiter("\\Z");
        readElements(scanner);
        fileInputStream.close();

        scanner = new Scanner(fileInputStream).useDelimiter("\\Z");
        readIonAdducts(scanner);
        fileInputStream.close();

        notifyObservers();
    }

    public Set<Element> getElements() {
        return elements;
    }

    public Map<String, Element> getName2ElementMap() {
        return name2ElementMap;
    }

    public Set<IonAdduct> getIonAdducts() {
        return ionAdducts;
    }

    private void readElements(Scanner scanner) {
        elements = new HashSet<>();
        name2ElementMap = new HashMap<>();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Matcher matcher = patternElements.matcher(line);
            if (matcher.matches()) {
                String name = matcher.group(1);
                double mass = Double.parseDouble(matcher.group(2));
                int valency = Integer.parseInt(matcher.group(3));
                Element element = new Element(name, mass, valency);
                elements.add(element);
                name2ElementMap.put(name, element);
            }
        }
        scanner.close();
    }

    private void readIonAdducts(Scanner scanner) {
        ionAdducts = new HashSet<>();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Matcher matcher = patternIonAdducts.matcher(line);
            if (matcher.matches()) {
                String name = matcher.group(1);
                double mass = Double.parseDouble(matcher.group(2));
                String ionSign = matcher.group(3);
                if (ionSign.equals("POS")) {
                    ionAdducts.add(new IonAdduct(name, IonAdduct.IonSign.POSITIVE, mass));
                } else if (ionSign.equals("NEG")){
                    ionAdducts.add(new IonAdduct(name, IonAdduct.IonSign.NEGATIVE, mass));
                }
            }
        }
        scanner.close();
    }
}
