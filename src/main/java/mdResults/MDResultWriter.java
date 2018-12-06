package mdResults;

import mdCoreElements.Element;
import mdCoreElements.IonAdduct;
import mdGraphAssignment.MassAssigned;
import mdGraphConstruction.MassEdge;

import java.util.List;
import java.util.Set;

public class MDResultWriter {

    private Set<Element> elements;
    private Set<IonAdduct> ionAdducts;
    private List<MassAssigned> massAssignedList;
    private List<MassEdge> massEdges;

    public MDResultWriter(List<MassAssigned> massAssignedList, List<MassEdge> massEdges, Set<Element> elements, Set<IonAdduct> ionAdducts) {
        this.massAssignedList = massAssignedList;
        this.massEdges = massEdges;
        this.elements = elements;
        this.ionAdducts = ionAdducts;
    }

    public void writeExpMasses() {

    }

    public void writeMassAssignedList() {

    }

    public void writeGraph() {

    }
}
