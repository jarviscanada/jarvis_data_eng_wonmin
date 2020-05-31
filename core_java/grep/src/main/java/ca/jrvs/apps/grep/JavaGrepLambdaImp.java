package ca.jrvs.apps.grep;

import java.io.*;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.log4j.BasicConfigurator;
import java.nio.file.*;
import java.util.stream.*;

public class JavaGrepLambdaImp extends JavaGrepImp{

    final Logger logger = LoggerFactory.getLogger(JavaGrep.class);
    private String regex;
    private String rootPath;
    private String outFile;

    public static void main(String[] args){
        BasicConfigurator.configure();
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
            javaGrepImp.logger.error(ex.getMessage(),ex);
        }
    }

    @Override
    public void process() throws IOException {
        List<String> matchedLines = new ArrayList<>();
        try{
            listFile(rootPath)
                    .forEach(lines -> {
                        if (containsPattern(lines.toString())) {
                            matchedLines.add(lines.toString());
                        }
                    } );
            writeToFile(matchedLines);
        }
        catch (IOException ex) {
            throw new IOException("Invalid I/O!",ex);
        }
    }

    @Override
    public List<File> listFile(String rootDir) throws IOException {
        List<File> fileList;

        try(Stream<Path> walk = Files.walk(Paths.get(rootDir))) {
            fileList = walk.filter(Files::isRegularFile)
                    .map(x -> x.toFile())
                    .collect(Collectors.toList());
        }
        catch (IOException ex) {
            throw new IOException("Invalid I/O!", ex);
        }
        return fileList;
    }

    @Override
    public List<String> readLines(File inputFile) throws IllegalArgumentException {
        List<String> lines;

        try(Stream<String> stream = Files.lines(Paths.get(inputFile.getAbsolutePath()))) {
            lines = stream.collect(Collectors.toList());
        }
        catch (IllegalArgumentException | IOException ex) {
            throw new IllegalArgumentException("Invalid Argument!", ex);
        }
        return lines;
    }

    @Override
    public void writeToFile(List<String> lines) throws IOException {
        File targetOut = new File(outFile);

        try {
            Files.write(targetOut.toPath(),lines);
        }
        catch(IOException ex) {
            throw new IOException("Invalid I/O!", ex);
        }
    }
}
