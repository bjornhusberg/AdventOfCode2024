import java.util.ArrayList;
import java.util.List;

@ExpectedTestAnswer("1928")
@VerifiedAnswer("6356833654075")
public class Day9a extends Day {

    @Override
    public Object solve() {
        List<Integer> blocks = expand(string());
        compress(blocks);
        return checksum(blocks);
    }

    protected long checksum(List<Integer> blocks) {
        long checksum = 0;
        for (var i = 0; i < blocks.size(); i++) {
            if (blocks.get(i) != -1) {
                checksum += (long) i * blocks.get(i);
            }
        }
        return checksum;
    }

    protected List<Integer> expand(String string) {
        List<Integer> blocks = new ArrayList<>();
        for (int i = 0; i < string.length(); i++) {
            int id = i % 2 == 0 ? i / 2 : -1;
            int length = (string.charAt(i) - '0');
            for (int block = 0; block < length; block++) {
                blocks.add(id);
            }
        }
        return blocks;
    }

    protected void compress(List<Integer> blocks) {
        int end = blocks.size() - 1;
        for (int start = 0; start <= end; start++) {
            int id = blocks.get(start);
            if (id == -1) {
                while (end > start && blocks.get(end) == -1) end--;
                blocks.set(start, blocks.get(end));
                blocks.set(end, -1);
            }
        }
    }
}
