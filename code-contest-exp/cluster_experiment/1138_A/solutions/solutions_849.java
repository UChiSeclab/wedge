import java.io.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import java.util.*;
import java.math.*;
import java.net.*;

import static java.lang.Math.*;

public class Solution implements Runnable {

    void solve() throws Throwable {
        int n = sc.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = sc.nextInt();
        }
        int res = 0;
        for (int i = 0, j = 0; j < n; ) {
            while (j < n && a[i] == a[j])
                j++;
            int k = j + 1;
            while (k < n && a[j] == a[k])
                k++;
            res = max(res, min(j - i, k - j));
            i = j;
            j = k;
        }
        out.println(res * 2);
    }
    
    BufferedReader in;
    PrintWriter out;
    FastScanner sc;

    final String INPUT_FILE = "";
    final String OUTPUT_FILE = "";

    static Throwable throwable;

    public static void main(String[] args) throws Throwable {
        Thread thread = new Thread(null, new Solution(), "", (1 << 26));
        thread.start();
        thread.join();
        if (Solution.throwable != null)
            throw Solution.throwable;
    }

    public void run() {
        try {
            if (INPUT_FILE.equals("")) {
                in = new BufferedReader(new InputStreamReader(System.in));
            } else {
                in = new BufferedReader(new FileReader(INPUT_FILE));
            }
            if (OUTPUT_FILE.equals("")) {
                out = new PrintWriter(System.out);
            } else {
                out = new PrintWriter(OUTPUT_FILE);
            }
            sc = new FastScanner(in);
            solve();
        } catch (Throwable e) {
            throwable = e;
        } finally {
            out.close();
        }
    }

}

class FastScanner {
	
    BufferedReader reader;
    StringTokenizer strTok;

    FastScanner(BufferedReader reader) {
        this.reader = reader;
    }

    public String nextToken() throws Exception {
        while (strTok == null || !strTok.hasMoreTokens()) {
            strTok = new StringTokenizer(reader.readLine());
        }
        return strTok.nextToken();
    }

    public int nextInt() throws Exception {
        return Integer.parseInt(nextToken());
    }

    public double nextDouble() throws Exception {
        return Double.parseDouble(nextToken());
    }

    public long nextLong() throws Exception {
        return Long.parseLong(nextToken());
    }
}