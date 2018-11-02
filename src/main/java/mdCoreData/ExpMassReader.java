package mdCoreData;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExpMassReader {
    private static Pattern patternExpMasses = Pattern.compile("^(\\d+\\.?\\d*)\\s+(\\d+)");
    private Set<Integer> idSet;
    private List<ExpMass> expMasses;

    public ExpMassReader() {
        this.idSet = new HashSet<>();
        this.expMasses = new ArrayList<>();
    }

    public void readExpMassesFromFile(File file) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        Scanner scanner = new Scanner(fileInputStream).useDelimiter("\\Z");

        readExpMasses(scanner);
        fileInputStream.close();
    }

    private void readExpMasses(Scanner scanner) throws IOException {
        this.expMasses = new ArrayList<>();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Matcher matcher = patternExpMasses.matcher(line);
            if (matcher.find()) {
                double mass = Double.parseDouble(matcher.group(1));
                int id = Integer.parseInt(matcher.group(2));
                if (idSet.contains(id)) {
                    throw new IOException("there are duplicate ids in the data");
                }
                ExpMass expMass = new ExpMass(id, mass);
                expMasses.add(expMass);
            }
        }

        expMasses.sort((o1, o2) -> {
            double diff = o2.getMass() - o1.getMass();
            if (diff > 0) return 1;
            return 0;
        });

        scanner.close();
    }

    public List<ExpMass> getExpMasses() {
        return expMasses;
    }
}
