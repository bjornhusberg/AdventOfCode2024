import java.util.*;
import java.util.function.Supplier;

@ExpectedTestAnswer("154115708116294")
@VerifiedAnswer("116821732384052")

public class Day21b extends Day21a {

    @Override
    public Object solve() {
        return lines().stream().map(code -> {
            var complexity = Integer.parseInt(code.substring(0, code.length() - 1));
            return complexity * calculateCost(this::initNumpad, code, 26);
        }).reduce(0L, Long::sum);
    }

    private final Map<String, Long> costCache = new HashMap<>();
    private long calculateCost(Supplier<State> stateSupplier, String sequence, int pads) {
        if (pads == 0) {
            return sequence.length();
        }
        String key = pads + sequence;
        Long cost = costCache.get(key);
        if (cost == null) {
            cost = 0L;
            State state = stateSupplier.get();
            for (char c : sequence.toCharArray()) {
                cost += state.sequencesTo(c).stream().map(s ->
                        calculateCost(this::initArrowpad, s + "A", pads - 1)
                ).reduce(Long.MAX_VALUE, Math::min);
            }
            costCache.put(key, cost);
        }
        return cost;
    }
}
