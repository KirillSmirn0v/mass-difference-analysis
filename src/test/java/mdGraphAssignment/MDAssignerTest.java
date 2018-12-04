package mdGraphAssignment;

import mdCoreElements.Element;
import mdGraphConstruction.MDGraphInterface;
import mdGraphConstruction.MassEdge;
import mdGraphConstruction.MassWrapper;
import mdGraphContruction.MockMDGraph;
import mdGraphElements.MassDifference;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import testPool.MDTestPool;

import java.util.*;
import java.util.stream.Collectors;

public class MDAssignerTest {
    private MDGraphInterface mockMDGraph;
    private MDAssignmentSettingsInterface mockMDAssignmentSettings;
    private MDAssignerInterface mdAssigner;

    @Before
    public void before() {
        List<MassWrapper> mockMassWrappers = MDTestPool.getInstance().getMassWrapperPool(
            "C6H12O6_[M-H]-_[M-H]-", "C6H12O6_[M+Cl]-_[M+Cl]-", "C6H12O7_[M-H]-_[M-H]-",
            "C7H14O6_[M-H]-_[M-H]-", "C7H14O6_[M+Cl]-_[M+Cl]-", "C8H16O6_[M-H]-_[M-H]-",
            "C7H14O7_[M+Cl]-_[M+Cl]-", "C7H14O8_[M-H]-_[M-H]-", "C6H11O6N_[M+Cl]-_[M+Cl]-"
        );
        List<RefMass> mockRefMasses = MDTestPool.getInstance().getRefMassPool("C6H12O6_[M-H]-", "C6H12O6_[M+Cl]-", "C6H12O7_[M-H]-");
        Set<MassDifference> mockMassDifferences = MDTestPool.getInstance().getMassDifferencePool("CH2", "O", "NH-1");
        Map<String, MassDifference> massDifferenceMap = new HashMap<>();
        for (MassDifference mockMassDifference : mockMassDifferences) {
            massDifferenceMap.put(mockMassDifference.getName(), mockMassDifference);
        }

        List<MassEdge> mockMassEdges = new ArrayList<>();
        mockMassEdges.add(new MassEdge(0, 2, massDifferenceMap.get("O")));
        mockMassEdges.add(new MassEdge(1, 2, massDifferenceMap.get("O")));
        mockMassEdges.add(new MassEdge(3, 6, massDifferenceMap.get("O")));
        mockMassEdges.add(new MassEdge(4, 6, massDifferenceMap.get("O")));
        mockMassEdges.add(new MassEdge(6, 7, massDifferenceMap.get("O")));
        mockMassEdges.add(new MassEdge(0, 3, massDifferenceMap.get("CH2")));
        mockMassEdges.add(new MassEdge(0, 4, massDifferenceMap.get("CH2")));
        mockMassEdges.add(new MassEdge(1, 3, massDifferenceMap.get("CH2")));
        mockMassEdges.add(new MassEdge(1, 4, massDifferenceMap.get("CH2")));
        mockMassEdges.add(new MassEdge(3, 5, massDifferenceMap.get("CH2")));
        mockMassEdges.add(new MassEdge(4, 5, massDifferenceMap.get("CH2")));
        mockMassEdges.add(new MassEdge(2, 6, massDifferenceMap.get("CH2")));
        mockMassEdges.add(new MassEdge(0, 8, massDifferenceMap.get("NH-1")));
        mockMassEdges.add(new MassEdge(1, 8, massDifferenceMap.get("NH-1")));

        mockMDAssignmentSettings = new MockMDAssignmentSettings() {
            @Override
            public double getRefError() {
                return 0.1;
            }

            @Override
            public double getMaxAssignmentError() {
                return 5.0;
            }

            @Override
            public double getMaxDiffError() {
                return 0.3;
            }

            @Override
            public int getMaxEdgeInconsistencies() {
                return 5;
            }

            @Override
            public int getMaxSameIterations() {
                return 10;
            }

            @Override
            public List<RefMass> getRefMasses() {
                return mockRefMasses;
            }
        };

        mockMDGraph = new MockMDGraph() {
            @Override
            public List<MassWrapper> getMassWrappers() {
                return mockMassWrappers;
            }

            @Override
            public List<MassEdge> getMassEdges() {
                return mockMassEdges;
            }
        };

        mdAssigner = new MDAssigner(mockMDGraph, mockMDAssignmentSettings);
    }

    @After
    public void after() {
        mockMDAssignmentSettings = null;
        mockMDGraph = null;
        mdAssigner = null;
    }

    @Test
    public void test_init_hasReferenceMasses_rightAmountOfUnmodifiableAssignedMasses() {
        List<MassWrapper> expectedMassWrappers = MDTestPool.getInstance().getMassWrapperPool(
            "C6H12O6_[M-H]-_[M-H]-", "C6H12O6_[M+Cl]-_[M+Cl]-", "C6H12O7_[M-H]-_[M-H]-"
        );

        List<MassAssigned> massAssignedList = mdAssigner.getMassAssignedList();
        List<MassWrapper> actualMassWrappers = massAssignedList.stream()
            .filter(x -> expectedMassWrappers.contains(x.getMassWrapper()) && !x.isModifiable())
            .map(MassAssigned::getMassWrapper).collect(Collectors.toList());

        Assert.assertEquals(expectedMassWrappers.size(), actualMassWrappers.size());
    }

    @Test
    public void test_runAssignmentAlgorithm_rightAssignmentsAreProduced() {
        mdAssigner.runAssignmentAlgorithm();

        List<MassWrapper> massWrappers = mockMDGraph.getMassWrappers();
        List<Map<Element, Integer>> expectedFormulas = new ArrayList<>();
        expectedFormulas.add(MDTestPool.getInstance().string2Formula("C6H12O6"));
        expectedFormulas.add(MDTestPool.getInstance().string2Formula("C6H12O6"));
        expectedFormulas.add(MDTestPool.getInstance().string2Formula("C6H12O7"));
        expectedFormulas.add(MDTestPool.getInstance().string2Formula("C7H14O6"));
        expectedFormulas.add(MDTestPool.getInstance().string2Formula("C7H14O6"));
        expectedFormulas.add(MDTestPool.getInstance().string2Formula("C8H16O6"));
        expectedFormulas.add(MDTestPool.getInstance().string2Formula("C7H14O7"));
        expectedFormulas.add(MDTestPool.getInstance().string2Formula("C7H14O8"));
        expectedFormulas.add(MDTestPool.getInstance().string2Formula("C6H11O6N"));

        List<MassAssigned> massAssignedList = mdAssigner.getMassAssignedList();
        Assert.assertEquals(massWrappers.size(), massAssignedList.size());
        for (int i = 0; i < massWrappers.size(); i++) {
            MassWrapper massWrapper = massWrappers.get(i);
            MassAssigned massAssigned = massAssignedList.get(i);
            Assert.assertEquals(massWrapper.getMass(), massAssigned.getMass(), 0.000001);
            Assert.assertEquals(expectedFormulas.get(i), massAssigned.getFormula());
        }
    }
}
