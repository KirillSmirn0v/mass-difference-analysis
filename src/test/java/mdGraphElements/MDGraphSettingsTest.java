package mdGraphElements;

import mdCoreElements.Element;
import mdCoreElements.MDSettingsInterface;
import mdCoreElements.MockMDSettings;
import org.junit.*;
import org.junit.rules.ExpectedException;
import testPool.MDTestPool;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MDGraphSettingsTest {
    private static final Path BASE_PATH = Paths.get("src/test/resources");
    private static final File TEST_MASSDIFFERENCES_OK = BASE_PATH.resolve("mdGraphElements/test_massDifferences_ok.txt").toFile();

    private MDGraphSettings mdGraphSettings = null;
    private MDSettingsInterface mockMDSettings;

    @Before
    public void before() {
        Set<Element> mockElements =
                MDTestPool.getInstance().getElementPool("C", "H", "O", "N");
        Map<String, Element> mockName2ElementMap = new HashMap<>();
        for (Element element : mockElements) {
            mockName2ElementMap.put(element.getName(), element);
        }
        mockMDSettings = new MockMDSettings() {
            @Override
            public Set<Element> getElements() {
                return mockElements;
            }

            @Override
            public Map<String, Element> getName2ElementMap() {
                return mockName2ElementMap;
            }
        };
        mdGraphSettings = new MDGraphSettings(mockMDSettings);
    }

    @After
    public void after() {
        mockMDSettings = null;
        mdGraphSettings = null;
    }

    @Test
    public void test_objectInitialization_defaultMDSettings_correctDefaultsAreSet() {
        Set<MassDifference> expectedMassDifferences =
                MDTestPool.getInstance().getMassDifferencePool("C", "H2", "O", "N2");

        Set<MassDifference> massDifferences = mdGraphSettings.getMassDifferences();

        Assert.assertEquals(expectedMassDifferences.size(), massDifferences.size());
        Assert.assertTrue(massDifferences.containsAll(expectedMassDifferences));
    }

    @Test
    public void test_readMassDifferenceFromFile_defaultMDSettings_correctMassDifferencesAreRead() {
        mdGraphSettings.getMassDifferences().clear();
        Assert.assertTrue(mdGraphSettings.getMassDifferences().isEmpty());

        Set<MassDifference> expectedMassDifferences =
                MDTestPool.getInstance().getMassDifferencePool("CH2", "C2H5OH", "NH3");
        try {
            mdGraphSettings.readSettingsFromFile(TEST_MASSDIFFERENCES_OK);
            Set<MassDifference> massDifferences = mdGraphSettings.getMassDifferences();

            Assert.assertEquals(expectedMassDifferences.size(), massDifferences.size());
            Assert.assertTrue(massDifferences.containsAll(expectedMassDifferences));
        } catch (IOException e) {
            Assert.fail();
        }
    }
}
