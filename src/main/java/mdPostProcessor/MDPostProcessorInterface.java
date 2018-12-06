package mdPostProcessor;

import mdGraphConstruction.MassEdge;
import mdGraphElements.MassDifference;

import java.util.List;
import java.util.Set;

public interface MDPostProcessorInterface {
    void squezeMassAssignedList();
    void rebuildNetwork(Set<MassDifference> massDifferences);
    List<MassProcessed> getMassProcessedList();
    List<MassEdge> getMassEdges();
}
