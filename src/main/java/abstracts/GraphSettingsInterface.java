package abstracts;

import java.io.File;
import java.io.IOException;

public interface GraphSettingsInterface {
    void setDefaults();
    void readSettingsFromFile(File file) throws IOException;
}
