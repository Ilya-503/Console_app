import java.io.*;
import java.util.*;

public class FileManager {

    public void separate(String inputFile) {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            int filesCounter = Integer.parseInt(reader.readLine());
            if (filesCounter <= 0) {
                throw new Exception("Illegal number of output files");
            }
            for (int i = 0; i < filesCounter; i++) {
                String outputFile = reader.readLine();
                checkOutFileName(outputFile);
                System.out.println("---" + outputFile + "---");
                BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
                int byteCounter = Integer.parseInt(reader.readLine());
                if (byteCounter < 0) {
                    throw new Exception("Size of " + outputFile + " file is negative");
                }
                while (byteCounter != 0) {
                    int b = reader.read();
                    writer.write(b);
                    byteCounter--;
                }
                reader.readLine();
                writer.close();
            }
        } catch (NumberFormatException ex) {
            System.err.println("Illegal file format");
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void merge(List<String> files, String outputFile) {
        try {
            checkOutFileName(outputFile);
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
            writer.write(String.valueOf(files.size()));
            for (String inputFile : files) {
                writer.newLine();
                File file = new File(inputFile);
                writer.write(inputFile);
                writer.newLine();
                writer.write(String.valueOf(file.length()));
                writer.newLine();
                BufferedReader reader = new BufferedReader(new FileReader(file));
                int b = reader.read();
                while (b != -1) {
                    writer.write(b);
                    b = reader.read();
                }
            }
            writer.close();
            System.out.printf("Success mering!\nOutput file: %s", outputFile);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    private void checkOutFileName(String outputFile) throws IOException {
       String[] illegalChars = new String[]{"\\", "/", ":", "*", "?", "\"", "<", ">", "|"};
       String[] path = outputFile.split("\\\\");
       String fileName = path[path.length - 1];
       for (String ilChar : illegalChars) {
           if (fileName.contains(ilChar)) {
               throw new IOException("Illegal outputFile's name: " + outputFile);
           }
       }
   }
}