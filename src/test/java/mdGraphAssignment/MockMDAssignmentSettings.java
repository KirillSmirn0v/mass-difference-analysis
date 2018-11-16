package mdGraphAssignment;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MockMDAssignmentSettings implements MDAssignmentSettingsInterface {
    @Override
    public void readSettingsFromFile(File file) throws IOException {

    }

    @Override
    public List<RefMass> getRefMasses() {
        return new ArrayList<>();
    }

    @Override
    public double getRefError() {
        return 0;
    }

    @Override
    public double getMaxAssignmentError() {
        return 0;
    }

    @Override
    public double getMaxDiffError() {
        return 0;
    }

    @Override
    public int getMaxEdgeInconsistencies() {
        return 0;
    }

    @Override
    public int getMaxSameIterations() {
        return 0;
    }
}
