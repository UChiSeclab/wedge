import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;
import java.io.IOException;

public class C_Diluc_and_Kaeya  {
    public static class FastReader {
        BufferedReader br;
        StringTokenizer st;

        public FastReader() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() {
            while (st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }

        long nextLong() {
            return Long.parseLong(next());
        }

        double nextDouble() {
            return Double.parseDouble(next());
        }

        String nextLine() {
            String str = "";
            try {
                str = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return str;
        }
    }

    public static int gcd(int a,int b) {
        if (b == 0)
            return a;
        return gcd(b, a % b);
    }

    public static void main(String[] args) throws java.lang.Exception {
        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(System.out));
        FastReader sc = new FastReader();
        int t = sc.nextInt();
        while (t-- > 0) {
            int n = sc.nextInt();
            String s = sc.nextLine();
            int cd = 0;
            int ck = 0;
            HashMap<String, Integer> map = new HashMap<>();
            for (int i = 0; i < n; i++) {
                if (s.charAt(i) == 'D')
                    cd++;
                else
                    ck++;
                int g = gcd(cd, ck);
                int rd = cd / g;
                int rk = ck / g;
                String ratio = String.valueOf(rd) + " " + String.valueOf(rk);
                map.put(ratio, map.getOrDefault(ratio, 0) + 1);
                output.write(map.get(ratio) + " ");
            }
            output.write("\n");
        }
        output.flush();
    }
}