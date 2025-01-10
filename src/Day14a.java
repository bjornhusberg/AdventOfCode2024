import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

@ExpectedTestAnswer("12")
@VerifiedAnswer("230435667")
public class Day14a extends Day {

    @Override
    public Object solve() {
        List<Point> robots = robots().map(robot -> move(robot, 100)).toList();
        int midx = width() / 2;
        int midy = height() / 2;
        long q1 = robots.stream().filter(robot -> robot.x() < midx && robot.y() < midy).count();
        long q2 = robots.stream().filter(robot -> robot.x() < midx && robot.y() > midy).count();
        long q3 = robots.stream().filter(robot -> robot.x() > midx && robot.y() < midy).count();
        long q4 = robots.stream().filter(robot -> robot.x() > midx && robot.y() > midy).count();
        return q1*q2*q3*q4;
    }

    protected Stream<Robot> robots() {
        Pattern pattern = Pattern.compile("p=(\\d+),(\\d+) v=(-?\\d+),(-?\\d+)");
        return lines().stream().map(pattern::matcher)
                .map(matcher -> new Robot(parseVector(matcher, 1), parseVector(matcher, 3)));
    }

    @Override
    protected int width() {
        return isTest() ? 11 : 101;
    }

    @Override
    protected int height() {
        return isTest() ? 7 : 103;
    }

    protected Point move(Robot robot, int steps) {
        long x = (robot.pos().x() + (long) robot.vel().x() * steps) % width();
        long y = (robot.pos().y() + (long) robot.vel().y() * steps) % height();
        return new Point((int) (x < 0 ? x + width() : x), (int) (y < 0 ? y + height() : y));
    }

    protected Point parseVector(Matcher matcher, int group) {
        if (matcher.matches()) {
            return new Point(parseInt(matcher, group), parseInt(matcher, group + 1));
        }
        throw new RuntimeException();
    }

    private int parseInt(Matcher matcher, int group) {
        return Integer.parseInt(matcher.group(group));
    }

    protected record Robot(Point pos, Point vel) {
    }
}
