package mdCoreElements;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import testPool.MDTestPool;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

public class MDSettingsTest {
    private static final Path BASE_PATH = Paths.get("src/test/resources");
    private static final File TEST_CORESETTINGS_OK = BASE_PATH.resolve("mdCoreElements/test_coreSettings_ok.txt").toFile();
    private static final File TEST_CORESETTINGS_DUPLICATES = BASE_PATH.resolve("mdCoreElements/test_coreSettings_duplicates.txt").toFile();

    private MDSettings mdSettings = null;
    private Set<Element> expectedElements = null;
    private Set<IonAdduct> expectedIonAdducts = null;

    @Before
    public void before() {
        mdSettings = new MDSettings();
        expectedElements = MDTestPool.getInstance().getElementPool("C", "H", "O");
        expectedIonAdducts = MDTestPool.getInstance().getIonAdductPool("[M-H]-", "[M+Na]+");
    }

    @After
    public void after() {
        mdSettings = null;
        expectedElements = null;
        expectedIonAdducts = null;
    }

    @Test
    public void test_fileHasCorrectDataFormat_correctDataIsRead() {
        assertDataFromFile(TEST_CORESETTINGS_OK);
    }

    @Test
    public void test_fileHasDuplicates_duplicatesAreSkipped() {
        assertDataFromFile(TEST_CORESETTINGS_DUPLICATES);
    }

    private void assertDataFromFile(File file) {
        try {
            clearMDSettingsSets();
            mdSettings.readSettingsFromFile(file);
            assertSetEquality();
        } catch (IOException e) {
            Assert.fail("Reading the data cannot fail");
        }
    }

    private void clearMDSettingsSets() {
        mdSettings.getElements().clear();
        mdSettings.getIonAdducts().clear();
        Assert.assertTrue(mdSettings.getElements().isEmpty());
        Assert.assertTrue(mdSettings.getIonAdducts().isEmpty());
    }

    private void assertSetEquality() {
        Assert.assertEquals(expectedElements.size(), mdSettings.getElements().size());
        Assert.assertEquals(expectedIonAdducts.size(), mdSettings.getIonAdducts().size());
        Assert.assertTrue(mdSettings.getElements().containsAll(expectedElements));
        Assert.assertTrue(mdSettings.getIonAdducts().containsAll(expectedIonAdducts));
    }
}
