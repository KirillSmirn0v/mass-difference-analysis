package mdPostProcessor;

import mdCoreElements.Element;
import mdGraphAssignment.MDAssignerInterface;
import mdGraphAssignment.MassAssigned;
import mdGraphConstruction.MassWrapper;
import utils.MDUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class MDPostProcessor implements MDPostProcessorInterface {

    private MDAssignerInterface mdAssigner;
    private List<MassProcessed> massProcessedList = new ArrayList<>();

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
    public void rebuildNetwork() {

    }

    @Override
    public List<MassProcessed> getMassProcessedList() {
        return massProcessedList;
    }
}
