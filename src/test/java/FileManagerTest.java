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
    String mergePath = "src\\test\\resources\\merge";
    String sepPath = "src\\test\\resources\\sep";

    @Test
    public void merge() {
        List<String> files = new ArrayList<>();
        files.add(mergePath + "\\input\\" + "merg 1.txt");
        files.add(mergePath + "\\input\\" + "merg 2.txt");
        files.add(mergePath + "\\input\\" + "merg_3.txt");
        String outputFile = mergePath + "\\output\\" + "out.txt";
        manager.merge(files, outputFile);
        try {
            compareFilesContent(mergePath + "\\output\\" + "merg_t.txt",
                    mergePath + "\\output\\" + "out.txt");
            Files.deleteIfExists(Paths.get(outputFile));
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Test
    public void mergeEmptyFiles() {
        List<String> files = new ArrayList<>();
        files.add(mergePath + "\\input\\" + "merg_Empty 1 .txt");
        files.add(mergePath + "\\input\\" + "merg_Empty 2 .txt");
        String outputFile = mergePath + "\\output\\" + "out_empty.txt";
        manager.merge(files, outputFile);
        try {
            compareFilesContent(mergePath + "\\output\\" + "merg_empty_t.txt", outputFile);
            Files.deleteIfExists(Paths.get(outputFile));
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Test
    public void mergeWithEmptyLines() {
        List<String> files = new ArrayList<>();
        files.add(mergePath + "\\input\\" + "merg_emptyLines0.txt");
        files.add(mergePath + "\\input\\" + "merg_emptyLines1.txt");
        String outputFile = mergePath + "\\output\\" + "out_emptyLines.txt";
        manager.merge(files, outputFile);
        try {
            compareFilesContent(mergePath + "\\output\\" + "merg_emptyLines_t.txt", outputFile);
            Files.deleteIfExists(Paths.get(outputFile));
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Test
    public void separate() {
        manager.separate(sepPath + "\\input\\" + "sep.txt");
        String fileV = sepPath + "\\output\\" + "Valhalla.txt";
        String fileM = sepPath + "\\output\\" + "messageNumber1.txt";
        try {
            compareFilesContent(sepPath + "\\output\\" +  "sep t V.txt", fileV);
            compareFilesContent(sepPath + "\\output\\" + "sep t M.txt", fileM);
            Files.deleteIfExists(Paths.get(fileV));
            Files.deleteIfExists(Paths.get(fileM));
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Test
    public void separateWithEmptyLines() {
        manager.separate(sepPath + "\\input\\" + "sep_EmptyLines.txt");
        String file1 = sepPath + "\\output\\" + "sep_out-1.txt";
        String file2 = sepPath + "\\output\\" + "sep_out-2.txt";
        try {
            compareFilesContent(sepPath + "\\output\\" + "sep_empty_t1.txt", file1);
            compareFilesContent(sepPath + "\\output\\" + "sep_empty_t2.txt", file2);
            Files.deleteIfExists(Paths.get(file1));
            Files.deleteIfExists(Paths.get(file2));
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
