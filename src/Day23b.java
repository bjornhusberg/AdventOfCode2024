import java.util.*;
import java.util.stream.Collectors;

@ExpectedTestAnswer("co,de,ka,ta")
@VerifiedAnswer("as,co,do,kh,km,mc,np,nt,un,uq,wc,wz,yo")
public class Day23b extends Day {

    @Override
    public Object solve() {
        Map<String, List<String>> connections = new HashMap<>();
        lines().forEach(line -> {
            String[] connected = line.split("-");
            connections.compute(connected[0], (k, v) -> v == null ? new ArrayList<>() : v).add(connected[1]);
            connections.compute(connected[1], (k, v) -> v == null ? new ArrayList<>() : v).add(connected[0]);
        });

        Set<Set<String>> networks = new HashSet<>();
        boolean done = false;
        while (!done) {
            done = true;
            for (String computer1 : connections.keySet().stream().toList()) {
                for (String computer2 : connections.get(computer1)) {
                    if (networks.stream().anyMatch(network -> network.contains(computer1) && network.contains(computer2))) {
                        continue;
                    }

                    List<String> computer1connections = connections.get(computer1);
                    List<String> computer2connections = connections.get(computer2);

                    List<Set<String>> matching = networks.stream().filter(network ->
                            (network.contains(computer1) || computer1connections.containsAll(network)) &&
                            (network.contains(computer2) || computer2connections.containsAll(network))).toList();

                    if (matching.isEmpty()) {
                        HashSet<String> network = new HashSet<>();
                        network.add(computer1);
                        network.add(computer2);
                        networks.add(network);
                        done = false;
                    } else {
                        for (var network : matching) {
                            if (network.add(computer1)) {
                                done = false;
                            }
                            if (network.add(computer2)) {
                                done = false;
                            }
                        }
                    }
                }
            }
        }
        var maxNetwork = networks.stream().max(Comparator.comparingInt(Set::size)).orElseThrow();
        return maxNetwork.stream().sorted().collect(Collectors.joining(","));
    }
}
