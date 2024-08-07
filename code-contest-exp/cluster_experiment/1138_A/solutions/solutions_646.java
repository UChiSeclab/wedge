import java.util.*;

public class Sushi {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        int[] sushi = new int[n];
        for (int i = 0; i < n; i++) sushi[i] = scan.nextInt();
        ArrayList<Integer> blocks = new ArrayList<Integer>();
        int last = sushi[0];
        int blocksize = 1;
        for (int i = 1; i < n; i++) {
            if (sushi[i] == last) {
                blocksize++;
            }
            else {
                blocks.add(blocksize);
                blocksize = 1;
            }
            last = sushi[i];
        }
        blocks.add(blocksize); // Add last block
        // Find the largest block with an adjacent sized block
        int largestAdjacentBlock = -1;
        for (int i = 1; i < blocks.size(); i++) {
            largestAdjacentBlock = Math.max(largestAdjacentBlock, Math.min(blocks.get(i), blocks.get(i - 1)));
        }
        System.out.println(largestAdjacentBlock * 2);
    }
}