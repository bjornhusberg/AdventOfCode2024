import java.util.ArrayList;
import java.util.Arrays;

@ExpectedTestAnswer("55312")
@VerifiedAnswer("218956")
public class Day11a extends Day {

    @Override
    public Object solve() {
        var numbers = new ArrayList<>(Arrays.stream(string().split("\s+")).toList());
        for (int i = 0; i < 25; i++) {
            for (int j = 0; j < numbers.size(); j++) {
                String number = numbers.get(j);
                if (number.equals("0")) {
                    numbers.set(j, "1");
                } else if (number.length() % 2 == 0) {
                    String number1 = Long.toString(Long.parseLong(number.substring(0, number.length() / 2)));
                    String number2 = Long.toString(Long.parseLong(number.substring(number.length() / 2)));
                    numbers.set(j, number1);
                    numbers.add(j + 1, number2);
                    j++;
                } else {
                    numbers.set(j, Long.toString(Long.parseLong(number) * 2024));
                }
            }
        }

        return numbers.size();
    }
}
