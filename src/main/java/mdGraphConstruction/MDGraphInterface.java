package mdGraphConstruction;

import java.util.List;

public interface MDGraphInterface {
    void createEdges();
    List<MassWrapper> getMassWrappers();
    List<MassEdge> getMassEdges();
}
