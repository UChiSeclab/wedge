import java.io.*;
import java.util.*;

public class Main {

    static int[][] a;

    public static void main(String[] args) throws IOException {
//        br = new BufferedReader(new FileReader(new File("cutting.in")));
//        out = new PrintWriter(new File("cutting.out"));
        br = new BufferedReader(new InputStreamReader(System.in));
        out = new PrintWriter(System.out);
        int PUTEN = 1;
        for (int POMOGI = 0; POMOGI < PUTEN; POMOGI++) {
            int n = nextInt();
            ArrayList<Integer> a = new ArrayList<>();
            for (int i = 0; i < n; i++) a.add(nextInt());
            Collections.sort(a);
            int max = 0;
            int ans = 0;
            for (int i = 0; i < n; i++) {
                if (a.get(i) - 1 > 0 && a.get(i) - 1 > max) {
                    max = a.get(i) - 1;
                    ans++;
                } else if (a.get(i) > max) {
                    max = a.get(i);
                    ans++;
                } else if (a.get(i) + 1 > max) {
                    max = a.get(i) + 1;
                    ans++;
                }
            }
            out.println(ans);

        }
        out.close();
    }


    static BufferedReader br;
    static PrintWriter out;
    static StringTokenizer in = new StringTokenizer("");

    static String next() throws IOException {
        while (!in.hasMoreTokens()) {
            in = new StringTokenizer(br.readLine());
        }
        return in.nextToken();
    }

    static int nextInt() throws IOException {
        return Integer.parseInt(next());
    }

    static long nextLong() throws IOException {
        return Long.parseLong(next());
    }

    static double nextDouble() throws IOException {
        return Double.parseDouble(next());
    }

    static int gcd(int a, int b) {
        if (b == 0) return a;
        return gcd(b, a % b);
    }
}