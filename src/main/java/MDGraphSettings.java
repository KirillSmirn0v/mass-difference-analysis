import java.io.File;
import java.util.*;
import java.util.function.Predicate;

public class MDGraphSettings implements GraphSettingsInterface, Observer {
    private MDSettings mdSettings;
    private List<MassDifference> massDifferences;
    private double edgeCreationError;

    public MDGraphSettings(MDSettings mdSettings) {
        this.mdSettings = mdSettings;
        mdSettings.addObserver(this);
        setDefaults();
    }

    @Override
    public void setDefaults() {
        Map<Element, Integer> formula = new HashMap<>();

        edgeCreationError = 0.1;
    }

    @Override
    public void readSettingsFromFile(File file) {
        // TODO: fix the mass differences

        edgeCreationError = 0.1;
    }

    public List<MassDifference> getMassDifferences() {
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
