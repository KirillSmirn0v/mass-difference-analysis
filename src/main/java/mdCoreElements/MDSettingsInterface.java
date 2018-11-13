package mdCoreElements;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

public interface MDSettingsInterface {
    void setDefaults();
    void readSettingsFromFile(File file) throws IOException;
    Set<Element> getElements();
    Map<String, Element> getName2ElementMap();
    Set<IonAdduct> getIonAdducts();
    Map<String, IonAdduct> getName2IonAdductMap();
}
