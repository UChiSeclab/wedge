import java.util.*;

/**
 * Created by Acesine on 2/24/20.
 */
public class F {
    static class BIT {
        int[] arr;
        int n;

        public BIT(int n) {
            this.n = n;
            arr = new int[n+1];
        }

        public void update(int x, int v) {
            x++;
            while (x <= n) {
                arr[x] += v;
                x += x & (-x);
            }
        }

        public int get(int x) {
            x++;
            int r = 0;
            while (x > 0) {
                r += arr[x];
                x -= x & (-x);
            }
            return r;
        }
    }

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
        Set<Integer> uniqV = new HashSet<>();
        for (int i=0;i<n;i++) {
            p[i] = new Point(x[i], v[i]);
            uniqV.add(v[i]);
        }
        List<Integer> sortedV = new ArrayList<>(uniqV);
        Collections.sort(sortedV);
        Map<Integer, Integer> vmap = new HashMap<>();
        for (int i=0;i<sortedV.size();i++) {
            vmap.put(sortedV.get(i), i);
        }
        Arrays.sort(p, (p1, p2) -> p1.x - p2.x);

        long ret = 0;
        BIT vBit = new BIT(vmap.size());
        BIT sumBit = new BIT(vmap.size());

        for (Point pt : p) {
            int pos = vmap.get(pt.v);
            int cnt = vBit.get(pos);
            int sum = sumBit.get(pos);
            ret += cnt * pt.x - sum;
            vBit.update(pos, 1);
            sumBit.update(pos, pt.x);
        }

        System.out.println(ret);
    }
}
