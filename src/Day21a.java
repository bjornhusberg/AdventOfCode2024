import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@ExpectedTestAnswer("126384")
@VerifiedAnswer("94284")
public class Day21a extends Day {

    char[][] numpad = new char[][] {
            {'7', '8', '9'},
            {'4', '5', '6'},
            {'1', '2', '3'},
            {' ', '0', 'A'}};

    char[][] arrowpad = new char[][] {
            {' ', '^', 'A'},
            {'<', 'v', '>'}};

    @Override
    public Object solve() {
        return lines().stream().map(code -> {
            var stack = new ArrayList<State>();
            stack.add(initNumpad());
            for (int i = 0; i < 2; i++) {
                stack.add(initArrowpad());
            }

            int complexity = Integer.parseInt(code.substring(0, code.length() - 1));
            List<String> sequences = calculate(code, stack);

            String seq = sequences.stream().min(Comparator.comparingInt(String::length)).orElseThrow();
            return seq.length() * complexity;
        }).reduce(0, Integer::sum);
    }

    protected State initNumpad() {
        return new State(numpad, new Point(2, 3));
    }

    protected State initArrowpad() {
        return new State(arrowpad, new Point(2, 0));
    }

    protected List<String> calculate(String seq, List<State> stack) {
        var sequences = new ArrayList<String>();
        sequences.add(seq);
        for (State state : stack) {
            Point p = state.position;
            var newSequences = new ArrayList<String>();
            for (String sequence : sequences) {
                state.position = p;
                newSequences.addAll(getSequences(state, sequence));
            }
            sequences = newSequences;
        }
        return sequences;
    }

    private List<String> getSequences(State state, String sequence) {
        List<String> result = new ArrayList<>();
        result.add("");
        for (char c : sequence.toCharArray()) {
            List<String> newResult = new ArrayList<>();
            for (String sequenceTo : state.sequencesTo(c)) {
                newResult.addAll(result.stream().map(s -> s + sequenceTo + "A").toList());
            }
            result = newResult;
        }
        return result;
    }

    protected class State {

        protected char[][] pad;
        protected Point position;

        public State(char[][] pad, Point position) {
            this.pad = pad;
            this.position = position;
        }

        public List<String> sequencesTo(char c) {
            Point to = find(c);

            int vert = to.y() - position.y();
            int horiz = to.x() - position.x();

            char[] vertBuf = new char[Math.abs(vert)];
            char[] horizBuf = new char[Math.abs(horiz)];
            Arrays.fill(vertBuf, vert > 0 ? 'v' : '^');
            Arrays.fill(horizBuf, horiz > 0 ? '>' : '<');

            if (vert == 0 || horiz == 0) {
                position = to;
                return List.of(new String(vertBuf) + new String(horizBuf));
            }

            if (pad[position.y()][to.x()] == ' ') {
                position = to;
                return List.of(new String(vertBuf) + new String(horizBuf));
            }

            if (pad[to.y()][position.x()] == ' ') {
                position = to;
                return List.of(new String(horizBuf) + new String(vertBuf));
            }

            position = to;
            return List.of(new String(vertBuf) + new String(horizBuf),
                    new String(horizBuf) + new String(vertBuf));
        }

        public Point find(char c) {
            for (int y = 0; y < pad.length; y++) {
                for (int x = 0; x < pad[y].length; x++) {
                    if (pad[y][x] == c) {
                        return new Point(x, y);
                    }
                }
            }
            throw new RuntimeException();
        }
    }
}
