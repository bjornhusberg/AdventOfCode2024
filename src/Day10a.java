import java.util.HashSet;
import java.util.Set;

@ExpectedTestAnswer("36")
@VerifiedAnswer("737")
public class Day10a extends Day {

    @Override
    public Object solve() {
        return allGridCoordinates().map(pos -> charAt(pos) == '0' ? findTrails(pos) : 0).reduce(0, Integer::sum);
    }

    protected int findTrails(Point pos) {
        Set<Point> peaks = new HashSet<>();
        findTrails(pos, peaks);
        return peaks.size();
    }

    private void findTrails(Point pos, Set<Point> peaks) {
        if (charAt(pos) == '9') {
            peaks.add(pos);
        } else {
            directions(pos, false)
                    .filter(p -> charAt(p) == charAt(pos) + 1)
                    .forEach(step -> findTrails(step, peaks));
        }
    }
}
