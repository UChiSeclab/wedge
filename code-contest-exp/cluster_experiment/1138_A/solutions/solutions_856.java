import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * Built using CHelper plug-in
 * Actual solution is at the top
 *
 * @author ky112233
 */
public class Main {
    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        Scanner in = new Scanner(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        ASushiForTwo solver = new ASushiForTwo();
        solver.solve(1, in, out);
        out.close();
    }

    static class ASushiForTwo {
        public void solve(int testNumber, Scanner in, PrintWriter out) {
            int n = in.nextInt();
            int[] t = new int[n];
            for (int i = 0; i < n; i++) t[i] = in.nextInt();
            List<Integer> list = new ArrayList<>();
            list.add(0);
            for (int i = 1; i < n; i++) {
                if (t[i] != t[i - 1]) list.add(i);
            }
            list.add(n);
            int mx = 0;
            for (int i = 0; i < list.size() - 2; i++) {
                int len = Math.min(list.get(i + 1) - list.get(i), list.get(i + 2) - list.get(i + 1));
                mx = Math.max(mx, 2 * len);
            }
            out.println(mx);
        }

    }
}

