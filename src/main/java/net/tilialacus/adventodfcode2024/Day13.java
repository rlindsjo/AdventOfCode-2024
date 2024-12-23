package net.tilialacus.adventodfcode2024;

import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;
import static net.tilialacus.adventodfcode2024.ScanInput.inputAsLines;

public class Day13 {

    public static void main(String[] args) {

        String lines = inputAsLines("src/main/resources/input-13.txt").stream().collect(Collectors.joining("\n"));
// Prize: X=4729, Y=5725
        Pattern pattern = Pattern.compile("Button A: X\\+(\\d+), Y\\+(\\d+)\\nButton B: X\\+(\\d+), Y\\+(\\d+)\\nPrize: X=(\\d+), Y=(\\d+)", Pattern.MULTILINE);
        var matcher = pattern.matcher(lines);
        var problems = new ArrayList<Problem>();

        while (matcher.find()) {
            problems.add(new Problem(
                    new Button(parseInt(matcher.group(1)), parseInt(matcher.group(2))),
                    new Button(parseInt(matcher.group(3)), parseInt(matcher.group(4))),
                    parseInt(matcher.group(5)), parseInt(matcher.group(6))));
        }

        var tokens = 0;
        for (Problem problem : problems) {
            var q = Integer.MAX_VALUE;
            for (int a = 0; a <= 100; a++) {
                if (problem.a().x() > problem.x() || problem.a().y() > problem.y()) {
                    break;
                }
                var b = (problem.x() - a * problem.a().x()) / problem.b().x();

                if (problem.a().x() * a + problem.b().x() * b == problem.x() && problem.a().y() * a + problem.b().y() * b == problem.y()) {
                    if (a * 3 + b < q) {
                        q = a * 3 + b;
                    }
                }
            }
            if (q < Integer.MAX_VALUE) {
                tokens += q;
            }
        }
        System.err.println(tokens);
    }

    record Button(int x, int y) {}
    record Problem(Button a, Button b, int x, int y) {}
}
