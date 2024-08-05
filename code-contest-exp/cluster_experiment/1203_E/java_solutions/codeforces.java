import java.io.*;
import java.math.*;
import java.util.*;
@SuppressWarnings("Duplicates")
// author @mdazmat9
public class codeforces{
    static Scanner sc = new Scanner(System.in);
    static PrintWriter out = new PrintWriter(System.out);
    public static void main(String[] args) throws IOException {
        int test = 1;
      //  test=sc.nextInt();
        for (int ind = 0; ind < test; ind++) {
            int n=sc.nextInt();
            int[]a=intarray(n);
            shuffle(a);
            Arrays.sort(a);
            int[] dp=new int[150000 +2];
            for(int i=0;i<n;i++){
                if(a[i]!=1){
                    if(dp[a[i]-1]==0)dp[a[i]-1]=1;
                    else if(dp[a[i]]==0)dp[a[i]]=1;
                    else if(dp[a[i]+1]==0)dp[a[i]+1]=1;
                }
                else {
                        if(dp[a[i]]==0)dp[a[i]]=1;
                    else if(dp[a[i]+1]==0)dp[a[i]+1]=1;
                }
              //  out.println(set);
            }
            int count=0;
            for(int num : dp){
                if(num==1)count++;
            }
            out.println(count);
        }
        out.flush();
    }
 
 
 
    static int[] intarray(int n){ int [] a=new int[n];for(int i=0;i<n;i++)a[i]=sc.nextInt();return a; }
    static ArrayList<Integer> intlist(int n){ArrayList<Integer> list=new ArrayList<>();for(int i=0;i<n;i++)list.add(sc.nextInt());return list; }
    static ArrayList<Long> longlist(int n){ArrayList<Long> list=new ArrayList<>();for(int i=0;i<n;i++)list.add(sc.nextLong());return list; }
    static int[][] read2darray(int n,int m){ int [][] a=new int[n][m];for(int i=0;i<n;i++){ for(int j=0;j<m;j++){ a[i][j]=sc.nextInt(); } }return a; }
    public static double logb( double a, double b ) {return Math.log(a) / Math.log(b); }
    static long fast_pow(long a, long b,long abs) {
        if(b == 0) return 1L;
        long val = fast_pow(a, b / 2,abs);
        if(b % 2 == 0) return val * val % abs;
        else return val * val % abs * a % abs;
    }
    static long abs = (long)1e9 + 7;
    static   void shuffle(int[] a) { int n = a.length;for(int i = 0; i < n; i++) { int r = i + (int) (Math.random() * (n - i));int tmp = a[i];a[i] = a[r];a[r] = tmp; } }
    static long gcd(long a , long b) {
        if(b == 0) return a;
        return gcd(b , a % b);
    }
}
 
class Scanner {
    public BufferedReader reader;
    public StringTokenizer st;
    public Scanner(InputStream stream) {
        reader = new BufferedReader(new InputStreamReader(stream));
        st = null;
    }
    public String next() {
        while (st == null || !st.hasMoreTokens()) {
            try {
                String line = reader.readLine();
                if (line == null) return null;
                st = new StringTokenizer(line);
            } catch (Exception e) {
                throw (new RuntimeException());
            }
        }
        return st.nextToken();
    }
    public int nextInt() {
        return Integer.parseInt(next());
    }
    public long nextLong() {
        return Long.parseLong(next());
    }
    public double nextDouble() {
        return Double.parseDouble(next());
    }
}