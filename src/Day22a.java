
@ExpectedTestAnswer("37327623")
@VerifiedAnswer("19927218456")
public class Day22a extends Day {

    @Override
    public Object solve() {
        return lines().stream().map(number -> {
            long secret = Long.parseLong(number);
            for (int i = 0; i < 2000; i++) {
                secret = next(secret);
            }
            return secret;
        }).reduce(0L, Long::sum);
    }

    public long next(long secret) {
        secret = mixAndPrune(secret, secret << 6);
        secret = mixAndPrune(secret, secret >> 5);
        secret = mixAndPrune(secret, secret << 11);
        return secret;
    }

    private long mixAndPrune(long secret, long value) {
        return (secret ^ value) % 16777216;
    }
}
