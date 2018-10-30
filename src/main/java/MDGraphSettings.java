import java.io.File;
import java.util.List;

public class MDGraphSettings implements GraphSettingsInterface {
    private List<MassDifference> massDifferences;
    private double edgeCreationError;

    public MDGraphSettings() {
        setDefaults();
    }

    @Override
    public void setDefaults() {
        // TODO: fix the mass differences

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
}
