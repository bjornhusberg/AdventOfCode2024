import java.util.ArrayList;
import java.util.List;

@ExpectedTestAnswer("3")
@VerifiedAnswer("2835")
public class Day25a extends Day {

    @Override
    public Object solve() {
        List<byte[]> locks = new ArrayList<>();
        List<byte[]> keys = new ArrayList<>();
        for (int i = 0; i < height(); i = i + 8) {
            byte[] pins = new byte[5];
            boolean isKey = !line(i).startsWith("#");
            for (int height = 0; height < 5; height++) {
                for (int pin = 0; pin < 5; pin++) {
                    if (charAt(pin, i + height + 1) == '#' ^ isKey) {
                        pins[pin]++;
                    }
                }
            }
            if (isKey) {
                keys.add(pins);
            } else {
                locks.add(pins);
            }
        }
        return keys.stream().map(key ->
            locks.stream().filter(lock -> fits(lock, key)).count()
        ).reduce(0L, Long::sum);
    }

    private boolean fits(byte[] lock, byte[] key) {
        for (int i = 0; i < 5; i++) {
            if (lock[i] > key[i]) {
                return false;
            }
        }
        return true;
    }
}
