import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;

public class ProblemF {

    public static InputStream inputStream = System.in;

    public static OutputStream outputStream = System.out;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(inputStream);
        PrintWriter out = new PrintWriter(outputStream);

        int n = scanner.nextInt();
        List<ProblemF.Pair<Long, Long>> xvList = new ArrayList<>();
        TreeSet<Long> treeSet = new TreeSet<>();
        for (int i = 0; i < n; i++) {
            xvList.add(new ProblemF.Pair<>(scanner.nextLong(), 0L));
        }
        for (int i = 0; i < n; i++) {
            long v = scanner.nextLong();
            xvList.get(i).second = v;
            treeSet.add(v);
        }

        Map<Long, Integer> indexesMap = new HashMap<>();
        int index = treeSet.size() - 1;
        for (long x : treeSet) {
            indexesMap.put(x, index--);
        }

        xvList.sort((a, b) -> {
            int x = Long.compare(b.first, a.first);
            if (x != 0) {
                return x;
            }
            return Long.compare(b.second, a.second);
        });

        BinaryIndexedTree xSumTree = new BinaryIndexedTree(n);
        BinaryIndexedTree countSumTree = new BinaryIndexedTree(n);

        long ans = 0;

        for (int i = 0; i < n; i++) {
            long x = xvList.get(i).first;
            long v = xvList.get(i).second;

            index = indexesMap.get(v);
            ans += (xSumTree.getSum(index) - x * countSumTree.getSum(index));

            xSumTree.update(index, x);
            countSumTree.update(index, 1);
        }

        out.println(ans);

        out.flush();
    }

    private static class BinaryIndexedTree {

        private long[] bit;

        private int n;

        private BinaryIndexedTree(int n) {
            this.bit = new long[n + 1];
            this.n = n;
        }

        private void update(int index, long delta) {
            index++;
            while (index <= n) {
                bit[index] += delta;
                index += (index & (-index));
            }
        }

        private long getSum(int index) {
            index++;
            long sum = 0;
            while (index > 0) {
                sum += bit[index];
                index = index - (index & (-index));
            }
            return sum;
        }
    }

    private static class Pair<F, S> {

        private F first;

        private S second;

        public Pair() {}

        public Pair(F first, S second) {
            this.first = first;
            this.second = second;
        }
    }

}
