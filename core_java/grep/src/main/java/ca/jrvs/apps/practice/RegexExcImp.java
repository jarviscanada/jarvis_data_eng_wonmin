package ca.jrvs.apps.practice;

public class RegexExcImp implements RegexExc{

    @Override
    public boolean matchJpeg(String filename) {
        String jpegPattern = "[^\\s]+(\\.(jpg|jpeg))$";
        return filename.matches(jpegPattern);
    }

    @Override
    public boolean matchIp(String ip) {
        String ipPattern = "\\b\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\b";
        return ip.matches(ipPattern);
    }

    @Override
    public boolean isEmptyLine(String line) {
        String emptyRegex = "^\\s*$";
        return line.matches(emptyRegex);
    }
}
