package net.tilialacus.adventodfcode2024;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

public class Day1Part2 {
    public static void main(String[] args) throws IOException {
        var list1 = new ArrayList<Long>();
        var list2 = new ArrayList<Long>();
        Path path = Path.of("src/main/resources/input-1.txt");
        Scanner scanner = new Scanner(path);
        while (scanner.hasNextLine()) {
            list1.add(scanner.nextLong());
            list2.add(scanner.nextLong());
        }
        var a = list1.listIterator();
        long similarity = 0;
        while (a.hasNext()) {
            Long next = a.next();
            similarity += next * list2.stream()
                    .filter(next::equals)
                    .count()
            ;
        }
        System.err.println(similarity);
    }
}
