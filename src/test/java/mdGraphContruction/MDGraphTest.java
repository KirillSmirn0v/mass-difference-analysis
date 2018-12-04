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
import testPool.MDTestPool;

import java.util.*;

public class MDGraphTest {
    private MDPreprocessorInterface mockMDPreprocessor = null;
    private MDGraph mdGraph = null;

    @Before
    public void before() {
        List<MassWrapper> massWrappers = MDTestPool.getInstance().getMassWrapperPool(
                "C6H12O6_[M+H]+_[M+H]+", "C7H14O6_[M+H]+_[M+H]+",
                "C7H14O7_[M+H]+_[M+H]+", "C7H14O6_[M+Na]+_[M+Na]+",
                "C7H14O7_[M+Na]+_[M+Na]+", "C7H14O8_[M+Na]+_[M+Na]+"

        );
        Set<MassDifference> massDifferences = MDTestPool.getInstance().getMassDifferencePool(
                "CH2", "O"
        );

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
        expectedIdMap.put(4, expectedIdList);
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
