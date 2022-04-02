import org.junit.Test;
import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;

public class FileManagerTest {

    FileManager manager = new FileManager();
    String path = "src\\test\\resources\\";

    @Test
    public void merge() {
        List<String> files = new ArrayList<>();
        files.add(path + "merg_1.txt");
        files.add(path + "merg_2.txt");
        files.add(path + "merg_3.txt");
        String outputFile = path + "out.txt";
        manager.merge(files, outputFile);
        try {
            compareFilesContent(path + "merg_t.txt", path + "out.txt");
            Files.deleteIfExists(Paths.get(outputFile));
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Test
    public void separate() {
        manager.separate(path + "sep.txt");
        String fileV = path + "Valhalla.txt";
        String fileM = path + "messageNumber1.txt";
        try {
            compareFilesContent(path + "sep_t_V.txt", fileV);
            compareFilesContent(path + "sep_t_M.txt", fileM);
            Files.deleteIfExists(Paths.get(fileV));
            Files.deleteIfExists(Paths.get(fileM));
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    private void compareFilesContent(String needFile, String inputFile) throws Exception {
        try (BufferedReader ansReader = new BufferedReader(new FileReader(needFile));
             BufferedReader inpReader = new BufferedReader(new FileReader(inputFile))) {
            String ansStr = ansReader.readLine();
            String inpStr = inpReader.readLine();
            while (inpStr != null) {
                assertEquals(ansStr, inpStr);
                ansStr = ansReader.readLine();
                inpStr = inpReader.readLine();
            }
        }
    }
}
