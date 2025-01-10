import java.util.ArrayDeque;
import java.util.List;

@ExpectedTestAnswer("22")
@VerifiedAnswer("374")
public class Day18a extends Day {

    @Override
    public Object solve() {
        List<Point> blocks = lines().stream().map(l -> l.split(","))
                .map(c -> new Point(Integer.parseInt(c[0]), Integer.parseInt(c[1])))
                .limit(isTest() ? 12 : 1024)
                .toList();

        int length = isTest() ? 7 : 71;
        data(length, length);

        allGridCoordinates().forEach(coord -> setRawCharAt('.', coord));
        blocks.forEach(block -> setRawCharAt('#', block));

        Point start = new Point(0, 0);
        Point goal = new Point(length - 1, length - 1);

        var queue = new ArrayDeque<Record>();
        queue.add(new Record(start, null));

        while (!queue.isEmpty()) {
            Record next = queue.poll();
            if (next.self().equals(goal)) {
                return next.distance();
            }
            if (getRawCharAt(next.self()) != '.') {
                continue;
            }
            setRawCharAt('*', next.self());
            queue.addAll(directions(next.self(), false)
                    .filter(child -> !blocks.contains(child))
                    .map(child -> new Record(child, next))
                    .toList());
        }

        throw new RuntimeException("No route found");
    }

    public record Record(Point self, Record parent) {

        public int distance() {
            if (parent == null) {
                return 0;
            }
            return 1 + parent.distance();
        }
    }
}
