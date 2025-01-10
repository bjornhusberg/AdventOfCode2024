import java.util.Arrays;
import java.util.List;

@ExpectedTestAnswer("3749")
@VerifiedAnswer("945512582195")
public class Day7a extends Day {

    @Override
    public Object solve() {
        return lines().stream().map(line -> {
            String[] keyval = line.split(":");
            long result = Long.parseLong(keyval[0]);
            List<Long> parts = Arrays.stream(keyval[1].trim().split(" "))
                    .map(Long::parseLong).toList();
            return hasResult(result, parts.getFirst(), parts.subList(1, parts.size())) ? result : 0;
        }).reduce(0L, Long::sum);
    }

    protected boolean hasResult(long expectedResult, long result, List<Long> parts) {
        if (parts.isEmpty()) {
            return result == expectedResult;
        }
        return hasResult(expectedResult, result, parts.getFirst(), parts.subList(1, parts.size()));
    }

    protected boolean hasResult(long expectedResult, long result, Long next, List<Long> rest) {
        return hasResult(expectedResult, result + next, rest) ||
                hasResult(expectedResult, result * next, rest);
    }
}
