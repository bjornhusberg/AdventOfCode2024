import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ExpectedTestAnswer("4,6,3,5,6,3,5,2,1,0")
@VerifiedAnswer("7,5,4,3,4,5,3,4,6")
public class Day17a extends Day {

    @Override
    public Object solve() {
        int a = register("A");
        int b = register("B");
        int c = register("C");
        List<Integer> output = new ArrayList<>();
        List<Integer> program = program();
        for (int i = 0; i < program.size(); i = i + 2) {
            int opcode = program.get(i);
            int operand = program.get(i + 1);
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
                        i = operand - 2;
                    }
                    break;
                case 4:
                    b = b ^ c;
                    break;
                case 5:
                    output.add(operand & 7);
                    break;
                case 6:
                    b = a >> operand;
                    break;
                case 7:
                    c = a >> operand;
                    break;
            }
        }
        return String.join(",", output.stream().map(String::valueOf).toList());
    }

    protected List<Integer> program() {
        return lines(l -> l.startsWith("Program: "))
                .map(l -> l.split(": ")[1])
                .flatMap(l -> Arrays.stream(l.split(",")))
                .map(Integer::parseInt)
                .toList();
    }

    protected int register(String register) {
        return lines(l -> l.startsWith("Register " + register + ": ")).map(l -> Integer.parseInt(l.split(": ")[1])).findFirst().get();
    }
}
