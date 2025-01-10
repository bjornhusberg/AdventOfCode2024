import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public abstract class Day {

    private static final Pattern namePattern = Pattern.compile("^Day(\\d+)([ab])$");

    private boolean testMode;
    private char[][] data;

    private Matcher nameMatcher() {
        Matcher matcher = namePattern.matcher(getClass().getSimpleName());
        if (!matcher.matches()) {
            throw new RuntimeException("Invalid class name: " + getClass().getSimpleName());
        }
        return matcher;
    }

    protected void clear(char c) {
        allGridCoordinates().forEach(p -> setCharAt(c, p));
    }

    protected void data(int width, int height) {
        this.data = new char[width][height];
    }

    protected void data(char[][] data) {
        this.data = data;
    }

    protected boolean isTest() {
        return testMode;
    }

    private int day() {
        return Integer.parseInt(nameMatcher().group(1));
    }

    private String part() {
        return nameMatcher().group(2);
    }

    public boolean verify() {
        try {
            String myTestAnswer = toString(test());
            String registeredTestAnswer = testAnswer();
            if (myTestAnswer == null || myTestAnswer.isEmpty()) {
                log("Not implemented");
                return false;
            }

            if (!myTestAnswer.equals(registeredTestAnswer)) {
                log("Test failed: " + myTestAnswer + " != " + registeredTestAnswer);
                return false;
            }

            String verifiedAnswer = verifiedAnswer();
            if (verifiedAnswer == null || verifiedAnswer.isEmpty()) {
                String myAnswer = toString(solve());
                log(myAnswer);
            } else {
                log(verifiedAnswer);
            }

            return true;
        } catch (Exception e) {
            log("Failed: " + e + " " + e.getMessage(), e);
            return false;
        }
    }

    private String toString(Object o) {
        return o == null ? null : o.toString();
    }

    private void log(String message) {
        log(message, null);
    }

    private void log(String message, Exception e ) {
        System.out.println("Day " + day() + part() + (isTest() ? " (test)" : "") + ": " + message);
        if (e != null) {
            e.printStackTrace();
        }
    }

    private String findFilename() {
        String basename = "src/" + (testMode ? "test" : "data") + "/" + day();
        String partName = basename + part() + ".data";
        if (Files.exists(Paths.get(partName))) {
            return partName;
        } else {
            return basename + ".data";
        }
    }

    protected char[][] data() {
        if (data == null) {
            try (BufferedReader reader = new BufferedReader(new FileReader(findFilename()))) {
                data = reader.lines().map(String::toCharArray).toArray(char[][]::new);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return data;
    }

    protected List<String> lines() {
        return Arrays.stream(data()).map(String::new).toList();
    }

    protected Stream<String> lines(Predicate<String> filter) {
        return lines().stream().filter(filter);
    }

    protected int height() {
        return lines().size();
    }

    protected int width() {
        return data()[0].length;
    }

    protected Point find(char c) {
        return allGridCoordinates().filter(p -> charAt(p) == c).findFirst().get();
    }

    protected Stream<Point> allGridCoordinates() {
        return Point.range(0, 0, width() - 1, height() - 1);
    }

    protected Stream<Point> directions(boolean diagonal) {
        return Point.range(-1, -1, 1, 1)
                .filter(p -> (p.x != 0 || p.y != 0) && (diagonal || p.x == 0 || p.y == 0));
    }

    protected Stream<Point> directions(Point from, boolean diagonal) {
        return directions(diagonal).map(p -> new Point(from.x + p.x, from.y + p.y)).filter(this::isValid);
    }

    protected boolean isValid(int x, int y) {
        return x >= 0 && x < width() && y >= 0 && y < height();
    }

    protected boolean isValid(Point pos) {
        return isValid(pos.x(), pos.y());
    }

    protected String line(int row) {
        return new String(data()[row]);
    }

    protected record Point(int x, int y) {
        Point add(Point other) {
            return new Point(x + other.x, y + other.y);
        }

        static Stream<Point> range(int fromX, int fromY, int toX, int toY) {
            return IntStream.range(fromX, toX + 1).boxed()
                    .flatMap(x -> IntStream.range(fromY, toY + 1).boxed()
                            .map(y -> new Point(x, y)));
        }
    }

    protected int charAt(Point pos) {
        return charAt(pos.x, pos.y);
    }

    protected int charAt(int x, int y) {
        return isValid(x, y) ? data()[y][x] : -1;
    }

    protected void setCharAt(char c, int x, int y) {
        if (isValid(x, y)) {
            data()[y][x] = c;
        }
    }

    protected void setRawCharAt(char c, Point pos) {
        data[pos.y][pos.x] = c;
    }

    protected void setRawCharAt(char c, int x, int y) {
        data[y][x] = c;
    }

    protected char getRawCharAt(Point pos) {
        return data[pos.y][pos.x];
    }

    protected char getRawCharAt(int x, int y) {
        return data[y][x];
    }

    protected void setCharAt(char c, Point pos) {
        setCharAt(c, pos.x, pos.y);
    }

    protected void print() {
        System.out.println("---");
        System.out.println(string());
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public Object test() {
        this.testMode = true;
        this.data = null;
        try {
            return solve();
        } finally {
            this.testMode = false;
            this.data = null;
        }
    }

    protected Stream<String> split(String regex) {
        return Arrays.stream(string().split(regex));
    }

    protected Matcher matcher(String regex) {
        return Pattern.compile(regex, Pattern.MULTILINE).matcher(string());
    }

    protected String string() {
        return String.join("\n", lines());
    }

    protected Stream<Stream<String>> columns() {
        return lines().stream().map(line -> Stream.of(line.split("\s+")));
    }

    protected Stream<Stream<Integer>> intColumns() {
        return columns().map(row -> row.map(Integer::parseInt));
    }

    protected Stream<Integer> intColumn(int index) {
        return intColumns().map(row -> row.toList().get(index));
    }

    public abstract Object solve();

    public String verifiedAnswer() {
        VerifiedAnswer annotation = this.getClass().getAnnotation(VerifiedAnswer.class);
        return annotation == null ? null : annotation.value();
    }

    public String testAnswer() {
        ExpectedTestAnswer annotation = this.getClass().getAnnotation(ExpectedTestAnswer.class);
        return annotation == null ? null : annotation.value();
    }
}
