package mdGraphContruction;

import mdGraphConstruction.MDPreprocessorInterface;
import mdGraphConstruction.MassWrapper;
import mdGraphElements.MassDifference;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MockMDPreprocessor implements MDPreprocessorInterface {

    @Override
    public void runPreprocessing() {

    }

    @Override
    public List<MassWrapper> getMassWrappers() {
        return new ArrayList<>();
    }

    @Override
    public Set<MassDifference> getMassDifferences() {
        return new HashSet<>();
    }

    @Override
    public double getEdgeCreationError() {
        return 0;
    }
}
