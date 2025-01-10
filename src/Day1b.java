import java.util.List;

@ExpectedTestAnswer("31")
@VerifiedAnswer("22962826")
public class Day1b extends Day {

    @Override
    public Object solve() {
        List<Integer> other = intColumn(1).toList();
        return intColumn(0).reduce(0, (acc, i) ->
            (int) (acc + i * other.stream().filter(i::equals).count())
        );
    }
}
