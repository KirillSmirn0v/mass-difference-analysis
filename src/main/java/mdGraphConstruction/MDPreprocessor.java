package mdGraphConstruction;

import mdCoreData.ExpMass;
import mdGraphElements.MDGraphSettings;

import java.util.ArrayList;
import java.util.List;

public class MDPreprocessor {
    private List<ExpMass> expMasses;
    private MDGraphSettings mdGraphSettings;
    private List<MassWrapper> massWrappers;

    public MDPreprocessor(List<ExpMass> expMasses, MDGraphSettings mdGraphSettings) {
        this.expMasses = expMasses;
        this.mdGraphSettings = new MDGraphSettings(mdGraphSettings);
        this.massWrappers = new ArrayList<>();
    }

    public void runPreprocessing() {

    }
}
