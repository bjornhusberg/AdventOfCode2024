import java.util.regex.Pattern;
import java.util.stream.Collectors;

@ExpectedTestAnswer("48")
@VerifiedAnswer("88802350")
public class Day3b extends Day {

    @Override
    public Object solve() {
        String data = split("do\\(\\)")
                .map(s -> s.split("don't\\(\\)")[0])
                .collect(Collectors.joining());
        return Pattern.compile("mul\\((\\d+),(\\d+)\\)").matcher(data).results()
            .map(match -> Integer.parseInt(match.group(1)) * Integer.parseInt(match.group(2))).reduce(0, Integer::sum);
    }
}
