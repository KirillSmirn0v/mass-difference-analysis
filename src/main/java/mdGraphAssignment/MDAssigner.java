package mdGraphAssignment;

import mdCoreElements.Element;
import mdGraphConstruction.MDGraphInterface;
import mdGraphConstruction.MassEdge;
import mdGraphConstruction.MassWrapper;
import mdGraphElements.MassDifference;
import utils.MDUtils;

import java.util.*;
import java.util.stream.Collectors;

public class MDAssigner {
    private MDGraphInterface mdGraph;
    private MDAssignmentSettingsInterface mdAssignmentSettings;
    private List<MassAssigned> massAssignedList;
    private Map<MassEdge, Integer> edgeFailuresMap = new HashMap<>();

    public MDAssigner(MDGraphInterface mdGraph, MDAssignmentSettingsInterface mdAssignmentSettings) {
        this.mdGraph = mdGraph;
        this.mdAssignmentSettings = mdAssignmentSettings;
        this.massAssignedList = new ArrayList<>();
        findReferenceMasses(mdGraph.getMassWrappers(), mdAssignmentSettings.getRefMasses());
    }

    public void runAssignmentAlgorithm() {
        List<MassEdge> massEdges = mdGraph.getMassEdges();
        initEdgeFailuresMap(massEdges);

        boolean isAlgorithmFinished = false;
        while (!isAlgorithmFinished) {
            Collections.shuffle(massEdges);
            for (MassEdge massEdge : massEdges) {
                MassAssigned source = massAssignedList.get(massEdge.getSource());
                MassAssigned target = massAssignedList.get(massEdge.getTarget());
                MassDifference massDifference = massEdge.getMassDifference();
                boolean isSourceTargetEdgeTrustful = assumeSourceTargetAssignment(source, target, massDifference);
                boolean isTargetSourceEdgeTrustful = assumeTargetSourceAssignment(source, target, massDifference);
                if (!isSourceTargetEdgeTrustful || !isTargetSourceEdgeTrustful) {
                    edgeFailuresMap.put(massEdge, edgeFailuresMap.get(massEdge) + 1);
                }
            }
        }
    }

    private void findReferenceMasses(List<MassWrapper> massWrappers, List<RefMass> refMasses) {
        for (MassWrapper massWrapper : massWrappers) {
            final MassWrapper massWrapperFinal = massWrapper;
            List<RefMass> refMassesFiltered = refMasses.stream()
                    .filter(x -> Math.abs((massWrapperFinal.getMass()/x.getMass() - 1)) * 1000000 > mdAssignmentSettings.getRefError()).collect(Collectors.toList());
            if (refMassesFiltered.size() == 1) {
                MassAssigned massAssigned = new MassAssigned(massWrapper, false);
                massAssigned.setFormula(refMassesFiltered.get(0).getFormula());
                massAssigned.setAssigned(true);
                massAssignedList.add(massAssigned);
            } else {
                MassAssigned massAssigned = new MassAssigned(massWrapper, true);
                massAssignedList.add(massAssigned);
            }
        }
    }

    private void initEdgeFailuresMap(List<MassEdge> massEdges) {
        for (MassEdge massEdge : massEdges) {
            edgeFailuresMap.put(massEdge, 0);
        }
    }

    private boolean assumeSourceTargetAssignment(MassAssigned source, MassAssigned target, MassDifference massDifference) {
        if (!source.isAssigned() && target.isAssigned()) {
            double massSource = source.getMassWrapper().getMass();
            double massSourceAssumed = source.getMass();

            Map<Element, Integer> formulaTargetAssumed = MDUtils.addSecondFormulaToFirst(source.getFormula(), massDifference.getFormula());
            double massTarget = target.getMassWrapper().getMass();
            double massTargetAssumed = MDUtils.getMassFromFormula(formulaTargetAssumed);

            double ppmErrorSourceAssignment = MDUtils.getPPMError(massSource, massSourceAssumed);
            double ppmErrorTargetAssignment = MDUtils.getPPMError(massTarget, massTargetAssumed);

            boolean isValidFormula = MDUtils.isValidFormula(formulaTargetAssumed);
            boolean isAssignmentErrorAcceptable = Math.abs(ppmErrorTargetAssignment) < mdAssignmentSettings.getMaxAssignmentError();
            boolean isDiffErrorAcceptable = Math.abs(ppmErrorSourceAssignment - ppmErrorTargetAssignment) < mdAssignmentSettings.getMaxDiffError();

            if (target.isModifiable()) {
                if (isValidFormula && isAssignmentErrorAcceptable && isDiffErrorAcceptable) {
                    target.setFormula(formulaTargetAssumed);
                    target.setAssigned(true);
                    return true;
                } else {
                    source.setFormula(new HashMap<>());
                    source.setAssigned(false);
                    return false;
                }
            } else {
                if (formulaTargetAssumed.equals(target.getFormula())) {
                    return true;
                } else {
                    source.setFormula(new HashMap<>());
                    source.setAssigned(false);
                    return false;
                }
            }
        }
        return true;
    }

    private boolean assumeTargetSourceAssignment(MassAssigned source, MassAssigned target, MassDifference massDifference) {
        if (source.isAssigned() && !target.isAssigned()) {
            Map<Element, Integer> formulaSourceAssumed = MDUtils.subtractSecondFormulaFromFirst(target.getFormula(), massDifference.getFormula());
            double massSource = source.getMassWrapper().getMass();
            double massSourceAssumed = MDUtils.getMassFromFormula(formulaSourceAssumed);

            double massTarget = target.getMassWrapper().getMass();
            double massTargetAssumed = target.getMass();

            double ppmErrorSourceAssignment = MDUtils.getPPMError(massSource, massSourceAssumed);
            double ppmErrorTargetAssignment = MDUtils.getPPMError(massTarget, massTargetAssumed);

            boolean isValidFormula = MDUtils.isValidFormula(formulaSourceAssumed);
            boolean isAssignmentErrorAcceptable = Math.abs(ppmErrorSourceAssignment) < mdAssignmentSettings.getMaxAssignmentError();
            boolean isDiffErrorAcceptable = Math.abs(ppmErrorSourceAssignment - ppmErrorTargetAssignment) < mdAssignmentSettings.getMaxDiffError();

            if (source.isModifiable()) {
                if (isValidFormula && isAssignmentErrorAcceptable && isDiffErrorAcceptable) {
                    source.setFormula(formulaSourceAssumed);
                    source.setAssigned(true);
                    return true;
                } else {
                    target.setFormula(new HashMap<>());
                    target.setAssigned(false);
                    return false;
                }
            } else {
                if (formulaSourceAssumed.equals(source.getFormula())) {
                    return true;
                } else {
                    target.setFormula(new HashMap<>());
                    target.setAssigned(false);
                    return false;
                }
            }
        }
        return true;
    }

    public List<MassAssigned> getMassAssignedList() {
        return massAssignedList;
    }
}
