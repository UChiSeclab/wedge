import java.util.*;
import java.io.*;
public class Red {
    static FastScanner f;
    static PrintWriter pw = new PrintWriter(System.out);
    static long mod = 1000_000_007;
    static long oo = Long.MAX_VALUE;
    static int ooo = Integer.MAX_VALUE;
    static int dp[][];

    static void preprocess(int arr[], int n)
    {
        // Initialize M for the intervals
        // with length 1
        for (int i = 0; i < n; i++)
            dp[i][0] = i;
 
        // Compute values from smaller
        // to bigger intervals
        for (int j = 1; (1 << j) <= n; j++) 
        {
            // Compute minimum value for
            // all intervals with size 2^j
            for (int i = 0;
                 (i + (1 << j) - 1) < n; 
                 i++) 
            {
                // For arr[2][10], we compare
                // arr[dp[0][3]] 
                // and arr[dp[3][3]]
                if (arr[dp[i][j - 1]]
                    < arr[dp[i + (1 << (j - 1))]
                                [j - 1]])
                    dp[i][j] = dp[i][j - 1];
                else
                    dp[i][j]
                        = dp[i + (1 << (j - 1))][j - 1];
            }
        }
    }

    static int min(int arr[], int L, int R)
    {
        // For [2,10], j = 3
        int j = (int)Math.log(R - L + 1);
 
        // For [2,10], we compare 
        // arr[dp[0][3]]
        // and arr[dp[3][3]],
        if (arr[dp[L][j]]
            <= arr[dp[R - (1 << j) + 1][j]])
            return arr[dp[L][j]];
 
        else
            return arr[dp[R - (1 << j) + 1][j]];
    }

    public static void solve()throws IOException {
        int n = f.ni();
        int arr[] = new int[n];

        for (int i = 0; i < n; ++i) {
            arr[i] = f.ni();
        }

        dp = new int[n][20];

        preprocess(arr , n);
        StringBuffer ans = new StringBuffer("");
        for (int i = 0; i < n; ++i) {
            // iS array i + 1 compressible ?
            int bs = n - i;
            //pn("Checing for bs = " + bs);
            HashSet<Integer> perm = new HashSet<>();
            long sum = 0;
            boolean ok = true;
            for (int j = 0; j <= i; ++j) {
                int curr = min(arr , j , j + bs - 1);
                //pn("Min from " + j + " to " + (j + bs - 1) + " = " + curr);
                if (perm.contains(curr)) {
                    ok = false;
                    //pn("BS = " + bs + " is invalid");
                    break;
                }
                perm.add(curr);
                sum += curr;
            }
            if (ok && perm.size() == (i + 1)) {
                long req = (perm.size() * (perm.size() + 1)) / 2;
                if (sum == req) {
                    ans.append("1");
                    //pn("BS = " + bs + " is valid");
                }
                else {
                    //pn("BS = " + bs + " is invalid");
                    ans.append(0);
                }
            }

            else ans.append("0");
        }
        ans.reverse();
        pn(ans);
    }    
    
    public static void main(String args[])throws IOException {
        go();
        boolean test_cases = true;
        int t = test_cases ? f.ni() : 1;
        while (t--> 0) solve();
        pw.flush();
        pw.close(); 
    }

    public static void go()throws IOException {
        if (System.getProperty("ONLINE_JUDGE") == null) {
            f = new FastScanner("");
        }
        else f = new FastScanner(System.in);
    }

    public static class FastScanner {
        StringTokenizer st;
        BufferedReader br;
 
        public FastScanner(InputStream s) {
            br = new BufferedReader(new InputStreamReader(s));
        }

        public FastScanner(String str) {
            try {
                br = new BufferedReader(new FileReader("inp.txt"));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
 
        public String next()throws IOException {
            while (st == null || !st.hasMoreTokens())
                st = new StringTokenizer(br.readLine());
            return st.nextToken();
        }
 
        public int ni() throws IOException {return Integer.parseInt(next());}
 
        public long nl() throws IOException {return Long.parseLong(next());}
 
        public String nextLine() throws IOException {return br.readLine();}

        public double nd() throws IOException {return Double.parseDouble(next());}
    }

    public static long mul(long a , long b) {
        return ((a % mod) * (b % mod)) % mod;
    }

    public static void sort(int a[]) {
        ArrayList<Integer> x = new ArrayList<>();
        for (int i : a) x.add(i);
        Collections.sort(x);
        for (int i = 0; i < a.length; ++i) a[i] = x.get(i);
    }
    
    public static void sort(long a[]) {
        ArrayList<Long> x = new ArrayList<>();
        for (Long i : a) x.add(i);
        Collections.sort(x);
        for (int i = 0; i < a.length; ++i) a[i] = x.get(i);
    }

    public static void pn(Object o){pw.println(o);}
        
    public static void p(Object o){pw.print(o);}
        
    public static void pni(Object o){pw.println(o);pw.flush();}
}