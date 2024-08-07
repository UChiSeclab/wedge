import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * Built using CHelper plug-in
 * Actual solution is at the top
 *
 * @author Bisoye
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
            int[] T = new int[n];
            for (int i = 0; i < n; i++) {
                T[i] = in.nextInt();
            }

            List<Integer> l = new ArrayList<>();
            int count = 0, curr = T[0];
            for (int i = 0; i < n; i++) {
                if (T[i] == curr) count++;
                else {
                    l.add(count);
                    curr = T[i];
                    count = 1;
                }
            }
            l.add(count);
            System.out.println(l);

            int max = 0;
            for (int i = 1; i < l.size(); i++) {
                int a = Math.min(l.get(i), l.get(i - 1));
                max = Math.max(max, a);
            }
            out.println(max * 2);

        }

    }
}

