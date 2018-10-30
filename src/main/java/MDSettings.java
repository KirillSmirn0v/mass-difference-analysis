import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;

public class MDSettings implements SettingsInterface {
    private static Pattern patternElements = Pattern.compile("^Element:\\s+([A-Z][a-z]?)\\s+(\\d+\\.?\\d*)\\s+(\\d+)$");
    private static Pattern patternIonAdducts = Pattern.compile("^Ion:\\s+(\\S+)\\s+(\\d+\\.?\\d*)\\s+(POS|NEG)$");

    private Set<Element> elements;
    private Set<IonAdduct> ionAdducts;

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
    public void readSettingsFromFile(File file) {
        FileInputStream fileInputStream = new FileInputStream(file);
        Scanner scanner = new Scanner(fileInputStream).useDelimiter("\\Z");

        readElements(scanner);

        readIonAdducts(scanner);
    }

    private void readElements(Scanner scanner) {

    }

    private void readIonAdducts(Scanner scanner) {

    }
}
