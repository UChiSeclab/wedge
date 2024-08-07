
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class ProblemG12D {
    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter writer = new PrintWriter(System.out);

        int t = Integer.parseInt(reader.readLine());

        for (int tt = 0; tt < t; tt++) {
            int n = Integer.parseInt(reader.readLine());
            int[] a = new int[n];
            StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
            boolean[] hasValue = new boolean[n + 1];
            MultiTreeSet set = new MultiTreeSet();
            for (int i = 0; i < n; i++) {
                a[i] = Integer.parseInt(tokenizer.nextToken());
                hasValue[a[i]] = true;
                set.add(a[i]);
            }
            boolean permutation = true;
            for (int i = 1; i <= n; i++) {
                if (!hasValue[i]) {
                    permutation = false;
                    break;
                }
            }
            boolean[] good = new boolean[n + 1];
            if (permutation) {
                good[1] = true;
            }
            int left = 0;
            int right = n - 1;
            for (int i = 1; i <= n; i++) {
                if (set.first() == i) {
                    good[n - i + 1] = true;
                    // try remove from either end
                    if (a[left] == i) {
                        set.remove(a[left]);
                        left++;
                        continue;
                    }
                    if (a[right] == i) {
                        set.remove(a[right]);
                        right--;
                        continue;
                    }
                }
                break;
            }
            for (int i = 1; i <= n; i++) {
                writer.print(good[i] ? "1" : "0");
            }
            writer.println();
        }

        

        reader.close();
        writer.close();
    }
    static class MultiTreeSet {
        private TreeMap<Long, Long> map = new TreeMap<>();
        public void add(long x) {
            map.put(x, map.getOrDefault(x, 0L) + 1L);
        }
        public void remove(long x) {
            map.put(x, map.getOrDefault(x, 0L) - 1L);
            if (map.get(x) <= 0) {
                map.remove(x);
            }
        }
        public long first() {
            return map.firstKey();
        }
        public long last() {
            return map.lastKey();
        }
        @Override
        public String toString() {
            return map.toString();
        }
    }
}
