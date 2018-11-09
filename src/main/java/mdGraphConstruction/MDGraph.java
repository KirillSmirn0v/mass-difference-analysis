package mdGraphConstruction;

import mdGraphElements.MassDifference;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MDGraph implements MDGraphInterface {
    private MDPreprocessorInterface mdPreprocessor;
    private double edgeCreationError;
    private List<MassWrapper> massWrappers;
    private List<MassEdge> massEdges;

    public MDGraph(MDPreprocessorInterface mdPreprocessor) {
        this.mdPreprocessor = mdPreprocessor.getCopy();
        this.edgeCreationError = mdPreprocessor.getEdgeCreationError();
        this.massWrappers = mdPreprocessor.getMassWrappers();
        this.massEdges = new ArrayList<>();
    }

    @Override
    public void createEdges() {
        Set<MassDifference> massDifferences = mdPreprocessor.getMassDifferences();
        List<MassWrapper> massWrappers = mdPreprocessor.getMassWrappers();

        for (MassDifference massDifference : massDifferences) {
            for (int i = 0; i < massWrappers.size() - 1; i++) {
                MassWrapper source = massWrappers.get(i);
                for (int j = i+1; j < massWrappers.size(); j++) {
                    MassWrapper target = massWrappers.get(j);
                    if (validSourceAndTarget(source, target, massDifference)) {
                        MassEdge massEdge = new MassEdge(source, target, massDifference);
                        massEdges.add(massEdge);
                    }
                }
            }
        }
    }

    @Override
    public List<MassWrapper> getMassWrappers() {
        return massWrappers;
    }

    @Override
    public List<MassEdge> getMassEdges() {
        return massEdges;
    }

    private boolean validSourceAndTarget(MassWrapper source, MassWrapper target, MassDifference massDifference) {
        double massSource = source.getMass();
        double massTarget = target.getMass();
        double massMassDifference = massDifference.getMass();

        double massSourceToTarget = massSource + massMassDifference;
        double massTargetToSource = massTarget - massMassDifference;

        double ppmErrorTarget = 1000000 * (massSourceToTarget - massTarget) / massTarget;
        double ppmErrorSource = 1000000 * (massTargetToSource - massSource) / massSource;

        if (Math.abs(ppmErrorSource) < edgeCreationError & Math.abs(ppmErrorTarget) < edgeCreationError) {
            return true;
        }
        return false;
    }
}
