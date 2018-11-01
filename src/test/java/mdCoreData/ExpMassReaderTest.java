package mdCoreData;

import org.junit.*;
import org.junit.rules.ExpectedException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ExpMassReaderTest {
    private static final Path BASE_PATH = Paths.get("src/test/resources");
    private static final File TEST_DATAFILE_OK = BASE_PATH.resolve("test_dataFile_ok.txt").toFile();
    private static final File TEST_DATAFILE_DUPLICATEIDS = BASE_PATH.resolve("test_dataFile_duplicateIds.txt").toFile();

    private ExpMassReader expMassReader = null;

    @Rule
    public ExpectedException expectedException = null;

    @Before
    public void before() {
        expMassReader = new ExpMassReader();
        expectedException = ExpectedException.none();
    }

    @After
    public void after() {
        expMassReader = null;
        expectedException = null;
    }

    @Test
    public void test_fileHasCorrectDataFormat_correctExpMassesAreRead() {
        List<ExpMass> expectedExpMasses = new ArrayList<>();
        expectedExpMasses.add(new ExpMass(1, 100.0000));
        expectedExpMasses.add(new ExpMass(2, 153.5565));
        expectedExpMasses.add(new ExpMass(3, 200.2212));
        expectedExpMasses.add(new ExpMass(4, 256.1234));
        expectedExpMasses.add(new ExpMass(5, 301.0000));

        try {
            expMassReader.readExpMassesFromFile(TEST_DATAFILE_OK);
            List<ExpMass> expMasses = expMassReader.getExpMasses();
            Assert.assertEquals(5, expMasses.size());
            for (int i = 0; i < expMasses.size(); i++) {
                ExpMass expectedExpMass = expectedExpMasses.get(i);
                ExpMass expMass = expMasses.get(i);
                Assert.assertEquals(expectedExpMass.getId(), expMass.getId());
                Assert.assertEquals(expectedExpMass.getMass(), expMass.getMass(), 0.0);
            }
        } catch (IOException e) {
            Assert.fail();
        }
    }

    @Test
    public void test_fileHasDuplicateIds_fileReadingThrowsException() throws IOException {
        expectedException.expect(IOException.class);
        expectedException.expectMessage("there are duplicate ids in the data");

        expMassReader.readExpMassesFromFile(TEST_DATAFILE_DUPLICATEIDS);
    }
}
