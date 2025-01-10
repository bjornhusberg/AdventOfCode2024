import java.util.HashMap;
import java.util.Map;

@ExpectedTestAnswer("2024")
@VerifiedAnswer("55114892239566")
public class Day24a extends Day {

    @Override
    public Object solve() {
        Map<String, Boolean> values = new HashMap<>();

        var operations = getOperations();

        for (var operation : operations.entrySet()) {
            values.put(operation.getKey(), null);
            values.put(operation.getValue().left, null);
            values.put(operation.getValue().right, null);
        }

        parseInitValues(values);

        return getResult(values, operations);
    }

    protected Map<String, Operation> getOperations() {
        Map<String, Operation> operations  = new HashMap<>();

        var opMatcher = matcher("^(\\S*) (\\S*) (\\S*) -> (\\S*)$");
        while (opMatcher.find()) {
            operations.put(opMatcher.group(4), new Operation(opMatcher.group(1), opMatcher.group(2), opMatcher.group(3)));
        }
        return operations;
    }

    protected void parseInitValues(Map<String, Boolean> values) {
        var valueMatcher = matcher("^(\\S*): ([1,0])$");
        while (valueMatcher.find()) {
            values.put(valueMatcher.group(1), valueMatcher.group(2).equals("1"));
        }
    }

    protected long getResult(Map<String, Boolean> values, Map<String, Operation> operations) {
        boolean done = false;
        while (!done) {
            done = true;
            for (var operation : operations.entrySet()) {
                if (values.get(operation.getKey()) != null) {
                    continue;
                }
                var left = values.get(operation.getValue().left());
                var right = values.get(operation.getValue().right());
                if (left != null && right != null) {
                    boolean result = switch (operation.getValue().operation()) {
                        case "OR" -> left | right;
                        case "AND" -> left & right;
                        case "XOR" -> left ^ right;
                        default -> throw new RuntimeException();
                    };
                    values.put(operation.getKey(), result);
                    done = false;
                }
            }
        }
        var bits = values.entrySet().stream().filter(entry -> entry.getKey().startsWith("z"))
                .sorted(Map.Entry.comparingByKey())
                .map(Map.Entry::getValue)
                .toList();
        long result = 0;
        for (var bit : bits.reversed()) {
            result = result << 1 | (bit ? 1 : 0);
        }
        return result;

    }

    protected record Operation (String left, String operation, String right) {
    };
}
