
@ExpectedTestAnswer("11048")
@VerifiedAnswer("109496")
public class Day16a extends Day {

    @Override
    public Object solve() {
        long[][][] costs = new long[height()][width()][4];
        allGridCoordinates().forEach(p -> {
            for (int i = 0; i < 4; i++) {
                costs[p.y()][p.x()][i] = Long.MAX_VALUE;
            }
        });
        return findMoves(find('S'), new Point(1, 0), 0, costs);
    }

    private long findMoves(Point pos, Point dir, long cost, long[][][] costs) {
        int c = getRawCharAt(pos);
        int directionIndex = dir.x() == 0 ? dir.y() + 2 : dir.x() + 1;
        if (c == '#' || cost > costs[pos.y()][pos.x()][directionIndex]) {
            return Long.MAX_VALUE;
        }
        costs[pos.y()][pos.x()][directionIndex] = cost;

        if (c == 'E') {
            return cost;
        }

        Point cw = new Point(-dir.y(), dir.x());
        Point ccw = new Point(dir.y(), -dir.x());
        return Math.min(Math.min(
                findMoves(pos.add(dir), dir, cost + 1, costs),
                findMoves(pos.add(ccw), ccw, cost + 1001, costs)),
                findMoves(pos.add(cw), cw, cost + 1001, costs));
    }
}
