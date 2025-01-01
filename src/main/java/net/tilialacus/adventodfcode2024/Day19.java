package net.tilialacus.adventodfcode2024;

import java.util.*;
import java.util.stream.Collectors;

import static net.tilialacus.adventodfcode2024.ScanInput.inputAsLines;

public class Day19 {

    public static void main(String[] args) {
        var lines = inputAsLines("src/main/resources/input-19.txt");
        var inventory = Arrays.stream(lines.get(0).split(", ")).collect(Collectors.toSet());

        var possibleCount = 0;
        for (int i = 2; i < lines.size(); i++) {
            var pattern = lines.get(i);
            if (isPossible(inventory, pattern)) {
                possibleCount++;
            }
        }
        System.err.println("Day 19 part 1 patterns possible:" + possibleCount);
    }

    private static boolean isPossible(Set<String> inventory, String pattern) {
        Queue<String> possible = new PriorityQueue<>(Comparator.comparing(String::length).reversed());

        for (String part : inventory) {
            if (pattern.startsWith(part)) {
                possible.add(pattern.substring(part.length()));
            }
        }
        while (!possible.isEmpty()) {
            var assembly = possible.remove();
            for (String part : inventory) {
                if (assembly.equals(part)) {
                    return true;
                } else if (assembly.startsWith(part)) {
                    String substring = assembly.substring(part.length());
                    if (!possible.contains(substring)) {
                        possible.add(substring);
                    }
                }
            }
        }
        return false;
    }
}
