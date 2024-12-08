package net.tilialacus.adventodfcode2024;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public static String inputAsSingleLine(String name) {
        Path path = Path.of(name);
        try (var stream = Files.lines(path)){
            return stream.collect(Collectors.joining());
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

    public static <T> Stream<T> mapInput(String name, Function<String, T> mapper) {
        Path path = Path.of(name);
        try {
            return Files.lines(path).map(mapper);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
