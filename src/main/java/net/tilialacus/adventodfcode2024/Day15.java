package net.tilialacus.adventodfcode2024;

import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static net.tilialacus.adventodfcode2024.ScanInput.inputAsLines;

public class Day15 {
    public static void main(String[] args) {
        Pattern pattern = Pattern.compile("p=(\\d+),(\\d+) v=(-?\\d+),(-?\\d+)");
        var map = inputAsLines("src/main/resources/input-15.txt").stream().takeWhile(Predicate.not(String::isBlank))
                .map(String::toCharArray)
                .toArray(char[][]::new);

        var moves = inputAsLines("src/main/resources/input-15.txt").stream()
                .dropWhile(Predicate.not(String::isBlank))
                .collect(Collectors.joining());

        var robot = find('@', map);
        for (char m : moves.toCharArray()) {
            var target = robot.move(m);

            if (map[target.row()][target.col()] == 'O') {
                var free = target;
                while (map[free.row()][free.col()] == 'O') {
                    free = free.move(m);
                }
                if (map[free.row()][free.col()] == '.') {
                    swap(map, free, target);
                }
            }

            if (map[target.row()][target.col()] == '.') {
                swap(map, target, robot);
                robot = target;
            }
        }

        var gps = gps(map);
        System.err.println(gps);
    }

    private static long gps(char[][] map) {
        long score = 0;
        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[row].length; col++) {
                if (map[row][col] == 'O') {
                    score += row * 100 + col;
                }
            }
        }

        return score;
    }

    private static void swap(char[][] map, Pos apos, Pos bpos) {
        var temp = map[apos.row()][apos.col()];
        map[apos.row()][apos.col()] = map[bpos.row()][bpos.col()];
        map[bpos.row()][bpos.col()] = temp;
    }

    private static Pos find(char target, char[][] map) {
        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[row].length; col++) {
                if (map[row][col] == target) {
                    return new Pos(row, col);
                }
            }
        }
        return null;
    }

    record Pos(int row, int col) {
        Pos move(char m) {
            return switch (m) {
                case '>' -> new Pos(row, col + 1);
                case '<' -> new Pos(row, col - 1);
                case '^' -> new Pos(row - 1, col);
                case 'v' -> new Pos(row + 1, col);
                default -> throw new IllegalStateException("Unexpected value: " + m);
            };
        }
    }
}
