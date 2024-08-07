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
        for (int i = 0; i < n; addX(nextInt()), i++) ;
        boolean used[] = new boolean[150002];
        int ans = 0;
        for (Map.Entry<Integer, Integer> i : tm.entrySet()) {
            int cnt = i.getValue();
            int key = i.getKey();
            if (key != 1) {
                if (!used[key - 1]) {
                    ans++;
                    used[key - 1] = true;
                    cnt--;
                }
            }
            if (cnt > 0 && !used[key]) {
                ans++;
                used[key] = true;
                cnt--;
            }
            if (cnt > 0 && !used[key + 1]) {
                ans++;
                used[key + 1] = true;
            }
        }
        System.out.println(ans);
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