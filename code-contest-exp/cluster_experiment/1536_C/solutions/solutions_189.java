import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.*;

public class Main3 {
    public static void main(String[] args) {
        FastReader in = new FastReader();
        PrintWriter pw = new PrintWriter(System.out);

        int j = in.nextInt();

        while(j-- > 0) {
            int n = in.nextInt();
            String s = in.nextLine();

            int[] d = new int[n+1];
            int[] k = new int[n+1];

            Map<Double, Integer> ans = new HashMap<>();

            boolean checkD = true;
            for(int i = 1; i <= n; i++) {
                d[i] = d[i-1];
                k[i] = k[i-1];

                if(s.charAt(i - 1) == 'D') {
                    d[i]++;
                } else {
                    k[i]++;
                }

                if(i == 1 && d[i] == 0) {
                    checkD = false;
                }

                double dept;

                if(checkD) {
                    dept = (double) k[i] / d[i];
                } else {
                    dept = (double) d[i] / k[i];
                }

                if(ans.containsKey(dept)) {
                    ans.put(dept, ans.get(dept) + 1);
                } else {
                    ans.put(dept, 1);
                }

                pw.print(ans.get(dept) + " ");
            }

            pw.println();
        }

        pw.close();
    }

    static class FastReader {
        BufferedReader br;
        StringTokenizer st;

        public FastReader() {
            br = new BufferedReader(new
                    InputStreamReader(System.in));
        }

        String next() {
            while(st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch(IOException e) {
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
            } catch(IOException e) {
                e.printStackTrace();
            }
            return str;
        }
    }
}
