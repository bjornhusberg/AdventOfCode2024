import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;

@ExpectedTestAnswer("16")
@VerifiedAnswer("619970556776002")
public class Day19b extends Day {

    @Override
    public Object solve() {
        var towels = Arrays.stream(line(0).split(",")).map(String::trim).collect(Collectors.toSet());
        var cache = new HashMap<String, BigInteger>();
        cache.put("", BigInteger.valueOf(1));
        var sum = BigInteger.valueOf(0);
        for (var combination : lines().stream().skip(2).toList()) {
            var count = countPossible(towels, combination, cache);
            sum = sum.add(count);
        }
        return sum;
    }

    private BigInteger countPossible(Set<String> towels, String combination, HashMap<String, BigInteger> cache) {
        BigInteger sum = cache.get(combination);
        if (sum == null) {
            sum = towels.stream()
                    .filter(combination::startsWith)
                    .map(towel -> countPossible(towels, combination.substring(towel.length()), cache))
                    .reduce(BigInteger.valueOf(0), BigInteger::add);
            cache.put(combination, sum);
        }
        return sum;
    }
}
