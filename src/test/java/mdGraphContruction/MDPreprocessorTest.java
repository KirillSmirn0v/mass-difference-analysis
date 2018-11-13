package mdGraphContruction;

import mdCoreData.ExpMass;
import mdCoreElements.Element;
import mdCoreElements.IonAdduct;
import mdCoreElements.MDSettingsInterface;
import mdGraphConstruction.MDPreprocessor;
import mdGraphConstruction.MassWrapper;
import mdGraphElements.MDGraphSettingsInterface;
import mdGraphElements.MassDifference;
import mdGraphElements.MockMDGraphSettings;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MDPreprocessorTest {
    private MDPreprocessor mdPreprocessor = null;
    private MDGraphSettingsInterface mockMDGraphSettings = null;
    private List<ExpMass> mockExpMasses = null;

    @Before
    public void before() {
        mockExpMasses = new ArrayList<>();
        mockExpMasses.add(new ExpMass(10, 100 ));
        mockExpMasses.add(new ExpMass(20, 200));
        mockExpMasses.add(new ExpMass(30, 300));

        Set<IonAdduct> ionAdducts = new HashSet<>();
        ionAdducts.add(new IonAdduct("[M+55]", IonAdduct.IonSign.POSITIVE, 55.0));
        ionAdducts.add(new IonAdduct("[M-33]+", IonAdduct.IonSign.POSITIVE, -33.0));
        mockMDGraphSettings = new MockMDGraphSettings() {
            @Override
            public Set<IonAdduct> getIonAdducts() {
                return ionAdducts;
            }
        };

        mdPreprocessor = new MDPreprocessor(mockExpMasses, mockMDGraphSettings);
    }

    @After
    public void after() {
        mockExpMasses = null;
        mockMDGraphSettings = null;
        mdPreprocessor = null;
    }

    @Test
    public void test_runPreprocessing_createsCorrectMassWrappers() {
        mdPreprocessor.runPreprocessing();

        List<Double> expectedValues = new ArrayList<>();
        expectedValues.add(45.0);
        expectedValues.add(133.0);
        expectedValues.add(145.0);
        expectedValues.add(233.0);
        expectedValues.add(245.0);
        expectedValues.add(333.0);

        List<MassWrapper> massWrappers = mdPreprocessor.getMassWrappers();
        Assert.assertEquals(6, massWrappers.size());
        for (int i = 0; i < massWrappers.size(); i++) {
            Assert.assertEquals(expectedValues.get(i), massWrappers.get(i).getMass(), 0.0);
        }
    }
}
