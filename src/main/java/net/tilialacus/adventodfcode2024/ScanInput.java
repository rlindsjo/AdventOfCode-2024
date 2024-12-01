package net.tilialacus.adventodfcode2024;

import java.nio.file.Path;
import java.util.Scanner;
import java.util.function.Consumer;

public class ScanInput {
    public static void scanInput(String name, Consumer<Scanner> consumer) {
        try {
            Path path = Path.of("src/main/resources/input-1.txt");
            Scanner scanner = new Scanner(path);
            while (scanner.hasNextLine()) {
                consumer.accept(scanner);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
