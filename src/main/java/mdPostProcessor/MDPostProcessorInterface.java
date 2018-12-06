package mdPostProcessor;

import java.util.List;

public interface MDPostProcessorInterface {
    void squezeMassAssignedList();
    void rebuildNetwork();
    List<MassProcessed> getMassProcessedList();
}
