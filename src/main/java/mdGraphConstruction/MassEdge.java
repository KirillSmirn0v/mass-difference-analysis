package mdGraphConstruction;

import mdGraphElements.MassDifference;

public class MassEdge {
    private Integer source;
    private Integer target;
    private MassDifference massDifference;

    public MassEdge(Integer source, Integer target, MassDifference massDifference) {
        this.source = source;
        this.target = target;
        this.massDifference = massDifference;
    }

    public Integer getSource() {
        return source;
    }

    public Integer getTarget() {
        return target;
    }

    public MassDifference getMassDifference() {
        return massDifference;
    }
}
