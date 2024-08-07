import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class DilucAndkaeya {

    static FastReader read = new FastReader();

    //Map<Pair, Integer> map;
    public static void main(String[] args) {

        int t = read.nextInt();

        while (t-- > 0)
            solve();
    }

    private static void solve() {
        int n = read.nextInt();

        String s = read.next();

        Map<String, Integer> map = new HashMap<>();
        int d = 0, k = 0;

        //List<Pair> list = new ArrayList<>();

        StringBuilder sb = new StringBuilder();

        for (char c : s.toCharArray()) {
            if (c == 'D') d++;
            if (c == 'K') k++;

            int x = d, y = k;

            if (x == 0) {
                y = 1;
            } else if (y == 0) {
                x = 1;
            } else {

                int gcd = gcd(x, y);

                x /= gcd;
                y /= gcd;
            }

            String tmp = x+"*"+y;



            map.put(tmp, map.getOrDefault(tmp, 0) + 1);


            sb.append(map.get(tmp));
            sb.append(" ");


        }
        System.out.println(sb);


    }

    private static Pair getFromList(int d, int k, List<Pair> list) {

        for (Pair p : list) {
            if (p.x == d && p.y == k)
                return p;
        }

        return null;
    }

    private static int gcd(int a, int b) {

        if (a == 0)
            return b;

        return gcd(b % a, a);
    }

    static class Pair {
        int x;
        int y;

        Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    static class FastReader {
        BufferedReader br;
        StringTokenizer st;

        public FastReader() {
            br = new BufferedReader(new
                    InputStreamReader(System.in));
        }

        public String next() {
            while (st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

        public long nextLong() {
            return Long.parseLong(next());
        }

        public double nextDouble() {
            return Double.parseDouble(next());
        }

        String nextLine() {
            String str = "";
            try {
                str = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return str;
        }

        String[] strArray() {
            String[] str = nextLine().split(" ");
            return str;
        }

        int[] intArray(int n) {
            String[] str = strArray();
            int[] arr = new int[n];
            for (int i = 0; i < n; i++) {
                arr[i] = Integer.parseInt(str[i]);
            }
            return arr;
        }
    }

}
