import java.util.stream.Collectors;

@ExpectedTestAnswer("10092")
@VerifiedAnswer("1457740")
public class Day15a extends Day {

    @Override
    public Object solve() {
        String moves = lines(l -> !l.startsWith("#") && !l.isBlank()).collect(Collectors.joining());
        data(lines(l -> l.startsWith("#")).map(String::toCharArray).toArray(char[][]::new));

        Point pos = allGridCoordinates().filter(p -> charAt(p) == '@').findFirst().get();
        setCharAt('.', pos);

        for (char move : moves.toCharArray()) {
            pos = tryMove(pos, parse(move));
        }

        return allGridCoordinates().filter(p -> charAt(p) == 'O').map(p -> p.x() + 100*p.y()).reduce(0, Integer::sum);
    }

    private Point tryMove(Point pos, Point mV) {
        Point newPos = new Point(pos.x() + mV.x(), pos.y() + mV.y());
        int obj = charAt(newPos);
        if (obj == '#') {
            return pos;
        }
        if (obj == '.') {
            return newPos;
        }
        if (obj == 'O') {
            Point boxPos = newPos;
            while (charAt(boxPos) == 'O') {
                boxPos = new Point(boxPos.x() + mV.x(), boxPos.y() + mV.y());
            }
            if (charAt(boxPos) == '.') {
                setCharAt('O', boxPos);
                setCharAt('.', newPos);
                return newPos;
            }
            return pos;
        }
        throw new RuntimeException();
    }

    private Point parse(char move) {
        return switch (move) {
            case '^' -> new Point(0, -1);
            case 'v' -> new Point(0, 1);
            case '<' -> new Point(-1, 0);
            case '>' -> new Point(1, 0);
            default -> throw new IllegalStateException("Unexpected value: " + move);
        };
    }
}
