import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
 
public class D implements Runnable {
 
    private static final boolean ONLINE_JUDGE = System.getProperty("ONLINE_JUDGE") != null;
 
    private BufferedReader in;
    private PrintWriter out;
    private StringTokenizer tok = new StringTokenizer("");
 
    private void init() throws FileNotFoundException {
        Locale.setDefault(Locale.US);
        String fileName = "";
        if (ONLINE_JUDGE && fileName.isEmpty()) {
            in = new BufferedReader(new InputStreamReader(System.in));
            out = new PrintWriter(System.out);
        } else {
            if (fileName.isEmpty()) {
                in = new BufferedReader(new FileReader("input.txt"));
                out = new PrintWriter("output.txt");
            } else {
                in = new BufferedReader(new FileReader(fileName + ".in"));
                out = new PrintWriter(fileName + ".out");
            }
        }
    }
 
    String readString() {
        while (!tok.hasMoreTokens()) {
            try {
                tok = new StringTokenizer(in.readLine());
            } catch (Exception e) {
                return null;
            }
        }
        return tok.nextToken();
    }
 
    int readInt() {
        return Integer.parseInt(readString());
    }
 
    long readLong() {
        return Long.parseLong(readString());
    }
 
    double readDouble() {
        return Double.parseDouble(readString());
    }
 
    int[] readIntArray(int size) {
        int[] a = new int[size];
        for (int i = 0; i < size; i++) {
            a[i] = readInt();
        }
        return a;
    }
 
    public static void main(String[] args) {
        //new Thread(null, new _Solution(), "", 128 * (1L << 20)).start();
        new D().run();
    }
 
    long timeBegin, timeEnd;
 
    void time() {
        timeEnd = System.currentTimeMillis();
        System.err.println("Time = " + (timeEnd - timeBegin));
    }
 
    @Override
    public void run() {
        try {
            timeBegin = System.currentTimeMillis();
            init();
            solve();
            out.close();
            time();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
 
    private int aiverson(boolean good) {
        return good ? 1 : 0;
    }
 
    private final int MAX_VALUE = 10000;
 
    private boolean isInterestingPair(int k, int x, int y) {
        return Integer.bitCount(x ^ y) == k;
    }
 
    private void solve() {
        int n = readInt();
        int k = readInt();
        int[] a = readIntArray(n);
        int[] count = new int[MAX_VALUE + 1];
        for (int i = 0; i < n; i++) {
            count[a[i]]++;
        }
        long answer = 0;
        for (int i = 0; i <= MAX_VALUE; i++) {
            if (count[i] > 0) {
                if (k == 0) {
                    answer += 1l * count[i] * (count[i] - 1) / 2;
                } else {
                    for (int j = i + 1; j <= MAX_VALUE; j++) {
                        if (count[j] > 0 && isInterestingPair(k, i, j)) {
                            answer += 1l * count[i] * count[j];
                        }
                    }
                }
            }
        }
        out.println(answer);
    }
}