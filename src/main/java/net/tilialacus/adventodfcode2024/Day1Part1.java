package net.tilialacus.adventodfcode2024;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import static java.lang.Math.abs;

public class Day1Part1 {
    public static void main(String[] args) throws IOException {
        var list1 = new ArrayList<Long>();
        var list2 = new ArrayList<Long>();
        Path path = Path.of("src/main/resources/input-1.txt");
        Scanner scanner = new Scanner(path);
        while (scanner.hasNextLine()) {
            list1.add(scanner.nextLong());
            list2.add(scanner.nextLong());
        }
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
