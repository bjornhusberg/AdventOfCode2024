
@ExpectedTestAnswer("161")
@VerifiedAnswer("174336360")
public class Day3a extends Day {

    @Override
    public Object solve() {
        return matcher("mul\\((\\d+),(\\d+)\\)").results()
            .map(match -> Integer.parseInt(match.group(1)) * Integer.parseInt(match.group(2))).reduce(0, Integer::sum);
    }
}
