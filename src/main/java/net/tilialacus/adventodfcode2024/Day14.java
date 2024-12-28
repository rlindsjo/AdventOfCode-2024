package net.tilialacus.adventodfcode2024;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.lang.Long.parseLong;
import static net.tilialacus.adventodfcode2024.ScanInput.inputAsLines;

public class Day14 {
    public static void main(String[] args) {
        Pattern pattern = Pattern.compile("p=(\\d+),(\\d+) v=(-?\\d+),(-?\\d+)");
        var robots = inputAsLines("src/main/resources/input-14.txt").stream()
                .map(pattern::matcher)
                .filter(Matcher::find)
                .map(matcher ->
                    new Robot(
                            new XY(
                                    parseLong(matcher.group(1)),
                                    parseLong(matcher.group(2))
                            ),
                            new XY(
                                    parseLong(matcher.group(3)),
                                    parseLong(matcher.group(4))
                            )
                    )
                )
                .toList();

        int width = 101;
        int height = 103;
        int seconds = 100;
        var pos = robots.stream()
                .map(it -> it.atTime(seconds, width, height))
                .filter(it -> it.x() != width / 2 && it.y() != height / 2)
                .toList();
        var quadrants = pos.stream()
                .collect(Collectors.groupingBy(it -> it.x() * 2 / width + it.y() * 2 / height * 2))
                .values();
        var safety = quadrants.stream()
                .mapToInt(List::size)
                .reduce(1, (a,b) -> a * b);
        System.err.println("Day 14 safety factor: " + safety);
    }

    record Robot(XY p, XY v) {
        XY atTime(long seconds, long width, long height) {
           return new XY(
                   ((p.x + v.x * seconds) % width + width) % width,
                   ((p.y + v.y * seconds) % height + height ) % height
           );
        }
    }
    record XY(long x, long y) {}
}
