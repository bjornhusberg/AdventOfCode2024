import java.util.List;

@ExpectedTestAnswer("2")
@VerifiedAnswer("246")
public class Day2a extends Day {

    @Override
    public Object solve() {
        return (int) intColumns().filter(row -> isSafe(row.toList())).count();
    }

    public boolean isSafe(List<Integer> values) {
        return isSafeInner(values) || isSafeInner(values.reversed());
    }

    private boolean isSafeInner(List<Integer> values) {
        for (int i = 1; i < values.size(); i++) {
            int diff = values.get(i) - values.get(i - 1);
            if (diff < 1 || diff > 3) {
                return false;
            }
        }
        return true;
    }
}
