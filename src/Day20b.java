import java.util.stream.Stream;

@ExpectedTestAnswer("285")
@VerifiedAnswer("966130")
public class Day20b extends Day20a {

    protected Stream<Point> getValidExits(Point entry) {
        return Point.range(-20, -20, 20, 20)
                .filter(p -> {
                    int dist = Math.abs(p.x()) + Math.abs(p.y());
                    return dist > 0 && dist <= 20;
                })
                .map(p -> p.add(entry))
                .filter(this::isValid)
                .filter(v -> getRawCharAt(v) == '.');
    }

    protected boolean isCheatCounted(int reference, int cheat) {
        return cheat <= (isTest() ? reference - 50: reference - 100);
    }
}
