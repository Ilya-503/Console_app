import java.io.*;
import java.util.*;

public class FileManager {

    public void separate(String inputFile) {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            checkInputFileFormat(inputFile);
            int fileCounter = Integer.parseInt(reader.readLine());
            String[] files = new String[fileCounter];
            Integer[] lines = new Integer[fileCounter];
            for (int index = 0; index < fileCounter; index++) {
                String[] info = reader.readLine().split(" ");
                files[index] = info[0];
                lines[index] = Integer.parseInt(info[1]);
            }
            for (int index = 0; index < fileCounter; index++) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(files[index]))) {
                    int lineCounter = 0;
                    while (lineCounter < lines[index]) {
                        writer.write(reader.readLine());
                        writer.newLine();
                        lineCounter++;
                    }
                }
            }
            System.out.printf("Separate success!\nInput file: %s\nOutput files: %s",
                    inputFile, Arrays.stream(files).toList());
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    private void checkInputFileFormat(String inputFile) throws Exception {
        String message = "Illegal file format";
        int needLines = 0, factLines = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            int filesCounter = Integer.parseInt(reader.readLine());
            if (filesCounter < 0) {
                throw new Exception(message);
            }
            while (filesCounter != 0) {
                String[] info = reader.readLine().split(" ");
                if (info.length != 2) {
                    throw new Exception(message);
                }
                int lines = Integer.parseInt(info[1]);
                if (lines < 0) {
                    throw new Exception(message);
                }
                needLines += lines;
                filesCounter--;
        }
            while (reader.readLine() != null) {
                factLines++;
            }
            if (factLines != needLines) {
                throw new Exception(message);
            }
        }
    }

    public void merge(List<String> files, String outputFile) {
        try {
            checkOutFileName(outputFile);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
                writer.write(String.valueOf(files.size()));
                writer.newLine();
                List<String> allLines = new ArrayList<>();
                for (String inputFile : files) {
                    int oldSize = allLines.size();
                    try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
                        allLines.addAll(reader.lines().toList());
                        StringBuilder builder = new StringBuilder();
                        builder.append(inputFile);
                        builder.append(" ");
                        builder.append(allLines.size() - oldSize);
                        writer.write(builder.toString());
                        writer.newLine();
                    }
                }
                for (String line: allLines) {
                    writer.write(line);
                    writer.newLine();
                }
                System.out.printf("Merge success!\nInput files: %s\nOutput file: %s", files, outputFile);
            }
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    private void checkOutFileName(String outputFile) throws IOException {
       String[] illegalChars = new String[]{"\\", "/", ":", "*", "?", "\"", "<", ">", "|"};
       String[] path = outputFile.split("\\\\");
       String fileName = path[path.length - 1];
       for (String ilChar : illegalChars) {
           if (fileName.contains(ilChar)) {
               throw new IOException("Illegal outputFile's name");
           }
       }
   }
}