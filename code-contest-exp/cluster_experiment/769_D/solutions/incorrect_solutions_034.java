import jdk.nashorn.internal.ir.RuntimeNode;

import java.io.*;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Stream;

/**
 * @author ramilagger
 */
public class Main {

    final static boolean ONLINE_JUDGE = System.getProperty("ONLINE_JUDGE") != null;
    int num;
    int cnt[] = new int[100005];
    int ans = 0;
    int k;

    public void generate(int index,int changes,int mask, int l){

        if(mask < 100001) {
            if (index == l) {
            if(changes != k) System.err.println("wtf");
            ans += cnt[mask];
        }
        else {
                if (changes + l - index > k)
                    generate(index + 1, changes, mask, l);
                if (changes < k)
                    generate(index + 1, changes + 1, mask ^ (1 << index), l);
            }
        }
    }
    void solve() {
        int n = nextInt();
         k = nextInt();
        for (int i = 0; i < n; i++) {
            int a = nextInt();
            generate(0,0,a,16);
            cnt[a]++;
        }
        pw.println(ans);
    }

    public void run() throws IOException {
        start = System.currentTimeMillis();
        solve();
        if (!ONLINE_JUDGE)
            System.err.println(System.currentTimeMillis() - start + " ms");
        br.close();
        pw.close();
    }

    public Main() {
        try {
            br = (ONLINE_JUDGE) ? new BufferedReader(new InputStreamReader(System.in))
                    : new BufferedReader(new FileReader("in.txt"));
            pw = (ONLINE_JUDGE) ? new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)))
                    : new PrintWriter(new BufferedWriter(new FileWriter("out.txt")));
            this.run();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public static void main(String[] args) {
        new Main();
    }


    public boolean hasNext() {
        if (st != null && st.hasMoreTokens())
            return true;
        try {
            while (st == null || !st.hasMoreTokens())
                st = new StringTokenizer(br.readLine());
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public String next() {
        if (hasNext())
            return st.nextToken();
        return null;
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

    public BigInteger nextBigInteger() {
        return new BigInteger(next());
    }

    public String nextLine() {
        StringBuilder sb;
        try {
            while (st == null || !st.hasMoreTokens()) return br.readLine();
            sb = new StringBuilder(st.nextToken());
            while (st.hasMoreTokens()) sb.append(" " + st.nextToken());
        } catch (IOException e) {
            throw new RuntimeException();
        }
        return sb.toString();
    }

    public int[] nextArray(int n) {
        int[] temp = new int[n];
        for (int i = 0; i < n; i++)
            temp[i] = nextInt();
        return temp;
    }

    public long[] nextLArray(int n) {
        long[] temp = new long[n];
        for (int i = 0; i < n; i++)
            temp[i] = nextLong();
        return temp;
    }

    long start;
    final BufferedReader br;
    final PrintWriter pw;
    StringTokenizer st;

}