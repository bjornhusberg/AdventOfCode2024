import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ExpectedTestAnswer("7")
@VerifiedAnswer("1149")
public class Day23a extends Day {

    @Override
    public Object solve() {
        Map<String, List<String>> connections = new HashMap<>();
        lines().forEach(line -> {
            String[] connected = line.split("-");
            connections.compute(connected[0], (k, v) -> v == null ? new ArrayList<>() : v).add(connected[1]);
            connections.compute(connected[1], (k, v) -> v == null ? new ArrayList<>() : v).add(connected[0]);
        });

        Set<String> networks = new HashSet<>();
        for (String computer1 : connections.keySet().stream().filter(c -> c.startsWith("t")).toList()) {
            for (String computer2 : connections.get(computer1)) {
                for (String computer3 : connections.get(computer1)) {
                    if (connections.get(computer2).contains(computer3)) {
                        networks.add(Stream.of(computer1, computer2, computer3).sorted().collect(Collectors.joining(",")));
                    }
                }
            }
        }
        return networks.size();
    }
}
