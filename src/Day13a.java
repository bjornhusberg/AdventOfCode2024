
@ExpectedTestAnswer("480")
@VerifiedAnswer("36758")
public class Day13a extends Day {

    @Override
    public Object solve() {
        var matcher = matcher("Button A: X\\+([0-9]+), Y\\+([0-9]+)\nButton B: X\\+([0-9]+), Y\\+([0-9]+)\nPrize: X=([0-9]+), Y=([0-9]+)");
        long cost = 0;
        while (matcher.find()) {
            long ax = Integer.parseInt(matcher.group(1));
            long ay = Integer.parseInt(matcher.group(2));
            long bx = Integer.parseInt(matcher.group(3));
            long by = Integer.parseInt(matcher.group(4));
            long px = Integer.parseInt(matcher.group(5));
            long py = Integer.parseInt(matcher.group(6));
            cost += findCost(ax, ay, bx, by, px, py);
        }
        return cost;
    }

    protected long findCost(long ax, long ay, long bx, long by, long px, long py) {
        for (long b = px / bx; b > 0; b--) {
            for (long a = 0; a < px / ax; a++) {
                if (a * ax + b * bx == px && a * ay + b * by == py) {
                    return 3 * a + b;
                }
            }
        }
        return 0;
    }
}
