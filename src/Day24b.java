import java.util.*;
import java.util.stream.Collectors;

@ExpectedTestAnswer("skipped")
@VerifiedAnswer("cdj,dhm,gfm,mrb,qjd,z08,z16,z32")

public class Day24b extends Day24a {

    @Override
    public Object solve() {
        if (isTest()) {
            return "skipped";
        }
        Map<String, Operation> operations = getOperations();
        List<String> swapped = new ArrayList<>();

        String[] xXORy = new String[45];
        String[] xANDy = new String[45];
        for (int i = 0; i < 45; i++) {
            xXORy[i] = findOperation("XOR", name('x', i), name('y', i), operations).orElseThrow();
            xANDy[i] = findOperation("AND", name('x', i), name('y', i), operations).orElseThrow();
        }

        if (!"z00".equals(xXORy[0])) {
            swap("z00", xXORy[0], operations, swapped);
        }

        for (int i = 1; i < 45; i++) {
            Optional<String> xor = findOperation("XOR", xXORy[i], null, operations);
            if (xor.isPresent()) {
                if (!name('z', i).equals(xor.get())) {
                    swap(name('z', i), xor.get(), operations, swapped);
                }
            } else {
                Operation zOp = operations.get(name('z', i));
                swap(zOp.right(), xXORy[i], operations, swapped);
            }
    /*
            Zn = (Xn ⊕ Yn) ⊕ Cn-1

            Cn = (Xn * Yn) + (Cn-1 * (Xn ⊕ Yn))

            with C0 = (X0 * Y0)
*/

        }
        return swapped.stream().sorted().collect(Collectors.joining(","));
    }

    private void swap(String wire1, String wire2, Map<String, Operation> operations, List<String> swapped) {
        Operation tmp = operations.get(wire2);
        operations.put(wire2, operations.get(wire1));
        operations.put(wire1, tmp);
        swapped.add(wire1);
        swapped.add(wire2);
    }

    private Optional<String> findOperation(String opType, String op1, String op2, Map<String, Operation> operations) {
        return operations.entrySet().stream()
                .filter(entry ->
                        entry.getValue().operation().equals(opType) &&
                                hasOperator(entry.getValue(), op1) &&
                                hasOperator(entry.getValue(), op2))
                .map(Map.Entry::getKey)
                .findFirst();
    }

    private boolean hasOperator(Operation op, String operator) {
        return operator == null || op.right().equals(operator) || op.left().equals(operator);
    }

    private String name(char c, int n) {
        return c + (n < 10 ? "0" : "") + n;
    }
}
