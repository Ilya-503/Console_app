import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

    String startTag = "<m_tar>", endTag = "</m_tar>", sepTag = "<s_tar>";

    public void separate(String inputFile) {
        int fileCounter = 0;
        List<String> inputStrings = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String string = reader.readLine();
            while (string != null) {
                if (string.trim().equals(sepTag)) {
                    fileCounter++;
                    writeToFile(inputFile, inputStrings, fileCounter);
                    inputStrings.clear();
                }
                else {
                    inputStrings.add(string);
                }
                string = reader.readLine();
            }
            System.out.printf("Separate success!\nInput file: %s\nSeparated into %d files", inputFile, fileCounter);
        } catch (IOException ex) {
            System.err.printf("File %s was not found", inputFile);
        }
    }

    private void writeToFile(String inputFile, List<String> inputStrings, int fileCounter) throws IOException {
        try (BufferedWriter writer = new BufferedWriter
                (new FileWriter(inputFile.replace(".txt", fileCounter + ".txt")))) {
            writer.write(startTag);
            writer.newLine();
            inputStrings.forEach((it) -> {
                try {
                    writer.write(it);
                    writer.newLine();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
            writer.write(endTag);
        }
    }

    public void merge(List<String> files, String outputFile) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            for (String inputFile : files) {
                checkFile(inputFile);
                try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
                    boolean isStart = false;
                    String string = reader.readLine();
                    while (string != null) {
                        if (string.trim().equals(startTag)) {
                            isStart = true;
                            string = reader.readLine();
                            while (!string.trim().equals(endTag)) {
                                writer.write(string);
                                writer.newLine();
                                string = reader.readLine();
                            }
                        }
                        if (string.trim().equals(endTag)) {
                            if (isStart) {
                                writer.write(sepTag);
                                writer.newLine();
                                isStart = false;
                            }
                        }
                        string = reader.readLine();
                    }
                }
            }
            System.out.printf("Merge success!\nInput files: %s\nOutput file: %s", files, outputFile);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    private void checkFile(String inputFile) throws Exception {
            try(BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
                boolean isStart = false;
                String string = reader.readLine();
                while (string != null) {
                    if (string.trim().equals(startTag)) {
                        if (isStart) {
                            throw new Exception("Incorrect use of tags");
                        }
                        isStart = true;
                    }
                    if (string.trim().equals(endTag)) {
                        if (!isStart) {
                            throw new Exception("Incorrect use of tags");
                        }
                        isStart = false;
                    }
                    string = reader.readLine();
                }
                if (isStart) {
                    throw new Exception("Incorrect use of tags");
                }
            } catch (IOException ex) {
                throw new IOException("File " + inputFile +" doesn't exist");
            }
    }
}