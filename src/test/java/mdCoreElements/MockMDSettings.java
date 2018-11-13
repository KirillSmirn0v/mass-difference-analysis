package mdCoreElements;


import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MockMDSettings implements MDSettingsInterface {

    @Override
    public void setDefaults() {

    }

    @Override
    public void readSettingsFromFile(File file) throws IOException {

    }

    @Override
    public Set<Element> getElements() {
        return new HashSet<>();
    }

    @Override
    public Map<String, Element> getName2ElementMap() {
        return new HashMap<>();
    }

    @Override
    public Set<IonAdduct> getIonAdducts() {
        return new HashSet<>();
    }

    @Override
    public Map<String, IonAdduct> getName2IonAdductMap() {
        return new HashMap<>();
    }
}
