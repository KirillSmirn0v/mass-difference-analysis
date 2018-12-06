package mdPostProcessor;

import mdCoreElements.Element;
import mdGraphAssignment.MDAssignerInterface;
import mdGraphAssignment.MassAssigned;
import mdGraphConstruction.MassEdge;
import mdGraphConstruction.MassWrapper;
import mdGraphElements.MassDifference;
import utils.MDUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MDPostProcessor implements MDPostProcessorInterface {

    private MDAssignerInterface mdAssigner;
    private List<MassProcessed> massProcessedList = new ArrayList<>();
    private List<MassEdge> massEdges = new ArrayList<>();

    public MDPostProcessor(MDAssignerInterface mdAssigner) {
        this.mdAssigner = mdAssigner;
    }

    @Override
    public void squezeMassAssignedList() {
        massProcessedList = new ArrayList<>();

        List<MassAssigned> massAssignedList = mdAssigner.getMassAssignedList();
        massAssignedList.sort((x1, x2) -> {
           double diff = x2.getMass() - x1.getMass();
           if (diff > 0) { return -1; }
           if (diff < 0) { return 1; }
           return 0;
        });

        int id = 1;
        Map<Element, Integer> formula = massAssignedList.get(0).getFormula();
        List<MassWrapper> massWrappers = new ArrayList<>();
        for (MassAssigned massAssigned : massAssignedList) {
            if (massAssigned.getFormula().equals(formula)) {
                massWrappers.add(massAssigned.getMassWrapper());
            } else {
                MassProcessed massProcessed = new MassProcessed(
                    id,
                    massWrappers,
                    formula,
                    MDUtils.getMassFromFormula(formula)
                );
                massProcessedList.add(massProcessed);
                id++;
                formula = massAssigned.getFormula();
                massWrappers = new ArrayList<>();
                massWrappers.add(massAssigned.getMassWrapper());
            }
        }
        MassProcessed massProcessed = new MassProcessed(id, massWrappers, formula, MDUtils.getMassFromFormula(formula));
        massProcessedList.add(massProcessed);
    }

    @Override
    public void rebuildNetwork(Set<MassDifference> massDifferences) {
        massEdges = new ArrayList<>();
        for (MassDifference massDifference : massDifferences) {
            for (int i = 0; i < massProcessedList.size() - 1; i++) {
                Map<Element, Integer> source = massProcessedList.get(i).getFormula();
                for (int j = (i + 1); j < massProcessedList.size(); j++) {
                    Map<Element, Integer> target = massProcessedList.get(j).getFormula();
                    if (MDUtils.addSecondFormulaToFirst(source, massDifference.getFormula()).equals(target)) {
                        MassEdge massEdge = new MassEdge(i, j, massDifference);
                        massEdges.add(massEdge);
                    }
                }
            }
        }
    }

    @Override
    public List<MassProcessed> getMassProcessedList() {
        return massProcessedList;
    }

    @Override
    public List<MassEdge> getMassEdges() {
        return massEdges;
    }
}
