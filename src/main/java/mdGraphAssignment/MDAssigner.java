package mdGraphAssignment;

import mdGraphConstruction.MDGraph;
import mdGraphConstruction.MassWrapper;

import java.util.ArrayList;
import java.util.List;

public class MDAssigner {
    private MDGraph mdGraph;
    private MDAssignmentSettingsInterface mdAssignmentSettings;
    private List<MassAssigned> massAssignedList;

    public MDAssigner(MDGraph mdGraph, MDAssignmentSettingsInterface mdAssignmentSettings) {
        this.mdGraph = mdGraph;
        this.mdAssignmentSettings = mdAssignmentSettings;
        this.massAssignedList = new ArrayList<>();
        findReferenceMasses(mdGraph.getMassWrappers(), mdAssignmentSettings.getRefMasses());
    }

    public void runAssignmentAlgorithm() {

    }

    private void findReferenceMasses(List<MassWrapper> massWrappers, List<RefMass> refMasses) {
        
    }
}
