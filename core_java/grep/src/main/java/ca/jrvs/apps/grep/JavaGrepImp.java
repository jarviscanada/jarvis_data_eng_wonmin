package ca.jrvs.apps.grep;

import java.io.*;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JavaGrepImp implements JavaGrep{
    final Logger logger = LoggerFactory.getLogger(JavaGrep.class);

    private String regex;
    private String rootPath;
    private String outFile;

    public static void main(String[] args) {
        if (args.length != 3) {
            throw new IllegalArgumentException("USAGE: JavaGrep regex rootPath outFile");
        }
        JavaGrepImp javaGrepImp = new JavaGrepImp();
        javaGrepImp.setRegex(args[0]);
        javaGrepImp.setRootPath(args[1]);
        javaGrepImp.setOutFile(args[2]);
        try {
            javaGrepImp.process();
        }
        catch(Exception ex) {
            logger.error("Invalid Usage!", ex);
        }
    }

    @Override
    public void process() throws IOException {
        try {
            List<String> matchedLines = new ArrayList<>();
            for (File file : listFile(rootPath)) {
                for (String line : readLines(file)) {
                    if (containsPattern(line)) {
                        matchedLines.add(line);
                    }
                }
            }
            writeToFile(matchedLines);
        }
        catch (IOException ex) {
            throw new IOException("Invalid I/O", ex);
        }
    }

    @Override
    public List<File> listFile(String rootDir) throws IOException{

        List<File> fileList = new ArrayList<>();
        File[] files = new File(rootDir).listFiles();

        for (File file : files) {
            if (file.isFile()) {
                fileList.add(file.getAbsoluteFile());
            }
            else if (file.isDirectory()) {
                fileList.addAll(listFile(file.getAbsolutePath()));
            }
        }
        return fileList;
    }

    @Override
    public List<String> readLines(File inputFile) throws IllegalArgumentException {
        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            while (reader.ready()) {
                lines.add(reader.readLine());
            }
        }
        catch (IllegalArgumentException | IOException ex) {
            throw new IllegalArgumentException("Invalid Argument!", ex);
        }
        return lines;
    }

    @Override
    public boolean containsPattern(String line) {
        return line.matches(regex);
    }

    @Override
    public void writeToFile(List<String> lines) throws IOException {
        File targetOut = new File(outFile);

        //A true argument is passed to the constructor to append if the output file already exists
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(targetOut,true))) {
            for (String s : lines) {
                writer.write(s);
                writer.newLine();
            }
        }
        catch (IOException ex) {
            throw new IOException("Invalid I/O!");
        }
    }

    @Override
    public String getRootPath() {
        return rootPath;
    }

    @Override
    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    @Override
    public String getRegex() {
        return regex;
    }

    @Override
    public void setRegex(String regex) {
        this.regex = regex;
    }

    @Override
    public String getOutFile() {
        return outFile;
    }

    @Override
    public void setOutFile(String outFile) {
        this.outFile = outFile;
    }
}


