package mdCoreElements;

import org.junit.*;
import org.junit.rules.ExpectedException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class MDSettingsTest {
    private static final Path BASE_PATH = Paths.get("src/test/resources");
    private static final File TEST_CORESETTINGS_OK = BASE_PATH.resolve("mdCoreElements/test_coreSettings_ok.txt").toFile();

    private MDSettings mdSettings = null;

    @Rule
    public ExpectedException expectedException = null;

    @Before
    public void before() {
        expectedException = ExpectedException.none();
        mdSettings = new MDSettings();
    }

    @After
    public void after() {
        expectedException = null;
        mdSettings = null;
    }

    @Test
    public void test_fileHasCorrectDataFormat_correctDataIsRead() {
        List<Element> expectedElements = new ArrayList<>();
        expectedElements.add(new Element("C", 12.00, 4));
        expectedElements.add(new Element("N", 14.25, 4));
        expectedElements.add(new Element("Me", 15.00, 2));

        List<IonAdduct> expectedIonAdducts = new ArrayList<>();
        expectedIonAdducts.add(new IonAdduct("[M-H]-", IonAdduct.IonSign.NEGATIVE, -1.001));
        expectedIonAdducts.add(new IonAdduct("[M+K]+", IonAdduct.IonSign.POSITIVE, 5.00));

        try {
            mdSettings.readSettingsFromFile(TEST_CORESETTINGS_OK);
            List<Element> elements = new ArrayList<>(mdSettings.getElements());
            elements.sort(new Comparator<Element>() {
                @Override
                public int compare(Element o1, Element o2) {
                    double diff = o1.getMass() - o2.getMass();
                    if (diff > 0) { return 1; }
                    else if (diff < 0) { return -1; }
                    return 0;
                }
            });
            for (int i = 0; i < 3; i++) {
                Assert.assertEquals(expectedElements.get(i).getName(), elements.get(i).getName());
                Assert.assertEquals(expectedElements.get(i).getMass(), elements.get(i).getMass(), 0.0);
                Assert.assertEquals(expectedElements.get(i).getValency(), elements.get(i).getValency());
            }

            List<IonAdduct> ionAdducts = new ArrayList<>(mdSettings.getIonAdducts());
             ionAdducts.sort(new Comparator<IonAdduct>() {
                @Override
                public int compare(IonAdduct o1, IonAdduct o2) {
                    double diff = o1.getMass() - o2.getMass();
                    if (diff > 0) { return 1; }
                    else if (diff < 0) { return -1; }
                    return 0;
                }
            });
            for (int i = 0; i < 2; i++) {
                Assert.assertEquals(expectedIonAdducts.get(i).getName(), ionAdducts.get(i).getName());
                Assert.assertEquals(expectedIonAdducts.get(i).getMass(), ionAdducts.get(i).getMass(), 0.0);
                Assert.assertEquals(expectedIonAdducts.get(i).getIonSign(), ionAdducts.get(i).getIonSign());
            }
        } catch (IOException e) {
            Assert.fail("Reading the data cannot fail");
        }
    }
}
