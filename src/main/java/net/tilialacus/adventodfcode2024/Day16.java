package net.tilialacus.adventodfcode2024;

import net.tilialacus.adventodfcode2024.Map.Pos;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;

import static net.tilialacus.adventodfcode2024.ScanInput.inputAsMap;

public class Day16 {
    enum Direction {
        NORTH, EAST, SOUTH, WEST;

        public Direction left() {
            return Direction.values()[(this.ordinal() + 3) % 4];
        }

        public Direction right() {
            return Direction.values()[(this.ordinal() + 1) % 4];
        }
    }

    public static void main(String[] args) {
        var map = inputAsMap("src/main/resources/input-16.txt");
        var queue = new PriorityQueue<>(Comparator.comparing(Path::score));
        queue.add(new Path(null, map.find('S'), Direction.EAST, 0));
        var visited = new HashMap<Visit, Integer>();
        var paths = new HashSet<Path>();
        while(!queue.isEmpty()) {
            var current = queue.poll();
            Visit o = new Visit(current.position(), current.direction);
            if (map.tileAt(current.position()) == '#' ||
                    visited.computeIfAbsent(o, k -> current.score()) < current.score()
            ) {
                continue;
            }
            if (map.tileAt(current.position()) == 'E') {
                paths.add(current);
            } else {
                queue.add(current.move(
                        switch (current.direction()) {
                            case NORTH -> current.position().up();
                            case SOUTH -> current.position().down();
                            case EAST -> current.position().right();
                            case WEST -> current.position().left();
                        })
                );
                queue.add(current.turn(current.direction().left()));
                queue.add(current.turn(current.direction().right()));
            }
        }
        System.out.println("Day 15 part 1 score " + paths.iterator().next().score());

        var tiles = new HashSet<>();
        for (Path path : paths) {
            while (path != null) {
                tiles.add(path.position());
                path = path.previous();
            }
        }
        System.out.println("Day 15 part 2 tiles " + tiles.size());
    }

    record Path(Path previous, Pos position, Direction direction, int score) {
        Path move(Pos position) {
            return new Path(this, position, direction, score + 1);
        }
        Path turn(Direction direction) {
            return new Path(this, position, direction, score + 1000);
        }
        public String path() {
            if (previous == null) {
                return direction.name();
            } else {
                return previous.path() + ", " + direction.name();
            }
        }

    }
    record Visit(Pos position, Direction direction) {}
}
