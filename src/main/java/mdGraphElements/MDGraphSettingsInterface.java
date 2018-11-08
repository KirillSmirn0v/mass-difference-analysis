package mdGraphElements;

import mdCoreElements.Element;
import mdCoreElements.IonAdduct;
import mdCoreElements.MDSettingsInterface;

import java.io.File;
import java.io.IOException;
import java.util.Set;

/**
 * Created by Kirill Smirnov on 11/8/2018.
 */
public interface MDGraphSettingsInterface {
    void setDefaults();
    void readSettingsFromFile(File file) throws IOException;
    void setEdgeCreationError(double edgeCreationError);
    Set<Element> getElements();
    Set<IonAdduct> getIonAdducts();
    Set<MassDifference> getMassDifferences();
    double getEdgeCreationError();
    MDSettingsInterface getMDSettings();
    MDGraphSettingsInterface getCopy();

}
