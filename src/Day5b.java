import java.util.List;

@ExpectedTestAnswer("123")
@VerifiedAnswer("6191")
public class Day5b extends Day {

    @Override
    public Object solve() {
        var rules = lines(line -> line.contains("|"))
                .map(line -> line.trim().split("\\|"))
                .toList();

        return lines(line -> !line.contains("|") && !line.isEmpty())
                .map(line -> line.trim().split(","))
                .filter(page -> !isValidOrSwap(page, rules))
                .map(page -> {
                    while (!isValidOrSwap(page, rules));
                    return Integer.parseInt(page[page.length / 2]);
                })
                .reduce(0, Integer::sum);
    }

    private boolean isValidOrSwap(String[] page, List<String[]> rules) {
        for (int i = 0; i < page.length; i++) {
            if (!isValidOrSwap(page, i, rules)) {
                return false;
            }
        }
        return true;
    }

    private boolean isValidOrSwap(String[] page, int i, List<String[]> rules) {
        return rules.stream().filter(rule -> rule[0].equals(page[i]))
                .noneMatch(rule -> {
                    for (int j = 0; j < i; j++) {
                        if (page[j].equals(rule[1])) {
                            page[j] = page[i];
                            page[i] = rule[1];
                            return true;
                        }
                    }
                    return false;
                });
    }
}
