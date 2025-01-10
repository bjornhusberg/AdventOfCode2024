
@ExpectedTestAnswer("81")
@VerifiedAnswer("1619")
public class Day10b extends Day10a {

    protected int findTrails(Point pos) {
        return charAt(pos) == '9' ? 1 : directions(pos, false)
                .filter(p -> charAt(p) == charAt(pos) + 1)
                .map(this::findTrails)
                .reduce(0, Integer::sum);
    }
}
