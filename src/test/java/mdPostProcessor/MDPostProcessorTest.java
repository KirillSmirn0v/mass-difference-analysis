package mdPostProcessor;

import javafx.util.Pair;
import mdCoreElements.Element;
import mdGraphAssignment.MDAssignerInterface;
import mdGraphAssignment.MassAssigned;
import mdGraphAssignment.MockMDAssigner;
import mdGraphConstruction.MassEdge;
import mdGraphConstruction.MassWrapper;
import mdGraphElements.MassDifference;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import testPool.MDTestPool;
import utils.MDUtils;

import java.util.*;

public class MDPostProcessorTest {
    private MDAssignerInterface mockMDAssigner;
    private MDPostProcessorInterface mdPostProcessor;

    @Before
    public void before() {
        List<MassAssigned> mockMassAssignedList = MDTestPool.getInstance().getMassAssignedPool(
                "C6H12O6_[M+H]+_[M+H]+", "C6H12O6_[M+Cl]-_[M+Cl]-",
                "C7H14O7_[M+Cl]-_[M+Cl]-",
                "C7H14O6_[M-H]-_[M-H]-", "C7H14O6_[M+Na]+_[M+Na]+",
                "C6H12O7_[M+H]+_[M+H]+"
        );

        mockMDAssigner = new MockMDAssigner() {
            @Override
            public List<MassAssigned> getMassAssignedList() {
                return mockMassAssignedList;
            }
        };
        mdPostProcessor = new MDPostProcessor(mockMDAssigner);
    }

    @After
    public void after() {
        mockMDAssigner = null;
        mdPostProcessor = null;
    }

    @Test
    public void test_squezeMassAssignerList_massProcessedList_returnsCorrectAmount() {
        mdPostProcessor.squezeMassAssignedList();

        List<MassProcessed> massProcessedList = mdPostProcessor.getMassProcessedList();
        Assert.assertEquals(4, massProcessedList.size());

        List<MassProcessed> expectedMassProcessedList = new ArrayList<>();
        expectedMassProcessedList.add(
            new MassProcessed(
                1,
                MDTestPool.getInstance().getMassWrapperPool("C6H12O6_[M+H]+_[M+H]+", "C6H12O6_[M+Cl]-_[M+Cl]-"),
                MDTestPool.getInstance().string2Formula("C6H12O6"),
                MDUtils.getMassFromFormula(MDTestPool.getInstance().string2Formula("C6H12O6"))
            )
        );
        expectedMassProcessedList.add(
                new MassProcessed(
                        2,
                        MDTestPool.getInstance().getMassWrapperPool("C7H14O6_[M-H]-_[M-H]-", "C7H14O6_[M+Na]+_[M+Na]+"),
                        MDTestPool.getInstance().string2Formula("C7H14O6"),
                        MDUtils.getMassFromFormula(MDTestPool.getInstance().string2Formula("C7H14O6"))
                )
        );
        expectedMassProcessedList.add(
                new MassProcessed(
                        3,
                        MDTestPool.getInstance().getMassWrapperPool("C6H12O7_[M+H]+_[M+H]+"),
                        MDTestPool.getInstance().string2Formula("C6H12O7"),
                        MDUtils.getMassFromFormula(MDTestPool.getInstance().string2Formula("C6H12O7"))
                )
        );
        expectedMassProcessedList.add(
                new MassProcessed(
                        4,
                        MDTestPool.getInstance().getMassWrapperPool("C7H14O7_[M+Cl]-_[M+Cl]-"),
                        MDTestPool.getInstance().string2Formula("C7H14O7"),
                        MDUtils.getMassFromFormula(MDTestPool.getInstance().string2Formula("C7H14O7"))
                )
        );

        Assert.assertEquals(expectedMassProcessedList, massProcessedList);
    }

    @Test
    public void test_rebuildNetwork_massEdges_returnsCorrectAmount() {
        List<MassProcessed> massProcessedList = mdPostProcessor.getMassProcessedList();
        massProcessedList.clear();
        Assert.assertTrue(mdPostProcessor.getMassProcessedList().isEmpty());

        List<MassWrapper> emptyArray = new ArrayList<>();
        Map<Element, Integer> formula;

        formula = MDTestPool.getInstance().string2Formula("C6H12O6");
        massProcessedList.add(new MassProcessed(1, emptyArray, formula, MDUtils.getMassFromFormula(formula)));
        formula = MDTestPool.getInstance().string2Formula("C7H14O6");
        massProcessedList.add(new MassProcessed(2, emptyArray, formula, MDUtils.getMassFromFormula(formula)));
        formula = MDTestPool.getInstance().string2Formula("C6H12O7");
        massProcessedList.add(new MassProcessed(3, emptyArray, formula, MDUtils.getMassFromFormula(formula)));
        formula = MDTestPool.getInstance().string2Formula("C7H14O7");
        massProcessedList.add(new MassProcessed(4, emptyArray, formula, MDUtils.getMassFromFormula(formula)));
        formula = MDTestPool.getInstance().string2Formula("C8H16O7");
        massProcessedList.add(new MassProcessed(5, emptyArray, formula, MDUtils.getMassFromFormula(formula)));

        Set<MassDifference> massDifferences = MDTestPool.getInstance().getMassDifferencePool("CH2", "O");

        mdPostProcessor.rebuildNetwork(massDifferences);
        List<MassEdge> massEdges = mdPostProcessor.getMassEdges();

        Map<String, List<Pair<Integer, Integer>>> expectedMassDifferenceMap = new HashMap<>();
        List<Pair<Integer, Integer>> expectedIndxList;
        expectedIndxList = new ArrayList<>();
        expectedIndxList.add(new Pair<>(0, 1));
        expectedIndxList.add(new Pair<>(2, 3));
        expectedIndxList.add(new Pair<>(3, 4));
        expectedMassDifferenceMap.put("CH2", expectedIndxList);
        expectedIndxList = new ArrayList<>();
        expectedIndxList.add(new Pair<>(0, 2));
        expectedIndxList.add(new Pair<>(1, 3));
        expectedMassDifferenceMap.put("O", expectedIndxList);

        Assert.assertEquals(5, massEdges.size());
        for (MassEdge massEdge : massEdges) {
            if (expectedMassDifferenceMap.containsKey(massEdge.getMassDifference().getName())) {
                List<Pair<Integer, Integer>> expectedPairs = expectedMassDifferenceMap.get(massEdge.getMassDifference().getName());
                int indxSource = massEdge.getSource();
                int indxTarget = massEdge.getTarget();
                Pair<Integer, Integer> pair = new Pair<>(indxSource, indxTarget);
                Assert.assertTrue(expectedPairs.contains(pair));
            } else {
                Assert.fail();
            }
        }
    }
}
