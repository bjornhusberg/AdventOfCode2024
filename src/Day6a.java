
@ExpectedTestAnswer("41")
@VerifiedAnswer("4515")
public class Day6a extends Day {

    @Override
    public Object solve() {
        State state = findState();
        while (state != null) {
            setCharAt('X', state.x, state.y);
            state = move(state);
        }

        return string().chars().filter(c -> c == 'X').count();
    }

    protected State move(State state) {
        int nextX = state.x + state.direction.dx;
        int nextY = state.y + state.direction.dy;
        if (nextX < 0 || nextX >= width() || nextY < 0 || nextY >= height()) {
            return null;
        }
        if (charAt(nextX, nextY) == '#') {
            return new State(state.x, state.y, state.direction.turnRight());
        }
        return new State(nextX, nextY, state.direction);
    }

    protected State findState() {
        for (int x = 0; x < width(); x++) {
            for (int y = 0; y < height(); y++) {
                try {
                    return new State(x, y, Direction.parse(charAt(x, y)));
                } catch (IllegalArgumentException e) {
                    // ignore
                }
            }
        }
        throw new IllegalStateException();
    }

    public record State(int x, int y, Direction direction) {}

    public enum Direction {

        UP(0, -1), DOWN(0, 1), LEFT(-1, 0), RIGHT(1, 0);

        final int dx;
        final int dy;

        Direction(int dx, int dy) {
            this.dx = dx;
            this.dy = dy;
        }

        static Direction parse(int ch) {
            return switch(ch) {
                case '^' -> UP;
                case 'v' -> DOWN;
                case '<' -> LEFT;
                case '>' -> RIGHT;
                default -> throw new IllegalArgumentException();
            };
        }

        public Direction turnRight() {
            return switch(this) {
                case UP -> RIGHT;
                case DOWN -> LEFT;
                case LEFT -> UP;
                case RIGHT -> DOWN;
            };
        }
    }
}
