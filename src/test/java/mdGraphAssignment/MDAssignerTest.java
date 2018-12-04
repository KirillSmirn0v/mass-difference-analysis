package mdGraphAssignment;

import javafx.util.Pair;
import mdCoreData.ExpMass;
import mdCoreElements.IonAdduct;
import mdGraphConstruction.MDGraphInterface;
import mdGraphConstruction.MassWrapper;
import mdGraphContruction.MockMDGraph;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import testPool.MDTestPool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MDAssignerTest {
    private MDGraphInterface mockMDGraph;
    private MDAssignmentSettingsInterface mockMDAssignmentSettings;
    private MDAssigner mdAssigner;

    @Before
    public void before() {
        List<MassWrapper> mockMassWrappers = MDTestPool.getInstance().getMassWrapperPool(
            "C6H12O6_[M-H]-_[M-H]-", "C6H12O6_[M+Cl]-_[M+Cl]-", "C6H12O7_[M-H]-_[M-H]-",
            "C7H14O6_[M-H]-_[M-H]-", "C7H14O6_[M+Cl]-_[M+Cl]-", "C8H16O6_[M-H]-_[M-H]-",
            "C7H14O7_[M+Cl]-_[M+Cl]-", "C7H14O8_[M-H]-_[M-H]-", "C6H11O6N_[M+Cl]-_[M+Cl]-"
        );
        List<RefMass> mockRefMasses = MDTestPool.getInstance().getRefMassPool("C6H12O6_[M-H]-", "C6H12O6_[M+Cl]-", "C6H12O7_[M-H]-");

        mockMDAssignmentSettings = new MockMDAssignmentSettings() {
            @Override
            public double getRefError() {
                return 1.0;
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
}
