#JavaGrep

##Introduction
The JavaGrep app implements a simple `grep` function within Java. The app searches for a text pattern recursively
in a given directory. Through the project, the developer familiarized themselves with the usage of 
Java Input and Output methods, Stream APIs and Lambda, and String pattern searching using Java Regex.

##Usage
The app takes in three arguments: `regex`, `rootPath`, and `outFile`.
* `regex`: A special text string for describing a search pattern
* `rootPath`: Root directory path 
* `outFile`: Output file name - Specified with a directory

When the three arguments are passed to the app, the program searches within all files that lie under 
`rootPath`(Directory and Subdirectory) and those of which contain the specified `regex` pattern. Then,
the program writes the contents of the files into `outFile`.

####Simple Example
When the parameters 
```bash
.*IllegalArgumentException.* ./grep/src /tmp/grep.out"
```
are passed to the program, it outputs the following text file
```text
  * @throws IllegalArgumentException if a given inputFile is not a file
    List<String> readLines(File inputFile) throws IllegalArgumentException;
     * @throws IllegalArgumentException if the input is not a valid file
            throw new IllegalArgumentException("USAGE: JavaGrep regex rootPath outFile");
    public List<String> readLines(File inputFile) throws IllegalArgumentException {
        catch (IllegalArgumentException | IOException ex) {
            throw new IllegalArgumentException("Invalid Argument!", ex);
            throw new IllegalArgumentException("USAGE: JavaGrep regex rootPath outFile");
    public List<String> readLines(File inputFile) throws IllegalArgumentException {
        catch (IllegalArgumentException | IOException ex) {
            throw new IllegalArgumentException("Invalid Argument!", ex);
```

##Pseudocode
The following is a pseudocode for the method `process`
```bash
matchedLines = matchedLineArray
for file in listFilesRecursively(rootDir)
  for line in readLines(file)
      if containsPattern(line)
        matchedLines.add(line)
writeToFile(matchedLines)
```

##Performance Issue
The program will not work on large files as the methods read the entire file into memory before copying it to the list. This process will be extremely time-consuming when the file is large in size. 
A possible solution is to use the Stream API and lambda, but it will be a somewhat mediocre solution.

##Possible Improvements
1. Solve the memory issue of the JavaGrep app

2. Easier accessibility by allowing the user to specify the three parameters at runtime

3. For further flexibility, the ability for the program to write the output by character instead of by line
will prove useful.