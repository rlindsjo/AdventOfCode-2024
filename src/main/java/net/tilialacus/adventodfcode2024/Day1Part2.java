package net.tilialacus.adventodfcode2024;

import java.io.IOException;
import java.util.ArrayList;

import static net.tilialacus.adventodfcode2024.ScanInput.scanInput;

public class Day1Part2 {
    public static void main(String[] args) {
        var list1 = new ArrayList<Long>();
        var list2 = new ArrayList<Long>();
        scanInput("src/main/resources/input-1.txt",
                scanner -> {
                    list1.add(scanner.nextLong());
                    list2.add(scanner.nextLong());
                }
        );
        long similarity = list1.stream()
                .mapToLong(it -> it * list2.stream()
                    .filter(it::equals)
                    .count())
                .sum()
            ;

        System.err.println(similarity);
    }
}
