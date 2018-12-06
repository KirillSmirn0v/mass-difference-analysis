package mdResults;

import mdCoreData.ExpMass;
import mdCoreElements.Element;
import mdCoreElements.IonAdduct;
import mdGraphConstruction.MassEdge;
import mdGraphConstruction.MassWrapper;
import mdPostProcessor.MassProcessed;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class MDResultWriter {

    private Set<Element> elements;
    private Set<IonAdduct> ionAdducts;
    private List<MassProcessed> massProcessedList;
    private List<MassEdge> massEdges;

    public MDResultWriter(List<MassProcessed> massProcessedList, List<MassEdge> massEdges, Set<Element> elements, Set<IonAdduct> ionAdducts) {
        this.massProcessedList = massProcessedList;
        this.massEdges = massEdges;
        this.elements = elements;
        this.ionAdducts = ionAdducts;
    }

    public void writeExpMasses(File file) {

    }

    public void writeMassAssignedList(File file) throws IOException {
        FileWriter fileWriter = new FileWriter(file);
        PrintWriter printWriter = new PrintWriter(fileWriter);

        printWriter.print("assignment ID\t");
        printWriter.print("assignment value\t");
        for (IonAdduct ionAdduct : ionAdducts) {
            printWriter.print(ionAdduct.getName() + " ID\t");
            printWriter.print(ionAdduct.getName() + " value\t");
        }
        for (Element element : elements) {
            printWriter.print(element.getName() + "\t");
        }
        printWriter.print("\n");

        for (MassProcessed massProcessed : massProcessedList) {
            printWriter.print(massProcessed.getId() + "\t");
            printWriter.print(massProcessed.getMass() + "\t");
            List<MassWrapper> massWrappers = massProcessed.getMassWrappers();
            for (IonAdduct ionAdduct : ionAdducts) {
                Optional<MassWrapper> massWrapper =
                        massWrappers.stream().filter(x -> x.getIonAdduct().equals(ionAdduct)).findFirst();
                if (massWrapper.isPresent()) {
                    ExpMass expMass = massWrapper.get().getExpMass();
                    printWriter.print(expMass.getId() + "\t");
                    printWriter.print(expMass.getMass() + "\t");
                } else {
                    printWriter.print("-\t-\t");
                }
            }
            Map<Element, Integer> formula = massProcessed.getFormula();
            for (Element element : elements) {
                if (formula.keySet().contains(element)) {
                    printWriter.print(formula.get(element) + "\t");
                } else {
                    printWriter.print("0\t");
                }
            }
            printWriter.print("\n");
        }

        printWriter.close();
        fileWriter.close();
    }

    public void writeGraph(File file) {

    }
}
