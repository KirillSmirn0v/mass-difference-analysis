package mdGraphContruction;

import javafx.util.Pair;
import mdCoreData.ExpMass;
import mdCoreElements.Element;
import mdCoreElements.IonAdduct;
import mdGraphConstruction.*;
import mdGraphElements.MassDifference;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class MDGraphTest {
    private IonAdduct hydrogenation = new IonAdduct("[M+H]+", IonAdduct.IonSign.POSITIVE, 1.007276);
    private IonAdduct sodiation = new IonAdduct("[M+Na]+", IonAdduct.IonSign.POSITIVE, 22.989221);
    private Element carbon = new Element("C", 12.000000, 4);
    private Element hydrogen = new Element("H", 1.0078250, 1);
    private Element oxygen = new Element("O", 15.994915, 2);

    private MDPreprocessorInterface mockMDPreprocessor = null;
    private MDGraph mdGraph = null;

    @Before
    public void before() {
        Set<MassDifference> massDifferences = new HashSet<>();
        Map<Element, Integer> formula;
        formula = new HashMap<>();
        formula.put(carbon, 1);
        formula.put(hydrogen, 2);
        massDifferences.add(new MassDifference(1, "CH2", formula));
        formula = new HashMap<>();
        formula.put(oxygen, 1);
        massDifferences.add(new MassDifference(2, "O", formula));

        List<MassWrapper> massWrappers = new ArrayList<>();
        massWrappers.add(new MassWrapper(new ExpMass(1, 181.070665), hydrogenation));
        massWrappers.add(new MassWrapper(new ExpMass(2, 195.086315), hydrogenation));
        massWrappers.add(new MassWrapper(new ExpMass(3, 211.081229), hydrogenation));
        massWrappers.add(new MassWrapper(new ExpMass(4, 217.068259), sodiation));
        massWrappers.add(new MassWrapper(new ExpMass(5, 233.063173), sodiation));
        massWrappers.add(new MassWrapper(new ExpMass(6, 249.058088), sodiation));

        mockMDPreprocessor = new MockMDPreprocessor() {
            @Override
            public Set<MassDifference> getMassDifferences() {
                return massDifferences;
            }

            @Override
            public List<MassWrapper> getMassWrappers() {
                return massWrappers;
            }

            @Override
            public double getEdgeCreationError() {
                return 0.1;
            }
        };
        mdGraph = new MDGraph(mockMDPreprocessor);
    }

    @After
    public void after() {
        mockMDPreprocessor = null;
        mdGraph = null;
    }

    @Test
    public void test_createEdges_correctEdgesAreCreated() {
        Map<Integer, List<Pair<Integer, Integer>>> expectedIdMap = new HashMap<>();
        List<Pair<Integer, Integer>> expectedIdList;
        expectedIdList = new ArrayList<>();
        expectedIdList.add(new Pair<>(0, 1));
        expectedIdList.add(new Pair<>(0, 3));
        expectedIdMap.put(1, expectedIdList);
        expectedIdList = new ArrayList<>();
        expectedIdList.add(new Pair<>(1, 2));
        expectedIdList.add(new Pair<>(1, 4));
        expectedIdList.add(new Pair<>(2, 5));
        expectedIdList.add(new Pair<>(3, 4));
        expectedIdList.add(new Pair<>(4, 5));
        expectedIdMap.put(2, expectedIdList);

        mdGraph.createEdges();
        List<MassEdge> massEdges = mdGraph.getMassEdges();
        Assert.assertEquals(7, massEdges.size());
        for (MassEdge massEdge : massEdges) {
            int idMassDifference = massEdge.getMassDifference().getId();
            if (expectedIdMap.containsKey(idMassDifference)) {
                List<Pair<Integer, Integer>> expectedPairs = expectedIdMap.get(idMassDifference);
                int idSource = massEdge.getSource();
                int idTarget = massEdge.getTarget();
                Pair<Integer, Integer> pair = new Pair<>(idSource, idTarget);
                Assert.assertTrue(expectedPairs.contains(pair));
            } else {
                Assert.fail();
            }
        }
    }
}
