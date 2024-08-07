import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.LinkedBlockingDeque;

public class ProblemF_SegmentTree {

    public static InputStream inputStream = System.in;

    public static OutputStream outputStream = System.out;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(inputStream);
        PrintWriter out = new PrintWriter(outputStream);

        int n = scanner.nextInt();
        List<Pair<Long, Long>> xvList = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            xvList.add(new Pair<>(scanner.nextLong(), 0L));
        }
        for (int i = 0; i < n; i++) {
            xvList.get(i).second = scanner.nextLong();
        }

        xvList.sort(Comparator.comparingLong(t -> t.first));

        Map<Long, Pair<Long, Long>> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            long x = xvList.get(i).first;
            long v = xvList.get(i).second;
            if (!map.containsKey(v)) {
                map.put(v, new Pair<>(0L, 0L));
            }
            map.put(v, sum(map.get(v), new Pair<>(x, 1L)));
        }

        Map<Long, Integer> indexes = new HashMap<>();
        int index = 0;
        for (Long key : new TreeSet<>(map.keySet())) {
            indexes.put(key, index);
            index++;
        }

        SegmentTree segmentTree = new SegmentTree(new ArrayList<>(map.values()));

        long ans = 0L;

        for (int i = 0; i < n; i++) {
            long x = xvList.get(i).first;
            long v = xvList.get(i).second;
            index = indexes.get(v);
            segmentTree.add(index, new Pair<>(-x, -1L));
            Pair<Long, Long> sum = segmentTree.getSum(index, n - 1);
            ans += sum.first - (sum.second * x);
        }

        out.println(ans);

        out.flush();
    }

    private static class SegmentTree {

        private Node root;

        private SegmentTree(List<Pair<Long, Long>> list) {
            root = getNode(list, 0, list.size() - 1);
        }

        private Node getNode(List<Pair<Long, Long>> list, int l, int r) {
            Node node = new Node();
            node.left = l;
            node.right = r;
            if (l == r) {
                node.value = list.get(l);
            } else {
                int middle = (l + r) / 2;
                node.leftNode = getNode(list, l, middle);
                node.rightNode = getNode(list, middle + 1, r);
                node.value = sum(node.leftNode.value, node.rightNode.value);
            }

            return node;
        }

        private Pair<Long, Long> getSum(int l, int r) {
            return root.getSum(l, r);
        }

        private void add(int index, Pair<Long, Long> value) {
            Node node = root;
            while (true) {
                node.value = sum(node.value, value);
                if (node.leftNode == null) {
                    break;
                }
                if (node.leftNode.right >= index) {
                    node = node.leftNode;
                } else {
                    node = node.rightNode;
                }
            }
        }
    }

    private static class Node {

        private Pair<Long, Long> value;

        private int left;

        private int right;

        private Node leftNode;

        private Node rightNode;

        private Pair<Long, Long> getSum(int l, int r) {
            if (left >= l && right <= r) {
                return value;
            }
            if (right < l || left > r) {
                return new Pair<>(0L, 0L);
            }
            return sum(leftNode.getSum(l, r), rightNode.getSum(l, r));
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

    private static Pair<Long, Long> sum(Pair<Long, Long> p1, Pair<Long, Long> p2) {
        return new Pair<>(p1.first + p2.first, p1.second + p2.second);
    }
}
