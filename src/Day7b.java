import java.util.List;

@ExpectedTestAnswer("11387")
@VerifiedAnswer("271691107779347")
public class Day7b extends Day7a {

    protected boolean hasResult(long expectedResult, long result, Long next, List<Long> rest) {
        return hasResult(expectedResult, result + next, rest) ||
                hasResult(expectedResult, result * next, rest) ||
                hasResult(expectedResult, Long.parseLong(result + "" + next), rest);
    }
}
