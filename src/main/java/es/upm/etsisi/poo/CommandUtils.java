package es.upm.etsisi.poo;

public class CommandUtils {

    public static String getName(String line) {
        int first = line.indexOf("\"");
        int last = line.lastIndexOf("\"");
        if (first != -1 && last > first) {
            return line.substring(first + 1, last);
        }
        return null;
    }

    public static String[] getAfterName(String line) {
        int lastQuote = line.lastIndexOf("\"");
        if (lastQuote == -1) {
            return new String[0];
        }
        String textAfterName = line.substring(lastQuote + 1);
        String text = textAfterName.trim();
        if (text.isEmpty()) {
            return new String[0];
        }
        return text.split(" ");
    }
}
