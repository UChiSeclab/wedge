import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.*;

public class Solution {


    static BufferedReader br;
    static PrintWriter out;
    static StringTokenizer st;

    static int gcd (int a, int b) {
        while (b > 0) {
            a %= b;
            int tmp =  a;
            a = b;
            b = tmp;
        }
        return a;
    }

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        out = new PrintWriter(new OutputStreamWriter(System.out));
        // br = new BufferedReader(new FileReader("in.txt"));
        // out = new PrintWriter(new FileWriter("out.txt"));

        int t = readInt();
        List<StringBuilder> answers = new ArrayList<>();

        for (int k = 0; k < t; k++) {
            StringBuilder sb = new StringBuilder();
            Map<Integer, Integer> ans = new HashMap<>();
            answers.add(sb);
            int n = readInt();
            String s = readLine();
            int nD = 0;
            int nK = 0;
            for (int i = 0; i < n; i++) {
                if ('D' == s.charAt(i)) {
                    nD++;
                }
                else {
                    nK++;
                }
                int g = gcd(nD, nK);
                int nDt = nD / g;
                int nKt = nK / g;

                //Map.Entry<Integer, Integer> nE = new AbstractMap.SimpleEntry<>(nDt, nKt);
                int nE = nDt << 19 + nKt;
                int curr = ans.getOrDefault(nE, 0);
                ans.put(nE, curr + 1);
                sb.append(curr + 1).append(' ');
            }
        }
        for (StringBuilder stringBuilder : answers) {
            out.println(stringBuilder.toString());
        }

        out.close();
    }

    static String next() throws IOException {
        while (st == null || !st.hasMoreTokens())
            st = new StringTokenizer(br.readLine().trim());
        return st.nextToken();
    }

    static long readLong() throws IOException {
        return Long.parseLong(next());
    }

    static int readInt() throws IOException {
        return Integer.parseInt(next());
    }

    static double readDouble() throws IOException {
        return Double.parseDouble(next());
    }

    static char readCharacter() throws IOException {
        return next().charAt(0);
    }

    static String readLine() throws IOException {
        return br.readLine().trim();
    }
}