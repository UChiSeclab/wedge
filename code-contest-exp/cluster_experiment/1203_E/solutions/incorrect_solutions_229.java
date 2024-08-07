import java.io.*;
import java.util.*;

public class Main {

    static TreeMap<Integer, Integer> tm;

    public static void main(String args[]) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        int n = nextInt();
        int a[] = new int[n];
        tm = new TreeMap<>();
        for (int i = 0; i < n; i++) {
            a[i] = nextInt();
            addX(a[i]);
        }
        TreeSet<Integer> ans = new TreeSet<>();
        for (Map.Entry<Integer, Integer> v : tm.entrySet()) {
            int cnt = v.getValue() - 1;
            ans.add(v.getKey());
            if (v.getKey() != 1) {
                ans.add(v.getKey() - 1);
                cnt--;
            }
            if (cnt > 0) {
                ans.add(v.getKey() + 1);
            }

        }
        System.out.println(ans.size());
    }

    static void addX(int x) {
        if (tm.containsKey(x)) tm.put(x, tm.get(x) + 1);
        else tm.put(x, 1);
    }

    static void removeX(int x) {
        tm.put(x, tm.get(x) - 1);
        if (tm.get(x) == 0) tm.remove(x);
    }

    static StringTokenizer st = new StringTokenizer("");


    static String next() throws IOException {
        while (st == null || !st.hasMoreTokens())
            st = new StringTokenizer(br.readLine());
        return st.nextToken();
    }

    static BufferedReader br;

    static int nextInt() throws IOException {
        return Integer.parseInt(next());
    }

    static long nextLong() throws IOException {
        return Long.parseLong(next());
    }
}