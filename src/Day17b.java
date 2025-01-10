import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ExpectedTestAnswer("117440")
@VerifiedAnswer("164278899142333")
public class Day17b extends Day17a {


    // 117440 - 57*2048 - 704
    @Override
    public Object solve() {
        long b = register("B");
        long c = register("C");
        List<Integer> program = program();

        int blockSizeBits = 10;
        int blockSize = 2 << blockSizeBits;

        HashMap<Long, Integer> seeds = new HashMap<>();
        for (long a = 0; a < blockSize; a++) {
            int size = run(a, b, c, program);
            if (size > 0) {
                seeds.put(a, size);
            }
        }

        int minimumSize = 2;
        int shift = blockSizeBits;
        while (!seeds.isEmpty()) {
            for (Map.Entry<Long, Integer> seed : new ArrayList<>(seeds.entrySet())) {
                seeds.remove(seed.getKey());
                for (long i = 0; i < 8; i++) {
                    long a = seed.getKey() | (i << shift);

                    int size = run(a, b, c, program);
                    if (size == program().size()) {
                        return a;
                    }
                    if (size >= minimumSize) {
                        seeds.put(a, size);
                    }
                }
            }
            minimumSize++;
            shift = shift + 3;
        }

        throw new RuntimeException("No solution found");
    }

    protected int run(long a, long b, long c, List<Integer> program) {
        int outputSize = 0;
        for (int i = 0; i < program.size(); i = i + 2) {

            int opcode = program.get(i);
            long operand = program.get(i + 1);
            if (opcode != 1 && opcode != 3) {
                if (operand == 4) {
                    operand = a;
                } else if (operand == 5) {
                    operand = b;
                } else if (operand == 6) {
                    operand = c;
                }
            }

            switch (opcode) {
                case 0:
                    a = a >> operand;
                    break;
                case 1:
                    b = b ^ operand;
                    break;
                case 2:
                    b = operand & 7;
                    break;
                case 3:
                    if (a != 0) {
                        i = (int) operand - 2;
                    }
                    break;
                case 4:
                    b = b ^ c;
                    break;
                case 5:
                    if (program.get(outputSize) != (operand & 7)) {
                        return outputSize;
                    }
                    outputSize++;
                    break;
                case 6:
                    b = a >> operand;
                    break;
                case 7:
                    c = a >> operand;
                    break;
            }
        }
        return outputSize;
    }

    protected List<Integer> program() {
        if (isTest()) {
            return List.of(0, 3, 5, 4, 3, 0);
        }
        return super.program();
    }
}
