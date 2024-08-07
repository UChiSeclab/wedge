import java.util.*;

/**
 * Created by Acesine on 2/24/20.
 */
public class F {
    static class Point {
        int x;
        int v;
        public Point() {}
        public Point(int x, int v) {
            this.x = x;
            this.v = v;
        }
    }

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int n = s.nextInt();
        int[] x = new int[n];
        for (int i=0;i<n;i++) x[i] = s.nextInt();
        int[] v = new int[n];
        for (int i=0;i<n;i++) v[i] = s.nextInt();
        Point[] p = new Point[n];
        for (int i=0;i<n;i++) {
            p[i] = new Point(x[i], v[i]);
        }
        Arrays.sort(p, (p1, p2) -> p1.x - p2.x);
        int ret = 0;
        TreeMap<Integer, List<Integer>> left = new TreeMap<>();
        TreeMap<Integer, List<Integer>> right = new TreeMap<>();
        Set<Integer> stills = new HashSet<>();
        for (Point pt : p) {
            if (pt.v > 0) {
                for (List<Integer> pos : right.headMap(pt.v, true).values()) {
                    for (int poss : pos) ret += pt.x - poss;
                }
                for (List<Integer> pos : left.values()) {
                    for (int poss : pos) ret += pt.x - poss;
                }
                for (int poss : stills) ret += pt.x - poss;
                right.putIfAbsent(pt.v, new ArrayList<>());
                right.get(pt.v).add(pt.x);
            } else if (pt.v < 0) {
                for (List<Integer> pos : left.headMap(pt.v, true).values()) {
                    for (int poss : pos) ret += pt.x - poss;
                }

                left.putIfAbsent(pt.v, new ArrayList<>());
                left.get(pt.v).add(pt.x);
            } else {
                for (List<Integer> pos : left.values()) {
                    for (int poss : pos) ret += pt.x - poss;
                }
                for (int poss : stills) ret += pt.x - poss;

                stills.add(pt.x);
            }
        }

        System.out.println(ret);
    }
}
