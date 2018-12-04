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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MDAssignerTest {
    private MDGraphInterface mockMDGraph;
    private MDAssignmentSettingsInterface mockMDAssignmentSettings;
    private MDAssigner mdAssigner;

    private IonAdduct adduct1 = new IonAdduct("Adduct1", IonAdduct.IonSign.POSITIVE, 50.0);
    private IonAdduct adduct2 = new IonAdduct("Adduct2", IonAdduct.IonSign.NEGATIVE, -30.0);

    @Before
    public void before() {

        mockMDAssignmentSettings = new MockMDAssignmentSettings() {
            @Override
            public double getRefError() {
                return 1.0;
            }

            @Override
            public List<RefMass> getRefMasses() {
                RefMass refMass1 = new RefMass(new HashMap<>(), 100.0, adduct1);
                RefMass refMass2 = new RefMass(new HashMap<>(), 200.0, adduct2);
                List<RefMass> refMasses = new ArrayList<>();
                refMasses.add(refMass1);
                refMasses.add(refMass2);
                return refMasses;
            }
        };

        mockMDGraph = new MockMDGraph() {
            @Override
            public List<MassWrapper> getMassWrappers() {
                MassWrapper massWrapper1 = new MassWrapper(new ExpMass(1, 100.0), adduct1);
                MassWrapper massWrapper2 = new MassWrapper(new ExpMass(2, 200.0), adduct1);
                MassWrapper massWrapper3 = new MassWrapper(new ExpMass(3, 200.0), adduct2);
                MassWrapper massWrapper4 = new MassWrapper(new ExpMass(4, 300.0), adduct2);
                List<MassWrapper> massWrappers = new ArrayList<>();
                massWrappers.add(massWrapper1);
                massWrappers.add(massWrapper2);
                massWrappers.add(massWrapper3);
                massWrappers.add(massWrapper4);
                return massWrappers;
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
        List<MassAssigned> massAssignedList = mdAssigner.getMassAssignedList();
        List<Pair<Double, Boolean>> expectedPairs = new ArrayList<>();
        expectedPairs.add(new Pair<>(50.0, false));
        expectedPairs.add(new Pair<>(150.0, true));
        expectedPairs.add(new Pair<>(230.0, false));
        expectedPairs.add(new Pair<>(330.0, true));

        for (int i = 0; i < massAssignedList.size(); i++) {
            Assert.assertEquals(expectedPairs.get(i).getKey(), massAssignedList.get(i).getMassWrapper().getMass(), 0.0);
            Assert.assertEquals(expectedPairs.get(i).getValue(), massAssignedList.get(i).isModifiable());
        }
    }
}
