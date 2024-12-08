package net.tilialacus.adventodfcode2024;

import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static net.tilialacus.adventodfcode2024.ScanInput.inputAsLines;

public class Day3 {

    private static final Pattern MUL_PATTERN = Pattern.compile("mul\\((\\d+),(\\d+)\\)");

    public static void main(String[] args) {
        var sum = sumOfMul();

        System.err.println("Part 1: " + sum.get());
    }

    private static AtomicLong sumOfMul() {
        var sum = new AtomicLong();
        inputAsLines("src/main/resources/input-3.txt",
                line -> {
                    Matcher matcher = MUL_PATTERN.matcher(line);
                    var i = 0;
                    while (i < line.length() &&  matcher.find(i)) {
                        sum.getAndAdd(Long.parseLong(matcher.group(1)) * Long.parseLong(matcher.group(2)));
                        i = matcher.end();
                    }
                }
        );
        return sum;
    }
}
