import java.util.List;

@ExpectedTestAnswer("143")
@VerifiedAnswer("5275")
public class Day5a extends Day {

    @Override
    public Object solve() {
        var rules = lines(line -> line.contains("|"))
                .map(line -> line.trim().split("\\|"))
                .toList();

        return lines(line -> !line.contains("|") && !line.isEmpty())
                .map(line -> line.trim().split(","))
                .filter(page -> isValid(page, rules))
                .map(page -> Integer.parseInt(page[page.length / 2]))
                .reduce(0, Integer::sum);
    }

    private boolean isValid(String[] page, List<String[]> rules) {
        for (int i = 0; i < page.length; i++) {
            if (!isValid(page, i, rules)) {
                return false;
            }
        }
        return true;
    }

    private boolean isValid(String[] page, int i, List<String[]> rules) {
        return rules.stream().filter(rule -> rule[0].equals(page[i]))
                .noneMatch(rule -> {
                    for (int j = 0; j < i; j++) {
                        if (page[j].equals(rule[1])) {
                            return true;
                        }
                    }
                    return false;
                });
    }
}
