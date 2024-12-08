package net.tilialacus.adventodfcode2024;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3 {

    private static final Pattern MUL_PATTERN = Pattern.compile("mul\\((\\d+),(\\d+)\\)");

    public static void main(String[] args) {
        var mem = ScanInput.inputAsSingleLine("src/main/resources/input-3.txt");
        System.err.println("Part 1: " + sumOfMul(mem));
        System.err.println("Part 2: " + sumOfEnabledMul(mem));
    }

    private static long sumOfMul(String mem) {
        var sum = 0L;
        Matcher matcher = MUL_PATTERN.matcher(mem);
        var i = 0;
        while (i < mem.length() && matcher.find(i)) {
            sum += Long.parseLong(matcher.group(1)) * Long.parseLong(matcher.group(2));
            i = matcher.end();
        }
        return sum;
    }

    private static long sumOfEnabledMul(String line) {
        var matcher = MUL_PATTERN.matcher(line);
        var doMatcher = Pattern.compile("do\\(\\)").matcher(line);
        var dontMatcher = Pattern.compile("don't\\(\\)").matcher(line);
        var sum = 0L;
        var i = 0;
        var dont = dontMatcher.find()
                ? dontMatcher.start()
                : line.length();

        while (i < line.length() && matcher.find(i)) {
            if (matcher.end() <= dont) {
                sum +=Long.parseLong(matcher.group(1)) * Long.parseLong(matcher.group(2));
                i = matcher.end();
            } else {
                if (doMatcher.find(dontMatcher.end())) {
                    i = doMatcher.end();
                    dont = dontMatcher.find(i)
                            ? dontMatcher.start()
                            : line.length();
                } else {
                    i = line.length();
                }
            }
        }
        return sum;
    }
}
