import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ExpectedTestAnswer("9021")
@VerifiedAnswer("1467145")
public class Day15b extends Day {

    private static Point plusx = new Point(1, 0);

    @Override
    public Object solve() {
        String moves = lines(l -> !l.startsWith("#") && !l.isBlank()).collect(Collectors.joining());
        data(lines(l -> l.startsWith("#"))
                .map(l -> l.chars().boxed().map(c -> switch ((int) c) {
                    case 'O' -> "[]";
                    case '@' -> "@.";
                    case '.' -> "..";
                    case '#' -> "##";
                    default -> throw new RuntimeException();
                }))
                .map(s -> s.collect(Collectors.joining()).toCharArray())
                .toArray(char[][]::new));

        Point pos = allGridCoordinates().filter(p -> charAt(p) == '@').findFirst().get();
        setCharAt('.', pos);

        for (char move : moves.toCharArray()) {
            pos = tryMove(pos, parse(move));
        }

        return allGridCoordinates().filter(p -> charAt(p) == '[').map(p -> p.x() + 100*p.y()).reduce(0, Integer::sum);
    }

    private Point tryMove(Point pos, Point mV) {
        Point newPos = pos.add(mV);
        List<Point> boxes = new ArrayList<>();
        if (!canMove(newPos, mV, boxes)) {
            return pos;
        }
        boxes.forEach(box -> {
            setCharAt('.', box);
            setCharAt('.', box.add(plusx));
        });
        boxes.stream().map(box -> box.add(mV)).forEach(box -> {
            setCharAt('[', box);
            setCharAt(']', box.add(plusx));
        });
        move('.', newPos, mV);
        return newPos;
    }

    private void move(char o, Point pos, Point mV) {
        int object = charAt(pos);
        setCharAt(o, pos);
        if (object == '[') {
            move(']', pos.add(plusx).add(mV), mV);
            return;
        } else if (object == ']') {
            move('[', pos.add(plusx).add(mV), mV);
            return;
        } else if (object == '.') {
            return;
        }
        throw new RuntimeException("Unexpected: " + (char) object);
    }

    private boolean canMove(Point pos, Point mV, List<Point> boxes) {
        int object = charAt(pos);
        if (object == '#') {
            return false;
        }
        if (object == '.') {
            return true;
        }
        if (object == '[') {
            boxes.add(pos);
            if (mV.x() == 1) {
                return canMove(new Point(pos.x() + 2, pos.y()), mV, boxes);
            }
            if (mV.y() == 1) {
                return canMove(new Point(pos.x(), pos.y() + 1), mV, boxes) &&
                        canMove(new Point(pos.x() + 1, pos.y() + 1), mV, boxes);
            }
            if (mV.y() == -1) {
                return canMove(new Point(pos.x(), pos.y() - 1), mV, boxes) &&
                        canMove(new Point(pos.x() + 1, pos.y() - 1), mV, boxes);
            }
        }
        if (object == ']') {
            boxes.add(new Point(pos.x() - 1, pos.y()));
            if (mV.x() == -1) {
                return canMove(new Point(pos.x() - 2, pos.y()), mV, boxes);
            }
            if (mV.y() == 1) {
                return canMove(new Point(pos.x(), pos.y() + 1), mV, boxes) &&
                        canMove(new Point(pos.x() - 1, pos.y() + 1), mV, boxes);
            }
            if (mV.y() == -1) {
                return canMove(new Point(pos.x(), pos.y() - 1), mV, boxes) &&
                        canMove(new Point(pos.x() - 1, pos.y() - 1), mV, boxes);
            }
        }
        throw new RuntimeException("Unexpected: " + (char) object);
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
