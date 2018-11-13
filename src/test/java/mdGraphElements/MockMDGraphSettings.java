package mdGraphElements;

import mdCoreElements.Element;
import mdCoreElements.IonAdduct;
import mdCoreElements.MDSettingsInterface;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class MockMDGraphSettings implements MDGraphSettingsInterface {

    @Override
    public void setDefaults() {

    }

    @Override
    public void readSettingsFromFile(File file) throws IOException {

    }

    @Override
    public void setEdgeCreationError(double edgeCreationError) {

    }

    @Override
    public Set<Element> getElements() {
        return new HashSet<>();
    }

    @Override
    public Set<IonAdduct> getIonAdducts() {
        return new HashSet<>();
    }

    @Override
    public Set<MassDifference> getMassDifferences() {
        return new HashSet<>();
    }

    @Override
    public double getEdgeCreationError() {
        return 0;
    }

    @Override
    public MDSettingsInterface getMDSettings() {
        return null;
    }
}
