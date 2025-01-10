import java.util.List;

@ExpectedTestAnswer("11")
@VerifiedAnswer("2192892")
public class Day1a extends Day {

    @Override
    public Object solve() {
        List<Integer> first = intColumn(0).sorted().toList();
        List<Integer> second = intColumn(1).sorted().toList();
        int distance = 0;
        for (int i = 0; i < first.size(); i++) {
            distance += Math.abs(first.get(i) - second.get(i));
        }
        return distance;
    }
}
