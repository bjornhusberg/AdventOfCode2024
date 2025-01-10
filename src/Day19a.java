import java.util.Arrays;
import java.util.List;

@ExpectedTestAnswer("6")
@VerifiedAnswer("296")
public class Day19a extends Day {

    @Override
    public Object solve() {
        var towels = Arrays.stream(line(0).split(",")).map(String::trim).toList();
        return lines().stream().skip(2).filter(combination -> isPossible(towels, combination)).count();
    }

    private boolean isPossible(List<String> towels, String combination) {
        if (combination.isEmpty()) {
            return true;
        }
        for (String towel : towels) {
            if (combination.startsWith(towel) && isPossible(towels, combination.substring(towel.length()))) {
                return true;
            }
        }
        return false;
    }
}
