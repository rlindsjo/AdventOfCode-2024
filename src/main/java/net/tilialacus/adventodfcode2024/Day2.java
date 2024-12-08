package net.tilialacus.adventodfcode2024;

import java.util.ArrayList;

import static java.lang.Math.abs;
import static net.tilialacus.adventodfcode2024.ScanInput.inputAsLines;
import static net.tilialacus.adventodfcode2024.ScanInput.splitToLong;

public class Day2 {

    public static void main(String[] args) {
        var reports = new ArrayList<Report>();
        inputAsLines("src/main/resources/input-2.txt",
                line -> {
                    reports.add(new Report(splitToLong(line)));
                }
        );

        System.err.println("Part 1: " + reports.stream().filter(Report::isSafe).count());
    }

    private static class Report {
        private long[] levels;

        public Report(long[] levels) {
            this.levels = levels;
        }

        public boolean isSafe() {
            // Assume a lot
            boolean increasing = true;
            boolean decreasing = true;
            boolean safe = true;
            for (int i = 1; i < levels.length; i++) {
                increasing = increasing && levels[i] > levels[i - 1];
                decreasing = decreasing && levels[i] < levels[i - 1];
                safe = safe && abs(levels[i] - levels[i - 1]) < 4;
            }

            return (increasing || decreasing) && safe;
        }
    }
}
