package net.tilialacus.adventodfcode2024;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Integer.parseInt;
import static net.tilialacus.adventodfcode2024.Map.Pos.pos;

public class ScanInput {
    public static void scanInput(String name, Consumer<Scanner> consumer) {
        try {
            Path path = Path.of(name);
            Scanner scanner = new Scanner(path);
            while (scanner.hasNextLine()) {
                consumer.accept(scanner);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void scanInput(String name, String delimiter, Consumer<Scanner> consumer) {
        try {
            Path path = Path.of(name);
            Scanner scanner = new Scanner(path);
            scanner.useDelimiter(delimiter);
            while (scanner.hasNextLine()) {
                consumer.accept(scanner);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void inputAsLines(String name, Consumer<String> consumer) {
        Path path = Path.of(name);
        try (var stream = Files.lines(path)){
            stream.forEach(consumer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static long[] splitToLong(String line) {
        return Arrays.stream(line.split("\\s+"))
                .mapToLong(Long::parseLong)
                .toArray();
    }

    public static int[] splitToInt(String line) {
        return Arrays.stream(line.split("\\s+|,"))
                .mapToInt(Integer::parseInt)
                .toArray();
    }

    public static Map.Pos parseColRow(String line) {
        String[] split = line.split("\\s+|,");
        return pos(parseInt(split[1]), parseInt(split[0]));
    }

    public static String inputAsSingleLine(String name) {
        Path path = Path.of(name);
        try (var stream = Files.lines(path)){
            return stream.collect(Collectors.joining());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<String> inputAsLines(String name) {
        Path path = Path.of(name);
        try {
            return Files.readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static char[][] inputAsArray(String name) {
        Path path = Path.of(name);
        try {
            return Files.lines(path)
                    .map(String::toCharArray)
                    .toArray(char[][]::new);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Map inputAsMap(String name) {
        Path path = Path.of(name);
        try {
            return new Map(Files.lines(path)
                    .map(String::toCharArray)
                    .toArray(char[][]::new));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> Stream<T> mapInput(String name, Function<String, T> mapper) {
        Path path = Path.of(name);
        try {
            return Files.lines(path).map(mapper);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
