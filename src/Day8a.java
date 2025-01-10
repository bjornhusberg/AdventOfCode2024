
@ExpectedTestAnswer("14")
@VerifiedAnswer("327")
public class Day8a extends Day {

    @Override
    public Object solve() {
        int count = 0;
        boolean[][] antinodes = new boolean[width()][height()];
        for (int x = 0; x < width(); x++) {
            for (int y = 0; y < height(); y++) {
                int ch = charAt(x, y);
                if (ch != '.') {
                    for (int xx = 0; xx < width(); xx++) {
                        for (int yy = 0; yy < height(); yy++) {
                            if ((xx != x || yy != y) && charAt(xx, yy) == ch) {
                                count += setAntinode(antinodes, xx + xx - x, yy + yy - y);
                                count += setAntinode(antinodes, x + x - xx, y + y - yy);
                            }
                        }
                    }
                }
            }
        }
        return count;
    }

    private int setAntinode(boolean[][] antinodes, int x, int y) {
        if (x >= 0 && x < width() && y >= 0 && y < height() && !antinodes[x][y]) {
            antinodes[x][y] = true;
            return 1;
        }
        return 0;
    }
}
