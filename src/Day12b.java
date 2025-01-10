
@ExpectedTestAnswer("1206")
@VerifiedAnswer("904114")
public class Day12b extends Day12a {

    protected long value() {
        long borders = 0;
        long areas = 0;
        for (int x = 0; x < width(); x++) {
            for (int y = 0; y < height(); y++) {
                if (charAt(x, y) == '.') {
                    areas++;
                    if (charAt(x - 1, y) != '.' && (charAt(x, y - 1) != '.' || charAt(x - 1, y - 1) == '.')) {
                        borders++;
                    }
                    if (charAt(x + 1, y) != '.' && (charAt(x, y - 1) != '.' || charAt(x + 1, y - 1) == '.')) {
                        borders++;
                    }
                    if (charAt(x, y - 1) != '.' && (charAt(x - 1, y) != '.' || charAt(x - 1, y - 1) == '.')) {
                        borders++;
                    }
                    if (charAt(x, y + 1) != '.' && (charAt(x - 1, y) != '.' || charAt(x - 1, y + 1) == '.')) {
                        borders++;
                    }
                }
            }
        }
        return borders * areas;
    }
}
