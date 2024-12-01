package net.tilialacus.adventodfcode2024;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import static java.lang.Math.abs;
import static net.tilialacus.adventodfcode2024.ScanInput.scanInput;

public class Day1Part1 {
    public static void main(String[] args) {
        var list1 = new ArrayList<Long>();
        var list2 = new ArrayList<Long>();
        scanInput("src/main/resources/input-1.txt",
                scanner -> {
                    list1.add(scanner.nextLong());
                    list2.add(scanner.nextLong());
                }
        );
        Collections.sort(list1);
        Collections.sort(list2);
        var a = list1.listIterator();
        var b = list2.listIterator();
        long similarity = 0;
        while (a.hasNext() && b.hasNext()) {
            similarity += abs(a.next() - b.next());
        }
        System.err.println(similarity);
    }
}
