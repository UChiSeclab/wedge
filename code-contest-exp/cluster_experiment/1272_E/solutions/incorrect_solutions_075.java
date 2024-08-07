import java.util.*;
import java.lang.*;
import java.io.*;
import java.math.*;

public class Codeforces {

    static double Phi = (1 + Math.sqrt(5)) / 2.0;
    static int MAX = -1 >>> 1;
    static long MODL = 1_000_000_007L;
    static int MOD = 1_000_000_007;
    static void no() throws IOException {println("NO");}
    static void yes() throws IOException {println("YES");}

    static boolean testCases = false;
    static boolean DEBUG = true;

    static void main() throws Exception {
        int n = ni();
        int[] a = nia(n);
        int[] steps = new int[n];
        Arrays.fill(steps, Integer.MAX_VALUE);
        for (int i = 0; i < n; ++i) {
            Set<Integer> st = new HashSet<>();
            if (steps[i] == Integer.MAX_VALUE) recurse(a, i, steps, st);
        }
        for (int ele : steps) {
            print(ele + " ");
        }
    }
    static void recurse(int[] a, int i, int[] steps, Set<Integer> st) {
        if (st.contains(i)) {
            steps[i] = -1;
            return;
        }
        st.add(i);
        //check left
        if (i - a[i] >= 0) {
            //check

            if (a[i - a[i]] % 2 != a[i] % 2) {
                //opposites
                steps[i] = 1;
            } else {
                if (steps[i - a[i]] == Integer.MAX_VALUE) {
                    recurse(a, i - a[i], steps, st);
                }
                if(steps[i] == Integer.MAX_VALUE) {
                    steps[i] = steps[i - a[i]] == -1 ? -1 : Math.min(steps[i], 1 + steps[i - a[i]]);
                } else {
                    steps[i] = steps[i - a[i]] == -1 ? steps[i] : Math.min(steps[i], 1 + steps[i - a[i]]);
                }
            }
        }
        if (i + a[i] < a.length) {
            if (a[i + a[i]] % 2 != a[i] % 2) {
                //opposites
                steps[i] = 1;
            } else {
                if (steps[i + a[i]] == Integer.MAX_VALUE) {
                    recurse(a, i + a[i], steps, st);
                }
                if(steps[i] == Integer.MAX_VALUE) {
                    steps[i] = steps[i + a[i]] == -1 ? -1 : Math.min(steps[i], 1 + steps[i + a[i]]);
                } else {
                    steps[i] = steps[i + a[i]] == -1 ? steps[i] : Math.min(steps[i], 1 + steps[i + a[i]]);
                }
            }
        }
        if(steps[i] == Integer.MAX_VALUE) {
            steps[i] = -1;
        }
        st.remove(i);
    }

    public static void main(String[] args) throws Exception {
        long startPointTime = System.currentTimeMillis();
        scannerIn = System.in;
        printerBW = new BufferedWriter(new OutputStreamWriter(System.out));
        if (DEBUG && System.getProperty("ONLINE_JUDGE") == null) {
            HABLA = true;
            debug("Debugging enabled");
        }

        int t = testCases ? ni() : 1;
        int to = t;
        while (t-- > 0) {
            debug("Case #" + (to - t) + " ---------------- ");
            main();
        }
        long endTime = System.currentTimeMillis();
        float totalProgramTime = endTime - startPointTime;
        if (HABLA)
            debug("Execution time is " + totalProgramTime + " (" + (totalProgramTime / 1000) + "s)");
        close();
    }

//IO Stuff
    private static boolean HABLA = false;
    private static byte[] scannerByteBuffer = new byte[1024]; // Buffer of Bytes
    private static int scannerIndex;
    private static InputStream scannerIn;
    private static int scannerTotal;
    private static BufferedWriter printerBW;

    private static int next() throws IOException { // Scan method used to scan buf
        if (scannerTotal < 0)
            throw new InputMismatchException();
        if (scannerIndex >= scannerTotal) {
            scannerIndex = 0;
            scannerTotal = scannerIn.read(scannerByteBuffer);
            if (scannerTotal <= 0)
                return -1;
        }
        return scannerByteBuffer[scannerIndex++];
    }

    static int ni() throws IOException {
        int integer = 0;
        int n = next();
        while (isWhiteSpace(n)) // Removing startPointing whitespaces
            n = next();
        int neg = 1;
        if (n == '-') { // If Negative Sign encounters
            neg = -1;
            n = next();
        }
        while (!isWhiteSpace(n)) {
            if (n >= '0' && n <= '9') {
                integer *= 10;
                integer += n - '0';
                n = next();
            } else
                throw new InputMismatchException();
        }
        return neg * integer;
    }

    static long nl() throws IOException {
        long integer = 0;
        int n = next();
        while (isWhiteSpace(n)) // Removing startPointing whitespaces
            n = next();
        int neg = 1;
        if (n == '-') { // If Negative Sign encounters
            neg = -1;
            n = next();
        }
        while (!isWhiteSpace(n)) {
            if (n >= '0' && n <= '9') {
                integer *= 10;
                integer += n - '0';
                n = next();
            } else
                throw new InputMismatchException();
        }
        return neg * integer;
    }

    static String line() throws IOException {
        StringBuilder sb = new StringBuilder();
        int n = next();
        while (isWhiteSpace(n))
            n = next();
        while (!isNewLine(n)) {
            sb.append((char) n);
            n = next();
        }
        return sb.toString();
    }

    private static boolean isNewLine(int n) {
        return n == '\n' || n == '\r' || n == -1;
    }

    private static boolean isWhiteSpace(int n) {
        return n == ' ' || isNewLine(n) || n == '\t';
    }

    static int[] nia(int n) throws Exception {
        if (n < 0)
            throw new Exception("Array size should be non negative");
        int[] array = new int[n];
        for (int i = 0; i < n; i++)
            array[i] = ni();
        return array;
    }

    static long[] nla(int n) throws Exception {
        if (n < 0)
            throw new Exception("Array size should be non negative");
        long[] array = new long[n];
        for (int i = 0; i < n; i++)
            array[i] = nl();
        return array;
    }

    static float[] nfa(int n) throws Exception {
        if (n < 0)
            throw new Exception("Array size should be non negative");
        float[] array = new float[n];
        for (int i = 0; i < n; i++)
            array[i] = nl();
        return array;
    }

    static double[] nda(int n) throws Exception {
        if (n < 0)
            throw new Exception("Array size should be non negative");
        double[] array = new double[n];
        for (int i = 0; i < n; i++)
            array[i] = nl();
        return array;
    }

    static <T> void print(T str) throws IOException {
        printerBW.append(str.toString());
        if (HABLA) flush();
    }

    static <T> void println() throws IOException {
        printerBW.append('\n');
        if (HABLA) flush();
    }

    static <T> void println(T str) throws IOException {
        if (str == null) {
            printerBW.append("null\n");
        } else {
            printerBW.append(str.toString());
            printerBW.append('\n');
            if (HABLA) flush();
        }
    }

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_DEBUG = "\u001B[33m";
    static <T> void debugl(T str) throws IOException {
        if (!HABLA) return;
        if (str == null) {
            printerBW.append(ANSI_DEBUG);
            printerBW.append("null");
            printerBW.append(ANSI_RESET);
        } else {
            printerBW.append(ANSI_DEBUG);
            printerBW.append(str.toString());
            printerBW.append(ANSI_RESET);
        }
        flush();
    }

    static void debug() throws IOException {
        if (!HABLA) return;
        printerBW.append('\n');
        flush();
    }
    static <T> void debug(T str) throws IOException {
        if (!HABLA) return;
        if (str == null) {
            printerBW.append(ANSI_DEBUG);
            printerBW.append("null\n");
            printerBW.append(ANSI_RESET);
        } else {
            printerBW.append(ANSI_DEBUG);
            printerBW.append(str.toString());
            printerBW.append(ANSI_RESET);
            printerBW.append('\n');
        }
        flush();
    }

    static void flush() throws IOException {
        printerBW.flush();
    }

    static void close() {
        try {
            printerBW.close();
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }
}
