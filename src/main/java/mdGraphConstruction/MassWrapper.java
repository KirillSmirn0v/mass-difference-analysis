package mdGraphConstruction;

import mdCoreData.ExpMass;
import mdCoreElements.IonAdduct;

public class MassWrapper {
    private ExpMass expMass;
    private IonAdduct ionAdduct;
    private double mass;

    public MassWrapper(ExpMass expMass, IonAdduct ionAdduct) {
        this.expMass = expMass;
        this.ionAdduct = ionAdduct;
        this.mass = expMass.getMass() - ionAdduct.getMass();
    }

    public MassWrapper(MassWrapper massWrapper) {
        this.expMass = new ExpMass(massWrapper.getExpMass());
        this.ionAdduct = new IonAdduct(massWrapper.getIonAdduct());
        this.mass = massWrapper.getMass();
    }

    public ExpMass getExpMass() {
        return expMass;
    }

    public IonAdduct getIonAdduct() {
        return ionAdduct;
    }

    public double getMass() {
        return mass;
    }
}
