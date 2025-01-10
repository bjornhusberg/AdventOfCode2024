
@ExpectedTestAnswer("34")
@VerifiedAnswer("1233")
public class Day8b extends Day {

    @Override
    public Object solve() {
        boolean[][] antinodes = new boolean[width()][height()];
        return allGridCoordinates().map(pos -> {
            int count = 0;
            int ch = charAt(pos);
            if (ch != '.') {
                for (int xx = 0; xx < width(); xx++) {
                    for (int yy = 0; yy < height(); yy++) {
                        if ((xx != pos.x() || yy != pos.y()) && charAt(xx, yy) == ch) {
                            count += setAntinodes(antinodes, pos, xx - pos.x(), yy - pos.y(), 0);
                            count += setAntinodes(antinodes, pos, pos.x() - xx, pos.y() - yy, 0);
                        }
                    }
                }
            }
            return count;
        }).reduce(0, Integer::sum);
    }

    private int setAntinodes(boolean[][] antinodes, Point pos, int dx, int dy, int count) {
        if (!isValid(pos)) {
            return count;
        }
        if (!antinodes[pos.x()][pos.y()]) {
            antinodes[pos.x()][pos.y()] = true;
            count++;
        }
        return setAntinodes(antinodes, new Point(pos.x() + dx, pos.y() + dy), dx, dy, count);
    }
}
