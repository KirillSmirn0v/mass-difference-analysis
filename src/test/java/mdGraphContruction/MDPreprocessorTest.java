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
import testPool.MDTestPool;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MDPreprocessorTest {
    private MDPreprocessor mdPreprocessor = null;
    private MDGraphSettingsInterface mockMDGraphSettings = null;

    @Before
    public void before() {
        List<ExpMass> mockExpMasses =
                MDTestPool.getInstance().getExpMassPool("C6H12O6_[M+H]+", "C7H14O6_[M+Na]+", "C6H12O8_[M+Na]+");
        Set<IonAdduct> mockIonAdducts =
                MDTestPool.getInstance().getIonAdductPool("[M+H]+", "[M+Na]+");

        mockMDGraphSettings = new MockMDGraphSettings() {
            @Override
            public Set<IonAdduct> getIonAdducts() {
                return mockIonAdducts;
            }
        };
        mdPreprocessor = new MDPreprocessor(mockExpMasses, mockMDGraphSettings);
    }

    @After
    public void after() {
        mockMDGraphSettings = null;
        mdPreprocessor = null;
    }

    @Test
    public void test_runPreprocessing_createsCorrectMassWrappers() {
        mdPreprocessor.runPreprocessing();

        List<MassWrapper> expectedMassWrappers = MDTestPool.getInstance().getMassWrapperPool(
                "C6H12O6_[M+H]+_[M+H]+", "C7H14O6_[M+Na]+_[M+H]+", "C6H12O8_[M+Na]+_[M+H]+",
                "C6H12O6_[M+H]+_[M+Na]+", "C7H14O6_[M+Na]+_[M+Na]+", "C6H12O8_[M+Na]+_[M+Na]+"
        );

        List<MassWrapper> massWrappers = mdPreprocessor.getMassWrappers();
        Assert.assertEquals(expectedMassWrappers.size(), massWrappers.size());
        Assert.assertTrue(massWrappers.containsAll(expectedMassWrappers));
    }
}
