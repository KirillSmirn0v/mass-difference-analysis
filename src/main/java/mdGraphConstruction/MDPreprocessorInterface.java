package mdGraphConstruction;

import mdGraphElements.MassDifference;

import java.util.List;
import java.util.Set;

public interface MDPreprocessorInterface {
    void runPreprocessing();
    List<MassWrapper> getMassWrappers();
    Set<MassDifference> getMassDifferences();
    double getEdgeCreationError();
    MDPreprocessorInterface getCopy();
}
