package mdGraphAssignment;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class MDAssignmentSettings implements MDAssignmentSettingsInterface {
    private static Pattern patternRefMasses = Pattern.compile("reference:\\s+([A-Za-z0-9]+)\\s+(\\d+\\.?\\d*)");
    private static Pattern patternElements = Pattern.compile("[A-Z][a-z]?\\d*]");
    private static Pattern patternParameters = Pattern.compile("([a-z\\s]+):\\s+(\\d+\\.?\\d*)");

    private List<MassAssigned> refMasses;
    private double maxAssignmentError;
    private double maxDiffError;
    private int maxEdgeInconsistencies;
    private int maxSameIterations;

    public MDAssignmentSettings() {
        setDefaults();
    }

    private void setDefaults() {
        refMasses = new ArrayList<>();
        maxAssignmentError = 5.0;
        maxDiffError = 0.3;
        maxEdgeInconsistencies = 5;
        maxSameIterations = 10;
    }

    @Override
    public void readSettingsFromFile(File file) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        Scanner scanner = new Scanner(fileInputStream);

        readSettings(scanner);
        fileInputStream.close();
    }

    private void readSettings(Scanner scanner) {

    }
}
