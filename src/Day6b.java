import java.util.ArrayList;
import java.util.List;

@ExpectedTestAnswer("6")
@VerifiedAnswer("1309")
public class Day6b extends Day6a {

    @Override
    public Object solve() {
        State state = findState();
        int count = 0;
        for (int x = 0; x < width(); x++) {
            for (int y = 0; y < height(); y++) {
                if (charAt(x, y) != '#') {
                    setCharAt('#', x, y);
                    if (isLooping(state)) {
                        count++;
                    }
                    setCharAt('.', x, y);
                }
            }
        }
        return count;
    }

    private boolean isLooping(State state) {
        List<State> previousStates = new ArrayList<>();
        while (state != null) {
            if (previousStates.contains(state)) {
                return true;
            }
            previousStates.add(state);
            state = move(state);
        }
        return false;
    }
}
