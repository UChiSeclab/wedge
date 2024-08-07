import java.io.*;

public class vkcup {

    public static void main(String[] args) throws IOException {
        new vkcup().run();
    }

    private StreamTokenizer in;
    private PrintWriter out;

    int nextInt() throws IOException {
        in.nextToken();
        return (int) in.nval;
    }

    String nextString() throws IOException {
        in.nextToken();
        return in.sval;
    }

    char nextChar() throws IOException {
        in.nextToken();
        return in.sval.charAt(0);
    }

    private void run() throws IOException {
        in = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));
        out = new PrintWriter(new OutputStreamWriter(System.out));
        solve();
        out.flush();
    }

    private void solve() throws IOException {
        int n = nextInt();
        int k = nextInt();
        int[] c = new int[n];
        for (int i = 0; i < n; i++) {
            c[i] = nextInt();
        }
        out.print(n);
        /*for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (check(c[i], c[j], k)) cnt++;
            }
        }*/
    }

    /*private int[][] che = new int[10001][10001];

    private boolean check(int a, int b, int k) {
        if (a == b) return k == 0;
        int cnt = 0;
        if (che[a][b] == 0) {
            int xor = a ^ b;
            boolean[] aa = toBinary(xor);
            for (int i = 0; i < 32; i++) {
                if (aa[i]) cnt++;
            }
        } else {
             cnt = che[a][b];
        }
        return k == cnt;
    }

    private boolean[] toBinary(int number) {
        final boolean[] ret = new boolean[32];
        for (int i = 31; i >= 0; i--) {
            ret[i] = (number & (1 << i)) != 0;
        }
        return ret;
    }*/
}