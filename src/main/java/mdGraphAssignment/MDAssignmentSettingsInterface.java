package mdGraphAssignment;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface MDAssignmentSettingsInterface {
    void readSettingsFromFile(File file) throws IOException;
    List<RefMass> getRefMasses();
    double getMaxAssignmentError();
    double getMaxDiffError();
    int getMaxEdgeInconsistencies();
    int getMaxSameIterations();
}
