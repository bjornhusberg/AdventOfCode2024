import java.util.ArrayList;
import java.util.List;

@ExpectedTestAnswer("2858")
@VerifiedAnswer("6389911791746")
public class Day9b extends Day {

    @Override
    public Object solve() {
        List<Block> blocks = expand(string());
        compress(blocks);
        return checksum(blocks);
    }

    private long checksum(List<Block> blocks) {
        long checksum = 0;
        int index = 0;
        for (Block block : blocks) {
            if (block.id != -1) {
                for (int i = index; i < index + block.length; i++) {
                    checksum += (long) i * block.id;
                }
            }
            index += block.length;
        }
        return checksum;
    }

    protected List<Block> expand(String string) {
        List<Block> blocks = new ArrayList<>();
        for (int i = 0; i < string.length(); i++) {
            int id = i % 2 == 0 ? i / 2 : -1;
            int length = string.charAt(i) - '0';
            blocks.add(new Block(id, length));
        }
        return blocks;
    }

    protected void compress(List<Block> blocks) {
        for (Block block : new ArrayList<>(blocks.reversed())) {
            if (block.id != -1) {
                compress(block, blocks);
            }
        }
    }

    private void compress(Block block, List<Block> blocks) {
        for (Block replace : blocks) {
            if (replace == block) {
                return;
            }
            if (replace.id == -1 && replace.length >= block.length) {
                int oldIndex = blocks.indexOf(block);
                blocks.remove(block);
                blocks.add(oldIndex, new Block(-1, block.length));
                int index = blocks.indexOf(replace);
                blocks.add(index, block);
                blocks.remove(replace);
                blocks.add(index + 1, new Block(-1, replace.length - block.length));
                return;
            }
        }
    }

    record Block(int id, int length) {}
}
