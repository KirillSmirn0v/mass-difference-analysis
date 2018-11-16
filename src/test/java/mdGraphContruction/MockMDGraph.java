package mdGraphContruction;

import mdGraphConstruction.MDGraphInterface;
import mdGraphConstruction.MassEdge;
import mdGraphConstruction.MassWrapper;

import java.util.ArrayList;
import java.util.List;

public class MockMDGraph implements MDGraphInterface {
    @Override
    public void createEdges() {

    }

    @Override
    public List<MassWrapper> getMassWrappers() {
        return new ArrayList<>();
    }

    @Override
    public List<MassEdge> getMassEdges() {
        return new ArrayList<>();
    }
}
