import java.io.*;
import java.util.*;

public class FileManager {

    public void separate(String inputFile) {
        String message = "Illegal file format";
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            List<String> allLines = reader.lines().toList();
            int filesCounter = Integer.parseInt(allLines.get(0));
            if (filesCounter <= 0) {
                throw new Exception(message);
            }
            String[] files = new String[filesCounter];
            int[] lines = new int[filesCounter];
            int totalLines = 0;
            for (int i = 0; i < filesCounter; i++) {
                String[] info = allLines.get(i + 1).split("\\|");
                if (info.length != 2) {
                    throw new Exception(message);
                }
                files[i] = info[0];
                int linesInFile = Integer.parseInt(info[1]);
                if (linesInFile < 0) {
                    throw new Exception(message);
                }
                lines[i] = linesInFile;
                totalLines += linesInFile;
            }
            if (totalLines != allLines.size() - 1 - filesCounter) {
                throw new Exception(message);
            }
            int index = filesCounter + 1;
            for (int i = 0; i < filesCounter; i++) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(files[i]))) {
                    while (lines[i] != 0) {
                        writer.write(allLines.get(index));
                        writer.newLine();
                        lines[i]--;
                        index++;
                    }
                }
            }
            System.out.printf("Separate success!\nInput file: %s\nOutput files: %s",
                    inputFile, Arrays.stream(files).toList());
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
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
                        writer.write(inputFile);
                        writer.write("|");
                        writer.write(String.valueOf(allLines.size() - oldSize));
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