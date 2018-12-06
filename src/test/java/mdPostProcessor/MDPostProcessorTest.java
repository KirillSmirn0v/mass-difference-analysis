package mdPostProcessor;

import mdGraphAssignment.MDAssignerInterface;
import mdGraphAssignment.MassAssigned;
import mdGraphAssignment.MockMDAssigner;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import testPool.MDTestPool;
import utils.MDUtils;

import java.util.ArrayList;
import java.util.List;

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
}
