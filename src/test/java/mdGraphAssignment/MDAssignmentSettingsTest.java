package mdGraphAssignment;

import mdCoreElements.Element;
import mdCoreElements.IonAdduct;
import mdCoreElements.MDSettingsInterface;
import mdCoreElements.MockMDSettings;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import testPool.MDTestPool;
import utils.MDUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class MDAssignmentSettingsTest {
    private static final Path BASE_PATH = Paths.get("src/test/resources");
    private static final File TEST_GRAPHASSIGNMENT_OK = BASE_PATH.resolve("mdGraphAssignment/test_graphAssignment_ok.txt").toFile();

    private MDSettingsInterface mockMDSettings;
    private MDAssignmentSettingsInterface mdAssignmentSettings;

    @Before
    public void before() {
        Set<Element> mockElements = MDTestPool.getInstance().getElementPool("C", "H", "O");
        Map<String, Element> mockName2ElementMap = new HashMap<>();
        for (Element mockElement : mockElements) {
            mockName2ElementMap.put(mockElement.getName(), mockElement);
        }

        Set<IonAdduct> mockIonAdducts = MDTestPool.getInstance().getIonAdductPool("[M+H]+", "[M-H]-");
        Map<String, IonAdduct> mockName2IonAdductMap = new HashMap<>();
        for (IonAdduct mockIonAdduct : mockIonAdducts) {
            mockName2IonAdductMap.put(mockIonAdduct.getName(), mockIonAdduct);
        }

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
            List<RefMass> expectedRefMasses =
                    MDTestPool.getInstance().getRefMassPool("C6H12O6_[M+H]+", "C6H12O7_[M-H]-");

            mdAssignmentSettings.readSettingsFromFile(TEST_GRAPHASSIGNMENT_OK);
            List<RefMass> refMasses = mdAssignmentSettings.getRefMasses();
            double refError = mdAssignmentSettings.getRefError();
            double maxAssignentError = mdAssignmentSettings.getMaxAssignmentError();
            double maxDiffError = mdAssignmentSettings.getMaxDiffError();
            int maxEdgeInconsistencies = mdAssignmentSettings.getMaxEdgeInconsistencies();
            int maxSameIterations = mdAssignmentSettings.getMaxSameIterations();

            Assert.assertEquals(2, refMasses.size());
            Assert.assertTrue(refMasses.containsAll(expectedRefMasses));

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