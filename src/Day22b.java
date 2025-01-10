import java.util.Arrays;

@ExpectedTestAnswer("23")
@VerifiedAnswer("2189")
public class Day22b extends Day22a {

    @Override
    public Object solve() {
        long[] totalValues = new long[130320];
        lines().forEach(number -> {
            long secret = Long.parseLong(number);
            Byte[] myValues = new Byte[totalValues.length];
            byte[] address = new byte[4];
            byte lastPrice = 0;
            for (int i = 0; i < 2000; i++) {
                byte price = (byte) (secret % 10);
                address[i % 4] = (byte) (price - lastPrice);
                lastPrice = price;
                if (i > 4) {
                    int index = toAddress(
                            address[ (i - 1) % 4],
                            address[ (i - 2) % 4],
                            address[ (i - 3) % 4],
                            address[ i % 4]);

                    if (myValues[index] == null) {
                        myValues[index] = price;
                    }
                }
                secret = next(secret);
            }
            for (int i = 0; i < totalValues.length; i++) {
                if (myValues[i] != null) {
                    totalValues[i] += myValues[i];
                }
            }
        });
        return Arrays.stream(totalValues).max().orElseThrow();
    }

    private int toAddress(int a, int b, int c, int d) {
        return (a + 9) + 19 * (b + 9) + 361 * (c + 9) + 6859 * (d + 9);
    }
}
