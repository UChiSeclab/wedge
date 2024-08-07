import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        Scanner in = new Scanner(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        Task solver = new Task();
        solver.solve(in, out);
        out.close();
    }
}

class Task {

    //    static int MAX = 1000001;
    static int MAX = 10001;
    boolean[] used;
    //    List<List<Integer>> g = new ArrayList<>();
    int n;
    int[] a;
    List<Vect> res = new ArrayList<>();

    byte[][] m = new byte[MAX][MAX];
    long[] aa = new long[MAX];

    byte[] xors = new byte[MAX * 2];

    public void solve(Scanner in, PrintWriter out) {
        n = in.nextInt();
        int k = in.nextInt();
        Arrays.fill(aa, 0);
        for (int i = 0; i < n; i++) {
            ++aa[in.nextInt()];
        }
//        long start = System.currentTimeMillis();
        fillM();
//        System.out.println(System.currentTimeMillis() - start);
        long r = 0;
        for (int i = 0; i < MAX; i++) {
            if (aa[i] == 0) {
                continue;
            }
            for (int j = i; j < MAX; j++) {
                if (aa[j] == 0) {
                    continue;
                }
                if (m[i][j] == k) {
                    if (i == j) {
                        r += aa[i] * (aa[i] - 1) / 2;
                    } else {
                        r += aa[i] * aa[j];
                    }
                }
            }
        }
        System.out.println(r);
//        System.out.println(System.currentTimeMillis() - start);
    }

    void fillM() {
//        Arrays.fill(m, new byte[MAX]);
        for (int i = 0; i < MAX * 2; i++) {
            int x = i;
            byte sum = 0;
            while (x != 0) {
                if ((x & 1) == 1) {
                    ++sum;
                }
                x = x >> 1;
            }
            xors[i] = sum;
        }

        for (int i = 0; i < MAX; i++) {
            for (int j = i; j < MAX; j++) {
                m[i][j] = xors[i ^ j];
            }
        }
    }

}


class Utils {

    public static int binarySearch(int[] a, int key) {
        int s = 0;
        int f = a.length;
        while (f > s) {
            int mid = (s + f) / 2;
            if (a[mid] > key) {
                f = mid - 1;
            } else if (a[mid] <= key) {
                s = mid + 1;
            }
        }
        return -1;
    }
}

class Pair<T, U> {
    public T a;
    public U b;

    public Pair(T a, U b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public boolean equals(Object obj) {
        Pair<T, U> p = (Pair<T, U>) obj;
        return p.a.equals(a) || p.a.equals(b);
    }

    @Override
    public int hashCode() {
        return 42;
    }
}


class Vect {
    public int a;
    public int b;

    public Vect(int a, int b) {
        this.a = a;
        this.b = b;
    }
}

class Triple<T, U, P> {
    public T a;
    public U b;
    public P c;

    public Triple(T a, U b, P c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }
}


class Scanner {
    BufferedReader in;
    StringTokenizer tok;

    public Scanner(InputStream in) {
        this.in = new BufferedReader(new InputStreamReader(in));
        tok = new StringTokenizer("");
    }

    private String tryReadNextLine() {
        try {
            return in.readLine();
        } catch (Exception e) {
            throw new InputMismatchException();
        }
    }

    public String nextToken() {
        while (!tok.hasMoreTokens()) {
            tok = new StringTokenizer(next());
        }
        return tok.nextToken();
    }

    public String next() {
        String newLine = tryReadNextLine();
        if (newLine == null)
            throw new InputMismatchException();
        return newLine;
    }

    public int nextInt() {
        return Integer.parseInt(nextToken());
    }

    public long nextLong() {
        return Long.parseLong(nextToken());
    }
}