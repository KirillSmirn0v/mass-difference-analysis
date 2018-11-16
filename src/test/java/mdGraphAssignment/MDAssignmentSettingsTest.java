package mdGraphAssignment;

import mdCoreElements.Element;
import mdCoreElements.IonAdduct;
import mdCoreElements.MDSettingsInterface;
import mdCoreElements.MockMDSettings;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MDAssignmentSettingsTest {
    private static final Path BASE_PATH = Paths.get("src/test/resources");
    private static final File TEST_GRAPHASSIGNMENT_OK = BASE_PATH.resolve("mdGraphAssignment/test_graphAssignment_ok.txt").toFile();

    private MDSettingsInterface mockMDSettings;
    private MDAssignmentSettingsInterface mdAssignmentSettings;

    private Element element1 = new Element("C", 1.0, 1);
    private Element element2 = new Element("H", 2.0, 2);
    private Element element3 = new Element("O", 3.0, 3);
    private IonAdduct adduct1 = new IonAdduct("[One]", IonAdduct.IonSign.POSITIVE, 0.2);
    private IonAdduct adduct2 = new IonAdduct("[Two]", IonAdduct.IonSign.NEGATIVE, -0.3);

    @Before
    public void before() {
        Map<String, Element> mockName2ElementMap = new HashMap<>();
        mockName2ElementMap.put("C", element1);
        mockName2ElementMap.put("H", element2);
        mockName2ElementMap.put("O", element3);

        Map<String, IonAdduct> mockName2IonAdductMap = new HashMap<>();
        mockName2IonAdductMap.put("[One]", adduct1);
        mockName2IonAdductMap.put("[Two]", adduct2);

        mockMDSettings = new MockMDSettings() {
            @Override
            public Map<String, Element> getName2ElementMap() {
                return mockName2ElementMap;
            }

            @Override
            public Map<String, IonAdduct> getName2IonAdductMap() {
                return mockName2IonAdductMap;
            }
        };
        mdAssignmentSettings = new MDAssignmentSettings(mockMDSettings);
    }

    @After
    public void after() {
        mockMDSettings = null;
        mdAssignmentSettings = null;
    }

    @Test
    public void test_fileHasCorrectDataFormat_correctDataIsRead() {
        try {
            mdAssignmentSettings.readSettingsFromFile(TEST_GRAPHASSIGNMENT_OK);
            List<RefMass> refMasses = mdAssignmentSettings.getRefMasses();
            double refError = mdAssignmentSettings.getRefError();
            double maxAssignentError = mdAssignmentSettings.getMaxAssignmentError();
            double maxDiffError = mdAssignmentSettings.getMaxDiffError();
            int maxEdgeInconsistencies = mdAssignmentSettings.getMaxEdgeInconsistencies();
            int maxSameIterations = mdAssignmentSettings.getMaxSameIterations();

            List<Map<Element, Integer>> expectedFormulas = new ArrayList<>();
            Map<Element, Integer> formula;
            formula = new HashMap<>();
            formula.put(element1, 2);
            formula.put(element2, 6);
            formula.put(element3, 1);
            expectedFormulas.add(formula);
            formula = new HashMap<>();
            formula.put(element1, 3);
            formula.put(element2, 1);
            formula.put(element3, 2);
            expectedFormulas.add(formula);

            List<Double> expectedMasses = new ArrayList<>();
            expectedMasses.add(50.2);
            expectedMasses.add(99.7);

            List<IonAdduct> expectedIonAdducts = new ArrayList<>();
            expectedIonAdducts.add(adduct1);
            expectedIonAdducts.add(adduct2);

            Assert.assertEquals(2, refMasses.size());
            for (int i = 0; i < refMasses.size(); i++) {
                Assert.assertEquals(expectedFormulas.get(i), refMasses.get(i).getFormula());
                Assert.assertEquals(expectedMasses.get(i), refMasses.get(i).getMass(), 0.0);
                Assert.assertEquals(expectedIonAdducts.get(i), refMasses.get(i).getIonAdduct());
            }
            Assert.assertEquals(0.1, refError, 0.0);
            Assert.assertEquals(0.1, maxAssignentError, 0.0);
            Assert.assertEquals(0.2, maxDiffError, 0.0);
            Assert.assertEquals(3, maxEdgeInconsistencies);
            Assert.assertEquals(4, maxSameIterations);
        } catch (IOException e) {
            Assert.fail();
        }

    }
}