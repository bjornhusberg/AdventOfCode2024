
@ExpectedTestAnswer("1930")
@VerifiedAnswer("1461752")
public class Day12a extends Day {

    @Override
    public Object solve() {
        long result = 0;
        for (int x = 0; x < width(); x++) {
            for (int y = 0; y < height(); y++) {
                int start = charAt(x, y);
                if (start == ' ') {
                    continue;
                }
                setCharAt('.', x, y);
                while (grow(start));
                result += value();
                clear();
            }
        }
        return result;
    }

    private boolean grow(int c) {
        boolean result = false;
        for (int x = 0; x < width(); x++) {
            for (int y = 0; y < height(); y++) {
                if (charAt(x, y) == c) {
                    if (charAt(x - 1, y) == '.' || charAt(x + 1, y) == '.' || charAt(x, y - 1) == '.' || charAt(x, y + 1) == '.') {
                        setCharAt('.', x, y);
                        result = true;
                    }
                }
            }
        }
        return result;
    }

    protected long value() {
        long borders = 0;
        long areas = 0;
        for (int x = 0; x < width(); x++) {
            for (int y = 0; y < height(); y++) {
                if (charAt(x, y) == '.') {
                    areas++;
                    if (charAt(x - 1, y) != '.') {
                        borders++;
                    }
                    if (charAt(x + 1, y) != '.') {
                        borders++;
                    }
                    if (charAt(x, y - 1) != '.') {
                        borders++;
                    }
                    if (charAt(x, y + 1) != '.') {
                        borders++;
                    }
                }
            }
        }
        return borders * areas;
    }


    private void clear() {
        for (int x = 0; x < width(); x++) {
            for (int y = 0; y < height(); y++) {
                if (charAt(x, y) == '.') {
                    setCharAt(' ', x, y);
                }
            }
        }
    }
}
