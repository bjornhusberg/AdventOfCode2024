import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@ExpectedTestAnswer("65601038650482")
@VerifiedAnswer("259593838049805")
public class Day11b extends Day {

    @Override
    public Object solve() {
        var stones = split("\s+").map(Long::parseLong).collect(Collectors.toMap(k -> k, _ -> 1L, Long::sum));
        for (int i = 0; i < 75; i++) {
            var newStones = new HashMap<Long, Long>();
            for (var entry : new ArrayList<>(stones.entrySet())) {
                for (long newStone : getStones(entry.getKey())) {
                    newStones.put(newStone, newStones.getOrDefault(newStone, 0L) + entry.getValue());
                }
            }
            stones = newStones;
        }
        return stones.values().stream().reduce(0L, Long::sum);
    }

    private List<Long> getStones(long stone) {
        if (stone == 0) {
            return List.of(1L);
        }
        String string = Long.toString(stone);
        int half = string.length() / 2;
        if (half + half == string.length()) {
            return List.of(Long.parseLong(string.substring(0, half)), Long.parseLong(string.substring(half)));
        }
        return List.of(stone * 2024);
    }
}
