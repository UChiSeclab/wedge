import java.io.*;
import java.util.*;

public class tank {

    static final FastScanner fs = new FastScanner();

    static PrintWriter out = new PrintWriter(System.out);

    public static void main(String[] args) {
        int t = fs.nextInt();
        while(t-->0) {
            run_case();
        }

        out.close();
    }

    static void run_case() {
        int n = fs.nextInt(), d = 0, k = 0;
        char[] arr = fs.next().toCharArray();
        int[] ans = new int[n];

        HashMap<String, Integer> hm = new HashMap<>();

        for (int i = 0; i < n; i++) {
            if(arr[i] == 'D') d++;
            else k++;

            if(d == 0) {
                hm.put("0/1", hm.getOrDefault("0/1", 0) + 1);
                ans[i] = hm.get("0/1");
            }else if(k == 0) {
                hm.put("1/0", hm.getOrDefault("1/0", 0) + 1);
                ans[i] = hm.get("1/0");
            }else {
                int gc = gcd(d, k), dd = d/gc, kk = k/gc;
                String ke = dd + "/" + kk;
                hm.put(ke, hm.getOrDefault(ke, 0) + 1);
                ans[i] = hm.get(ke);
            }
        }

        for(int i: ans) out.print(i + " ");
        out.println();
    }

    static int gcd(int a, int b) {
        if (a == 0)
            return b;
        return gcd(b % a, a);
    }

    static class FastScanner {
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st=new StringTokenizer("");
        String next() {
            while (!st.hasMoreTokens())
                try {
                    st=new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            return st.nextToken();
        }

        String nextLine(){
            try {
                return br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return "";
        }

        int nextInt() {
            return Integer.parseInt(next());
        }

        int[] readArray(int n) {
            int[] a=new int[n];
            for (int i=0; i<n; i++) a[i]=nextInt();
            return a;
        }

        long nextLong() {
            return Long.parseLong(next());
        }

        double nextDouble() {
            return Double.parseDouble(next());
        }
    }
}