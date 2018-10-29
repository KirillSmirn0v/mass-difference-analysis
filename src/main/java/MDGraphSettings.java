import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MDGraphSettings implements GraphSettingsInterface {
    private List<Element> elements;
    private List<IonAdduct> ionAdducts;
    private double edgeCreationError;

    public MDGraphSettings() {
        setDefaults();
    }

    @Override
    public void setDefaults() {
        // TODO: fix the masses
        elements = new ArrayList<>();
        elements.add(new Element("C", 12.0, 4));
        elements.add(new Element("H", 1.0, 1));
        elements.add(new Element("O", 1.0, 2));
        elements.add(new Element("N", 1.0, 3));

        // TODO: fix the masses
        ionAdducts = new ArrayList<>();
        ionAdducts.add(new IonAdduct("[M+H]+", IonAdduct.IonSign.POSITIVE, 1.0));
        ionAdducts.add(new IonAdduct("[M+Na]+", IonAdduct.IonSign.POSITIVE, 1.0));

        edgeCreationError = 0.1;
    }

    @Override
    public void readSettingsFromFile(File file) {
        elements = new ArrayList<>();
        ionAdducts = new ArrayList<>();

        edgeCreationError = 0.1;
    }
}
