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
        Set<Map<Element, Integer>> formulas = massAssignedList.stream().map(MassAssigned::getFormula).collect(Collectors.toSet());

        for (Map<Element, Integer> formula : formulas) {
            List<MassWrapper> massWrappers =
                    massAssignedList.stream()
                            .filter(x -> formulas.contains(x.getFormula()))
                            .map(MassAssigned::getMassWrapper).collect(Collectors.toList());
            MassProcessed massProcessed = new MassProcessed(massWrappers, formula, MDUtils.getMassFromFormula(formula));
            massProcessedList.add(massProcessed);
        }

        massProcessedList.sort((massProcessed1, massProcessed2) -> {
            double diff = massProcessed2.getMass() - massProcessed1.getMass();
            if (diff > 0) { return -1; }
            return 1;
        });
    }

    @Override
    public void rebuildNetwork() {

    }
}
