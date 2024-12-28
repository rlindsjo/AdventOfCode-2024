package net.tilialacus.adventodfcode2024;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;
import static net.tilialacus.adventodfcode2024.ScanInput.inputAsLines;

public class Day13 {
    public static void main(String[] args) {
        String lines = inputAsLines("src/main/resources/input-13.txt").stream().collect(Collectors.joining("\n"));
        Pattern pattern = Pattern.compile("Button A: X\\+(\\d+), Y\\+(\\d+)\\nButton B: X\\+(\\d+), Y\\+(\\d+)\\nPrize: X=(\\d+), Y=(\\d+)", Pattern.MULTILINE);
        var matcher = pattern.matcher(lines);
        var problems = new ArrayList<Problem>();

        while (matcher.find()) {
            BigDecimal offset = BigDecimal.valueOf(10000000000000L);
            problems.add(new Problem(
                    new Button(new BigDecimal(matcher.group(1)), new BigDecimal(matcher.group(2))),
                    new Button(new BigDecimal(matcher.group(3)), new BigDecimal(matcher.group(4))),
                    new BigDecimal(matcher.group(5)).add(offset), new BigDecimal(matcher.group(6)).add(offset)));
        }

        var tokens = 0L;
        for (Problem problem : problems) {
            try {
                BigDecimal divisor = problem.a().x().multiply(problem.b().y()).subtract(problem.a().y().multiply(problem.b().x()));
                var a = problem.b().y().multiply(problem.x()).subtract(problem.y().multiply(problem.b().x()))
                        .divide(divisor);
                var b = problem.a().x().multiply(problem.y()).subtract(problem.x().multiply(problem.a().y()))
                        .divide(divisor);
                tokens += a.longValue() * 3 + b.longValue();
            } catch (ArithmeticException e) {
                // Only integer result
            }

        }
        System.err.println(tokens);
    }

    record Button(BigDecimal x, BigDecimal y) {}
    record Problem(Button a, Button b, BigDecimal x, BigDecimal y) {}
}
