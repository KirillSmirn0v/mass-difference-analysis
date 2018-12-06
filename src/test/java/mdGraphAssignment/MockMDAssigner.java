package mdGraphAssignment;

import java.util.ArrayList;
import java.util.List;

public class MockMDAssigner implements MDAssignerInterface {
    @Override
    public void setSeed(long seed) {}

    @Override
    public void runAssignmentAlgorithm() {}

    @Override
    public List<MassAssigned> getMassAssignedList() {
        return new ArrayList<>();
    }
}
