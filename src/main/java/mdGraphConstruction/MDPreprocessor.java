package mdGraphConstruction;

import mdCoreData.ExpMass;
import mdCoreElements.IonAdduct;
import mdGraphElements.MDGraphSettings;
import mdGraphElements.MDGraphSettingsInterface;
import mdGraphElements.MassDifference;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MDPreprocessor implements MDPreprocessorInterface {
    private List<ExpMass> expMasses;
    private MDGraphSettingsInterface mdGraphSettings;
    private List<MassWrapper> massWrappers;

    public MDPreprocessor(List<ExpMass> expMasses, MDGraphSettingsInterface mdGraphSettings) {
        this.expMasses = expMasses;
        this.mdGraphSettings = mdGraphSettings.getCopy();
    }

    public MDPreprocessor(List<ExpMass> expMasses, MDGraphSettingsInterface mdGraphSettings, List<MassWrapper> massWrappers) {
        this.expMasses = expMasses;
        this.mdGraphSettings = mdGraphSettings;
        this.massWrappers = massWrappers;
    }

    @Override
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

    @Override
    public List<MassWrapper> getMassWrappers() {
        return massWrappers;
    }

    @Override
    public Set<MassDifference> getMassDifferences() {
        return mdGraphSettings.getMassDifferences();
    }

    @Override
    public double getEdgeCreationError() {
        return mdGraphSettings.getEdgeCreationError();
    }

    @Override
    public MDPreprocessorInterface getCopy() {
        List<ExpMass> expMassesCopy = new ArrayList<>();
        for (ExpMass expMass : expMasses) {
            expMassesCopy.add(new ExpMass(expMass));
        }
        List<MassWrapper> massWrappersCopy = new ArrayList<>();
        for (MassWrapper massWrapper : massWrappers) {
            massWrappersCopy.add(new MassWrapper(massWrapper));
        }
        return new MDPreprocessor(expMassesCopy, getMdGraphSettings(), massWrappersCopy);
    }

    private MDGraphSettingsInterface getMdGraphSettings() {
        return mdGraphSettings;
    }
}
