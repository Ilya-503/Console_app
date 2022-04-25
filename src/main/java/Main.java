import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.util.List;


public class Main {

    @Argument()
    private List<String> files;

    @Option(name = "-out", usage = "merging list of files before option into OutputFile",
            forbids = {"-u"}, metaVar = "OutputFile")
    private String outputFile;

    @Option(name = "-u", usage = "separating InputFile", forbids = {"-out"}, metaVar = "InputFile")
    private String inputFile;

    public static void main(String[] args) {new Main().parseArgs(args);}

    private void parseArgs(String[] args) {
        CmdLineParser parser = new CmdLineParser(this);
        try {
            parser.parseArgument(args);
            checkIllegalArgs(args);
            FileManager manager = new FileManager();
            if (inputFile != null) {
                manager.separate(inputFile);
            }
            else {
                manager.merge(files, outputFile);
            }
        } catch (CmdLineException ex) {
            System.err.println(ex.getMessage());
            parser.printUsage(System.err);
            System.err.print ("EXAMPLE: tar -u filename.txt ");
            System.err.println("OR tar file1.txt file2.txt … -out output.txt.");
        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println(ex.getMessage());
        }
    }

    private void checkIllegalArgs(String[] args) throws CmdLineException {
        if (inputFile == null && outputFile == null) {
            throw new CmdLineException("no option is specified ");
        }
        if (files != null && inputFile != null) {
            throw new CmdLineException("can't use [-u] option and argument(s) for [-out] option");
        }
        if (outputFile != null && files == null) {
            throw new CmdLineException("no files to merge");
        }
    }
}