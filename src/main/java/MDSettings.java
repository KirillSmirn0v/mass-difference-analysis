import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Observable;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MDSettings extends Observable implements SettingsInterface {
    private static Pattern patternElements = Pattern.compile("^Element:\\s+([A-Z][a-z]?)\\s+(\\d+\\.?\\d*)\\s+(\\d+)$");
    private static Pattern patternIonAdducts = Pattern.compile("^Ion:\\s+(\\S+)\\s+(\\d+\\.?\\d*)\\s+(POS|NEG)$");

    private static Set<Element> elements;
    private static Set<IonAdduct> ionAdducts;

    public MDSettings() {
        setDefaults();
    }

    @Override
    public void setDefaults() {
        elements.add(new Element("C", 12.000000, 4)); // add Carbon
        elements.add(new Element("H", 1.007825, 1)); // add Hydrogen
        elements.add(new Element("O", 15.994915, 2)); // add Oxygen
        elements.add(new Element("N", 14.003074, 3)); // add Nitrogen

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

    public Set<IonAdduct> getIonAdducts() {
        return ionAdducts;
    }

    private void readElements(Scanner scanner) {
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Matcher matcher = patternElements.matcher(line);
            if (matcher.matches()) {
                String name = matcher.group(1);
                double mass = Double.parseDouble(matcher.group(2));
                int valency = Integer.parseInt(matcher.group(3));
                elements.add(new Element(name, mass, valency));
            }
        }
        scanner.close();
    }

    private void readIonAdducts(Scanner scanner) {
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
