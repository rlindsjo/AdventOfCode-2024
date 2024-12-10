package net.tilialacus.adventodfcode2024;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static net.tilialacus.adventodfcode2024.ScanInput.inputAsArray;

public class Day10 {

    public static void main(String[] args) {
        var map = inputAsArray("src/main/resources/input-10.txt");

        var candidates = new LinkedList<Trail>();
        var trails = new ArrayList<Trail>();

        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[row].length; col++) {
                Pos pos = new Pos(row, col);
                if (getHeight(map, pos) == 0) {
                    candidates.add(new Trail(null, pos));
                }
            }
        }

        while (!candidates.isEmpty()) {
            var trail = candidates.poll();
            if (trail.length() < 10) {
                Stream.of(
                                trail.getPos().move(-1, 0),
                                trail.getPos().move(1, 0),
                                trail.getPos().move(0, -1),
                                trail.getPos().move(0, 1)
                        ).filter(it -> onMap(map, it))
                        .filter(it -> getHeight(map, it) == trail.length())
                        .map(trail::to)
                        .forEach(candidates::add);
            } else if (trail.length() == 10) {
                trails.add(trail);
            }
        }

        var startingGroups = trails.stream()
                .collect(Collectors.groupingBy(Trail::startPos,
                        Collectors.mapping(Function.identity(), Collectors.toList())))
                .values();

        var score = startingGroups.stream()
                .mapToLong(it -> it.stream()
                        .map(Trail::getPos)
                        .distinct()
                        .count())
                .sum();

        System.err.println("Day 10 part 1: " + score);
        System.err.println("Day 10 part 2: " + trails.size());
    }

    private static boolean onMap(char[][] map, Pos pos) {
        return pos.row() >= 0 && pos.row() < map.length & pos.col() >= 0 && pos.col() < map[0].length;
    }

    private static int getHeight(char[][] map, Pos pos) {
        return map[pos.row()][pos.col()] - '0';
    }

    static class Trail {
        private final Pos pos;
        private final Trail prev;

        public Trail(Trail prev, Pos pos) {
            this.prev = prev;
            this.pos = pos;
        }

        public Trail to(Pos pos) {
            return new Trail(this, pos);
        }

        public int length() {
            return prev == null ? 1 : 1 + prev.length();
        }

        public Pos getPos() {
            return pos;
        }

        public Pos startPos() {
            return prev == null ? pos : prev.startPos();
        }

        @Override
        public String toString() {
            if (prev != null) {
                return pos + ", " + prev;
            } else {
                return pos.toString();
            }
        }
    }

    record Pos(int row, int col) {
        public Pos move(int dr, int dc) {
            return new Pos(row + dr, col + dc);
        }
    };
}
