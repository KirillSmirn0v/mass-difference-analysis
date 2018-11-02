package mdGraphConstruction;

import mdCoreData.ExpMass;
import mdCoreElements.IonAdduct;
import mdGraphElements.MDGraphSettings;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MDPreprocessor {
    private List<ExpMass> expMasses;
    private MDGraphSettings mdGraphSettings;
    private List<MassWrapper> massWrappers;

    public MDPreprocessor(List<ExpMass> expMasses, MDGraphSettings mdGraphSettings) {
        this.expMasses = expMasses;
        this.mdGraphSettings = new MDGraphSettings(mdGraphSettings);
    }

    public void runPreprocessing() {
        massWrappers = new ArrayList<>();

        Set<IonAdduct> ionAdducts = mdGraphSettings.getIonAdducts();
        for (IonAdduct ionAdduct : ionAdducts) {
            for (ExpMass expMass : expMasses) {
                MassWrapper massWrapper = new MassWrapper(expMass, ionAdduct);
                massWrappers.add(massWrapper);
            }
        }

        massWrappers.sort((massWrapper1, massWrapper2) -> {
           double diff = massWrapper2.getMass() - massWrapper1.getMass();
           if (diff > 0) { return -1; }
           return 1;
        });
    }

    public List<MassWrapper> getMassWrappers() {
        return massWrappers;
    }
}
