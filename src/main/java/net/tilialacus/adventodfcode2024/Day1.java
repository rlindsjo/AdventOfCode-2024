package net.tilialacus.adventodfcode2024;

import java.util.ArrayList;

import static java.lang.Math.abs;
import static net.tilialacus.adventodfcode2024.ScanInput.scanInput;

public class Day1 {
    public static void main(String[] args) {
        var list1 = new ArrayList<Long>();
        var list2 = new ArrayList<Long>();
        scanInput("src/main/resources/input-1.txt",
                scanner -> {
                    list1.add(scanner.nextLong());
                    list2.add(scanner.nextLong());
                }
        );
        System.err.println("Part 1: " + pairedDistance(list1, list2));
        System.err.println("Part 2: " + similarityScore(list1, list2));
    }

    private static long pairedDistance(ArrayList<Long> list1, ArrayList<Long> list2) {
        var a = list1.stream().sorted().iterator();
        var b = list2.stream().sorted().iterator();
        long distance = 0;
        while (a.hasNext() && b.hasNext()) {
            distance += abs(a.next() - b.next());
        }
        return distance;
    }

    private static long similarityScore(ArrayList<Long> list1, ArrayList<Long> list2) {
        return  list1.stream()
                .mapToLong(it -> it * list2.stream()
                        .filter(it::equals)
                        .count())
                .sum()
                ;
    }
}
