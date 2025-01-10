import java.util.ArrayList;
import java.util.List;

@ExpectedTestAnswer("4")
@VerifiedAnswer("318")
public class Day2b extends Day2a {

    @Override
    public Object solve() {
        return (int) intColumns().filter(row -> isDampedSafe(row.toList())).count();
    }

    private boolean isDampedSafe(List<Integer> values) {
        if (isSafe(values)) {
            return true;
        }
        for (int i = 0; i < values.size(); i++) {
            List<Integer> newValues = new ArrayList<>(values.subList(0, i));
            newValues.addAll(values.subList(i + 1, values.size()));
            if (isSafe(newValues)) {
                return true;
            }
        }
        return false;
    }
}
