package mdGraphAssignment;

import java.util.List;

public interface MDAssignerInterface {
    void setSeed(long seed);
    void runAssignmentAlgorithm();
    List<MassAssigned> getMassAssignedList();
}
