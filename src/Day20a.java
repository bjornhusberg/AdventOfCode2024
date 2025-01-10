import java.util.*;
import java.util.stream.Stream;

@ExpectedTestAnswer("44")
@VerifiedAnswer("1351")
public class Day20a extends Day {

    @Override
    public Object solve() {
        Point start = find('S');
        Point end = find('E');

        setRawCharAt('.', start);
        setRawCharAt('.', end);

        var fromEnd = distancesFrom(end);
        var fromStart = distancesFrom(start);

        int reference = fromEnd[start.x()][start.y()];

        return allGridCoordinates()
                .filter(v -> getRawCharAt(v) == '.')
                .flatMap(v -> getValidExits(v)
                        .map(exit ->
                                fromStart[v.x()][v.y()] +
                                fromEnd[exit.x()][exit.y()] +
                                Math.abs(v.x() - exit.x()) +
                                Math.abs(v.y() - exit.y())))
                .filter(distance -> isCheatCounted(reference, distance))
                .count();
    }

    protected boolean isCheatCounted(int reference, int cheat) {
        return cheat < (isTest() ? reference : reference - 99);
    }

    protected Stream<Point> getValidExits(Point entry) {
        return Point.range(-2, -2, 2, 2)
                .filter(p -> Math.abs(p.x()) + Math.abs(p.y()) == 2)
                .map(p -> p.add(entry))
                .filter(this::isValid)
                .filter(v -> getRawCharAt(v) == '.');
    }

    protected int[][] distancesFrom(Point target) {
        int[][] distances = new int[width()][height()];
        for (int x = 0; x < width(); x++) {
            Arrays.fill(distances[x], Integer.MAX_VALUE);
        }

        var queue = new ArrayDeque<Distance>();
        queue.add(new Distance(target, 0));

        while (!queue.isEmpty()) {
            Distance next = queue.poll();
            distances[next.pos().x()][next.pos().y()] = next.distance;
            queue.addAll(directions(next.pos(), false)
                    .filter(v -> getRawCharAt(v) != '#')
                    .filter(v -> distances[v.x()][v.y()] == Integer.MAX_VALUE)
                    .map(v -> new Distance(v, next.distance + 1)).toList());
        }
        return distances;
    }

    record Distance(Point pos, int distance) {
    }
}
