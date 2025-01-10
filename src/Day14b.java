import java.util.List;

@ExpectedTestAnswer("Not implemented")
@VerifiedAnswer("7709")
public class Day14b extends Day14a {

    @Override
    public Object solve() {

        if (isTest()) {
            return "Not implemented";
        }

        long longestLine = 0;
        int result = 0;

        for (int i = 0; i < 10000; i++) {
            final int steps = i;
            List<Point> positions = robots().map(robot -> move(robot, steps)).toList();
            for (int x = 0; x < width(); x++) {
                int xpos = x;
                List<Integer> ypos = positions.stream().filter(p -> p.x() == xpos).map(Point::y).toList();
                int currentLine = 0;
                for (int y = 0; y < height(); y++) {
                    if (ypos.contains(y)) {
                        currentLine++;
                    } else {
                        if (longestLine < currentLine) {
                            longestLine = currentLine;
                            result = steps;
                        }
                        currentLine = 0;
                    }
                }
            }
        }

        return result;
    }
}
