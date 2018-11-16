package mdGraphAssignment;

import mdGraphConstruction.MDGraphInterface;
import mdGraphConstruction.MassWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MDAssigner {
    private MDGraphInterface mdGraph;
    private MDAssignmentSettingsInterface mdAssignmentSettings;
    private List<MassAssigned> massAssignedList;

    public MDAssigner(MDGraphInterface mdGraph, MDAssignmentSettingsInterface mdAssignmentSettings) {
        this.mdGraph = mdGraph;
        this.mdAssignmentSettings = mdAssignmentSettings;
        this.massAssignedList = new ArrayList<>();
        findReferenceMasses(mdGraph.getMassWrappers(), mdAssignmentSettings.getRefMasses());
    }

    public void runAssignmentAlgorithm() {

    }

    private void findReferenceMasses(List<MassWrapper> massWrappers, List<RefMass> refMasses) {
        for (MassWrapper massWrapper : massWrappers) {
            final MassWrapper massWrapperFinal = massWrapper;
            List<RefMass> refMassesFiltered = refMasses.stream()
                    .filter(x -> Math.abs((massWrapperFinal.getMass()/x.getMass() - 1)) * 1000000 > mdAssignmentSettings.getRefError()).collect(Collectors.toList());
            if (refMassesFiltered.size() == 1) {
                MassAssigned massAssigned = new MassAssigned(massWrapper, false);
                massAssignedList.add(massAssigned);
            } else {
                MassAssigned massAssigned = new MassAssigned(massWrapper, true);
                massAssignedList.add(massAssigned);
            }
        }
    }

    public List<MassAssigned> getMassAssignedList() {
        return massAssignedList;
    }
}
