package net.tilialacus.adventodfcode2024;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;

import static net.tilialacus.adventodfcode2024.Map.Pos.pos;

public class Day18 {
    public static void main(String[] args) {
        Map.Pos start = pos(0, 0);
        var exit = pos(70, 70);
        var map1 = blankMap();
        ScanInput.inputAsLines("src/main/resources/input-18.txt").stream()
                .limit(1024)
                .map(ScanInput::parseColRow)
                .forEach(pos -> map1.setTile(pos, '#'));
        System.err.println("Day 18 part 1 path length : " + findPath(map1, start, exit).length());

        var map2 = blankMap();
        for (String line : ScanInput.inputAsLines("src/main/resources/input-18.txt")) {
            var pos = ScanInput.parseColRow(line);
            map2.setTile(pos, '#');
            var path = findPath(map2, start, exit);
            if (path == null) {
                System.err.println("Day 18 part 2 last byte : " + line);
                break;
            }
        }
    }

    private static Map blankMap() {
        return new Map(71, 71, '.');
    }

    private static Map.Path findPath(Map map, Map.Pos start, Map.Pos exit) {
        Queue<Map.Path> queue = new LinkedList<>();
        var visited = new HashSet<Map.Pos>();
        queue.offer(Map.Path.start(start));
        while (!queue.isEmpty()) {
            var current = queue.poll();
            if (visited.contains(current.pos())) {
                continue;
            } else {
                visited.add(current.pos());
            }
            if (exit.equals(current.pos())) {
                return current;
            }
            possible(map, current.up()).ifPresent(queue::offer);
            possible(map, current.down()).ifPresent(queue::offer);
            possible(map, current.left()).ifPresent(queue::offer);
            possible(map, current.right()).ifPresent(queue::offer);
        }
        return null;
    }

    private static Optional<Map.Path> possible(Map map, Map.Path path) {
        if (map.onMap(path.pos()) && map.tileAt(path.pos()) == '.') {
            return Optional.of(path);
        } else {
        return Optional.empty();}
    }
}

