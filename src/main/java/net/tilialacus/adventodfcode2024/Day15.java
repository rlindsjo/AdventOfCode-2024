package net.tilialacus.adventodfcode2024;

import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static net.tilialacus.adventodfcode2024.ScanInput.inputAsLines;

public class Day15 {
    public static final Pattern PATTERN = Pattern.compile("p=(\\d+),(\\d+) v=(-?\\d+),(-?\\d+)");

    public static void main(String[] args) {
        part1();
        part2();
    }

    private static void part1() {
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

        print(map);
        System.err.println("Day 15 part 1 " + gps(map));
    }

    private static void part2() {
        var map = inputAsLines("src/main/resources/input-15.txt").stream().takeWhile(Predicate.not(String::isBlank))
                .map(it -> it.replaceAll("#", "##"))
                .map(it -> it.replaceAll("O", "[]"))
                .map(it -> it.replaceAll("\\.", ".."))
                .map(it -> it.replaceAll("@", "@."))
                .map(String::toCharArray)
                .toArray(char[][]::new);

        var moves = inputAsLines("src/main/resources/input-15.txt").stream()
                .dropWhile(Predicate.not(String::isBlank))
                .collect(Collectors.joining());

        var robot = find('@', map);
        print(map);

        for (char m : moves.toCharArray()) {
            if (canMove(map, robot.move(m), m)) {
                push(map, '.', robot, m);
                robot = robot.move(m);
            }
        }

        System.err.println("Day 15 part 2 wide " + gps(map));
    }

    private static void push(char[][] map, char replace, Pos pos, char move) {
        var tile = map[pos.row()][pos.col()];
        switch (tile) {
            case '.': map[pos.row()][pos.col()] = replace;
            break;
            case '@':
                push(map, tile, pos.move(move), move);
                map[pos.row()][pos.col()] = replace;
                break;
            case '[', ']': switch (move) {
                case '<', '>':
                    push(map, tile, pos.move(move), move);
                    map[pos.row()][pos.col()] = replace;
                break;
                case '^', 'v':
                    push(map, tile, pos.move(move), move);
                    map[pos.row()][pos.col()] = replace;
                    var other = tile == '[' ? pos.right() : pos.left();
                    push(map, map[other.row()][other.col()], other.move(move), move);
                    map[other.row()][other.col()] = '.';
                    break;
                default: throw new IllegalStateException("Unexpected value: " + tile);
            } break;
            default: throw new IllegalStateException("Unexpected value: " + tile);
        }
    }

    private static boolean canMove(char[][] map, Pos target, char move) {
        var tile = map[target.row()][target.col()];
        return switch (tile) {
            case '.' -> true;
            case '#' -> false;
            case '[', ']' -> switch (move) {
                case '<', '>' -> canMove(map, target.move(move), move);
                case '^', 'v' -> tile == '['
                        ? canMove(map, target.move(move), move) && canMove(map, target.right().move(move), move)
                        : canMove(map, target.move(move), move) && canMove(map, target.left().move(move), move);
                default -> throw new IllegalStateException("Unexpected value: " + tile);
            };
            default -> throw new IllegalStateException("Unexpected value: " + tile);
        };
    }

    private static void print(char[][] map) {
        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[row].length; col++) {
                sb.append(map[row][col]);
            }
            sb.append("\n");
        }
        System.err.println(sb);
    }

    private static long gps(char[][] map) {
        long score = 0;
        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[row].length; col++) {
                if (map[row][col] == 'O' || map[row][col] == '[') {
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
                case '>' -> right();
                case '<' -> left();
                case '^' -> new Pos(row - 1, col);
                case 'v' -> new Pos(row + 1, col);
                default -> throw new IllegalStateException("Unexpected value: " + m);
            };
        }
        Pos right() {
            return new Pos(row, col + 1);
        }
        Pos left() {
            return new Pos(row, col - 1);
        }
    }
}
