import java.io.File;
import java.io.IOException;

public interface SettingsInterface {
    void setDefaults();
    void readSettingsFromFile(File file);
}
