
@ExpectedTestAnswer("875318608908")
@VerifiedAnswer("76358113886726")
public class Day13b extends Day13a {

    protected long findCost(long ax, long ay, long bx, long by, long px, long py) {
        long ex = 10000000000000L;
        px = ex + px;
        py = ex + py;

        long best = (long) ((double) ex*ay-ex*ax)/(bx*ay-by*ax);
        long aest = (long) ((double) ex - best * bx) / ax;

        for (long b = best + 1000; b > best - 1000; b--) {
            for (long a = aest - 1000; a < aest + 1000; a++) {
                if (a * ax + b * bx == px && a * ay + b * by == py) {
                    return 3 * a + b;
                }
            }
        }
        return 0;
    }
}
