package net.tilialacus.adventodfcode2024;

import java.util.*;

import static net.tilialacus.adventodfcode2024.Day12.Direction.*;
import static net.tilialacus.adventodfcode2024.ScanInput.inputAsArray;

public class Day12 {

    private static char[][] map;

    public static void main(String[] args) {
        map = inputAsArray("src/main/resources/input-12.txt");

        Collection<List<Plot>> fields = calculateFields();
        System.err.println("Day 12 part 1: " + fields.stream()
                .mapToInt(it -> it.size() * it.stream()
                        .mapToInt(Plot::fences)
                        .sum())
                .sum());

        System.err.println("Day 12 part 1: " + fields.stream()
                .mapToInt(it -> it.size() * it.stream()
                        .mapToInt(Plot::corners)
                        .sum())
                .sum());
    }

    private static Collection<List<Plot>> calculateFields() {
        var plotMap = new Plot[map.length][map[0].length];
        var areas = new HashMap<Integer, List<Plot>>();
        // Color all regions
        int region = 0;
        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[row].length; col++) {
                if (plotMap[row][col] != null) {
                    continue;
                }
                Plot plot = new Plot(++region, map[row][col], row, col);
                plotMap[row][col] = plot;
                explore(plotMap, plot);
            }
        }
        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[row].length; col++) {
                Plot plot = plotMap[row][col];
                calculateCourners(plotMap, plot);
                areas.computeIfAbsent(plot.region, k -> new ArrayList<>() ).add(plot);
            }
        }
        return areas.values();
    }

    private static void calculateCourners(Plot[][] plotMap, Plot plot) {
        if (plot.fences.containsAll(EnumSet.of(UP, RIGHT))) {
            plot.courners++;
        }
        if (plot.fences.containsAll(EnumSet.of(RIGHT, DOWN))) {
            plot.courners++;
        }
        if (plot.fences.containsAll(EnumSet.of(DOWN, LEFT))) {
            plot.courners++;
        }
        if (plot.fences.containsAll(EnumSet.of(LEFT, UP))) {
            plot.courners++;
        }
        if (!plot.fences.contains(UP) && !plot.fences.contains(RIGHT)
            && plotMap[plot.row - 1][plot.col + 1].fences.containsAll(EnumSet.of(DOWN, LEFT))) {
            plot.courners++;
        }
        if (!plot.fences.contains(RIGHT) && !plot.fences.contains(DOWN)
                && plotMap[plot.row + 1][plot.col + 1].fences.containsAll(EnumSet.of(UP, LEFT))) {
            plot.courners++;
        }
        if (!plot.fences.contains(DOWN) && !plot.fences.contains(LEFT)
                && plotMap[plot.row + 1][plot.col - 1].fences.containsAll(EnumSet.of(UP, RIGHT))) {
            plot.courners++;
        }
        if (!plot.fences.contains(LEFT) && !plot.fences.contains(UP)
                && plotMap[plot.row - 1][plot.col - 1].fences.containsAll(EnumSet.of(DOWN, RIGHT))) {
            plot.courners++;
        }
    }

    private static void explore(Plot[][] plotMap, Plot plot) {
        for (Direction direction : values()) {
            var row = plot.row + direction.dr;
            var col = plot.col + direction.dc;
            if (row < 0 || row >= map.length || col < 0 || col >= map[row].length) {
                plot.fences.add(direction);
                continue;
            }
            var neighbour = plotMap[row][col];
            if (neighbour == null) {
                if (map[row][col] == plot.crop) {
                    neighbour = new Plot(plot.region, plot.crop, row, col);
                    plotMap[row][col] = neighbour;
                    explore(plotMap, neighbour);
                }
            }
            if (neighbour == null || neighbour.crop != plot.crop) {
                plot.fences.add(direction);
            }
        }
    }

    record Field(int area, int perimeter) {
        public static Field fence() {
            return new Field(0, 1);
        }

        public static Field start() {
            return new Field(1, 0);
        }

        Field add(Field other) {
            if (other == null) return this;
            return new Field(area + other.area, perimeter + other.perimeter);
        }
    }

    enum Direction {UP(-1,0), DOWN(1,0), LEFT(0,-1), RIGHT(0,1);

        private final int dr;
        private final int dc;

        Direction(int dr, int dc) {
            this.dr = dr;
            this.dc = dc;
        }
    }

    static class Plot {
        public int courners;
        EnumSet<Direction> fences;
        int region;
        char crop;
        int row;
        int col;

        public Plot(int region, char crop, int row, int col) {
            this.region = region;
            this.crop = crop;
            this.row = row;
            this.col = col;
            this.fences = EnumSet.noneOf(Direction.class);
        }

        int fences() {
            return fences.size();
        }

        int corners() {
            return courners;
        }
    }
}
