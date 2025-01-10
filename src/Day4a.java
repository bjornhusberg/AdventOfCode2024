
@ExpectedTestAnswer("18")
@VerifiedAnswer("2434")
public class Day4a extends Day {

    @Override
    public Object solve() {
        return allGridCoordinates().filter(pos -> charAt(pos) == 'X')
                .map(pos -> directions(true)
                        .filter(dir -> hasWord(pos, dir.x(), dir.y())).count())
                .reduce(0L, Long::sum);
    }

    private final char[] mas = "MAS".toCharArray();
    private boolean hasWord(Point pos, int dx, int dy) {
        for (char c : mas) {
            pos = new Point(pos.x() + dx, pos.y() + dy);
            if (charAt(pos) != c) {
                return false;
            }
        }
        return true;
    }
}
