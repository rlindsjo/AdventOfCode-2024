package net.tilialacus.adventodfcode2024;

import java.util.*;
import java.util.function.Predicate;

import static java.util.Collections.emptyList;

public class Day5 {

    public static void main(String[] args) {
        var before = new HashMap<String, List<String>>();
        var after = new HashMap<String, List<String>>();
        var updates = new ArrayList<List<String>>();
        ScanInput.inputAsLines(
                "src/main/resources/input-5.txt",
                line -> {
                    if (line.contains("|")) {
                        var parts = line.split("\\|");
                        before.computeIfAbsent(parts[0], k -> new ArrayList<>()).add(parts[1]);
                        after.computeIfAbsent(parts[1], k -> new ArrayList<>()).add(parts[0]);
                    } else if (line.contains(",")) {
                        updates.add(Arrays.asList(line.split(",")));
                    }
                }
        );

        var validMiddleSum = updates.stream().filter(new UpdateValidator(before, after))
                .map(it -> it.get(it.size() / 2))
                .mapToLong(Long::parseLong)
                .sum();

        var validMiddleSumCorrected = updates.stream().filter(Predicate.not(new UpdateValidator(before, after)))
                .map(new Sorter(before, after)::sort)
                .map(it -> it.get(it.size() / 2))
                .mapToLong(Long::parseLong)
                .sum();

        System.err.println(validMiddleSum);
        System.err.println(validMiddleSumCorrected);
    }

    private static class Sorter implements Comparator<String> {
        private final HashMap<String, List<String>> before;
        private final HashMap<String, List<String>> after;

        public Sorter(HashMap<String, List<String>> before, HashMap<String, List<String>> after) {
            this.before = before;
            this.after = after;
        }


        public List<String> sort(List<String> update) {
            return update.stream()
                    .sorted(this)
                    .toList();
        }

        @Override
        public int compare(String p1, String p2) {
            if (before.getOrDefault(p1, emptyList()).contains(p2)) {
                return 1;
            } else if (after.getOrDefault(p1, emptyList()).contains(p2)) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    private static Object fixSorting(List<String> update) {
        return null;
    }

    private static class UpdateValidator implements Predicate<List<String>> {
        private final HashMap<String, List<String>> before;
        private final HashMap<String, List<String>> after;

        public UpdateValidator(HashMap<String, List<String>> before, HashMap<String, List<String>> after) {
            this.before = before;
            this.after = after;
        }

        public boolean test(List<String> update) {
            for (int page = 0; page < update.size(); page++) {
                String p = update.get(page);
                var bf = after.getOrDefault(p, emptyList());
                for (int following = page + 1; following < update.size(); following++) {
                    String fp = update.get(following);
                    var ar = before.getOrDefault(fp, emptyList());
                    if (bf.contains(fp) || ar.contains(p)) {
                        return false;
                    }
                }
            }
            return true;
        }
    }
}
