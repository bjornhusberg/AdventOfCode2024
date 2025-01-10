
@ExpectedTestAnswer("9")
@VerifiedAnswer("1835")
public class Day4b extends Day {

    @Override
    public Object solve() {
        int count = 0;
        for (int x = 1; x < width() - 1; x++) {
            for (int y = 1; y < height() - 1; y++) {
                if (charAt(x, y) == 'A' &&
                    isMs(charAt(x - 1, y - 1), charAt(x + 1, y + 1)) &&
                    isMs(charAt(x + 1, y - 1), charAt(x - 1, y + 1))) {
                    count++;
                }
            }
        }
        return count;
    }

    private boolean isMs(int c1, int c2) {
        return c1 == 'M' && c2 == 'S' || c1 == 'S' && c2 == 'M';
    }
}
