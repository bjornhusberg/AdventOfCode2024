import java.util.ArrayDeque;
import java.util.List;

@ExpectedTestAnswer("6,1")
@VerifiedAnswer("30,12")
public class Day18b extends Day18a {

    @Override
    public Object solve() {
        List<Point> blocks = lines().stream().map(l -> l.split(","))
                .map(c -> new Point(Integer.parseInt(c[0]), Integer.parseInt(c[1])))
                .toList();

        int length = isTest() ? 7 : 71;
        data(length, length);

        int min = 0;
        int max = blocks.size();

        while (min + 1< max) {
            int divider = (max + min) >> 1;
            if (isReachable(blocks.subList(0, divider))) {
                min = divider;
            } else {
                max = divider;
            }
        }
        Point nextBlock = blocks.get(min);
        return nextBlock.x() + "," + nextBlock.y();
    }

    private boolean isReachable(List<Point> blocks) {
        Point start = new Point(0, 0);
        Point goal = new Point(width() - 1, height() - 1);

        allGridCoordinates().forEach(coord -> setRawCharAt('.', coord));
        blocks.forEach(block -> setRawCharAt('#', block));

        var queue = new ArrayDeque<Record>();
        queue.add(new Record(start, null));

        while (!queue.isEmpty()) {
            Record next = queue.poll();
            if (next.self().equals(goal)) {
                return true;
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

        return false;
    }
}
