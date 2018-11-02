package mdGraphConstruction;

import mdGraphElements.MassDifference;

public class MassEdge {
    private MassWrapper source;
    private MassWrapper target;
    private MassDifference massDifference;

    public MassEdge(MassWrapper source, MassWrapper target, MassDifference massDifference) {
        this.source = source;
        this.target = target;
        this.massDifference = massDifference;
    }

    public MassWrapper getSource() {
        return source;
    }

    public MassWrapper getTarget() {
        return target;
    }

    public MassDifference getMassDifference() {
        return massDifference;
    }
}
