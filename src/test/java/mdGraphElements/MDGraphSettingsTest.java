package mdGraphElements;

import mdCoreElements.Element;
import mdCoreElements.MDSettings;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

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

    private Element carbon = new Element("C", 12.000000, 4);
    private Element hydrogen = new Element("H", 1.0078250, 1);
    private Element oxygen = new Element("O", 15.994915, 2);
    private Element nitrogen = new Element("N", 14.003074, 3);

    private MDGraphSettings mdGraphSettings = null;
    private MDSettings mockMDSettings;

    @Rule
    public ExpectedException expectedException = null;

    @Before
    public void before() {
        expectedException = ExpectedException.none();
        mockMDSettings = Mockito.mock(MDSettings.class);
        Set<Element> mockElements = new HashSet<>();
        mockElements.add(carbon);
        mockElements.add(hydrogen);
        mockElements.add(oxygen);
        Mockito.when(mockMDSettings.getElements()).thenReturn(mockElements);
        mdGraphSettings = new MDGraphSettings(mockMDSettings);
    }

    @After
    public void after() {
        expectedException = null;
        mockMDSettings = null;
        mdGraphSettings = null;
    }

    @Test
    public void test_objectInitialization_defaultMDSettings_correctDefaultsAreSet() {
        Set<MassDifference> expectedMassDifferences = new HashSet<>();
        Map<Element, Integer> formula;
        formula = new HashMap<>();
        formula.put(carbon, 1);
        expectedMassDifferences.add(new MassDifference(1, "C", formula));
        formula = new HashMap<>();
        formula.put(hydrogen, 2);
        expectedMassDifferences.add(new MassDifference(2, "H", formula));
        formula = new HashMap<>();
        formula.put(oxygen, 1);
        expectedMassDifferences.add(new MassDifference(3, "O", formula));

        Set<MassDifference> massDifferences = mdGraphSettings.getMassDifferences();

        Assert.assertEquals(expectedMassDifferences, massDifferences);
    }

    @Test
    public void test_readMassDifferenceFromFile_defaultMDSettings_correctMassDifferencesAreRead() {
        try {
            Set<MassDifference> expectedMassDifferences = new HashSet<>();
            Map<Element, Integer> formula;
            formula = new HashMap<>();
            formula.put(carbon, 1);
            formula.put(hydrogen, 2);
            expectedMassDifferences.add(new MassDifference(1, "CH2", formula));
            formula = new HashMap<>();
            formula.put(carbon, 2);
            formula.put(hydrogen, 6);
            formula.put(oxygen, 1);
            expectedMassDifferences.add(new MassDifference(2, "C2H5OH", formula));

            mdGraphSettings.readSettingsFromFile(TEST_MASSDIFFERENCES_OK);
            Set<MassDifference> massDifferences = mdGraphSettings.getMassDifferences();

            Assert.assertEquals(expectedMassDifferences, massDifferences);
        } catch (IOException e) {
            Assert.fail();
        }
    }
}