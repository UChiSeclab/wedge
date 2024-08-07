import java.util.*;
import java.io.*;

public class Div688A {
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////                                                                                                               /////////
////////                                                                                                               /////////
////////   HHHH        HHHH  EEEEEEEEEEEEE   MMMM          MMMM         OOOOOO             SSSSSSS      EEEEEEEEEEEEE  /////////
////////   HHHH        HHHH  EEEEEEEEEEEEE   MMMMMM      MMMMMM      OOO      OOO        SSSS   SSS     EEEEEEEEEEEEE  /////////
////////   HHHH        HHHH  EEEEE           MMMM MMM  MMM MMMM    OOO          OOO    SSSS       SSS   EEEEE          /////////
////////   HHHH        HHHH  EEEEE           MMMM  MMMMMM  MMMM   OOO            OOO   SSSS             EEEEE          /////////
////////   HHHH        HHHH  EEEEE           MMMM          MMMM  OOO              OOO   SSSSSSS         EEEEE          /////////
////////   HHHHHHHHHHHHHHHH  EEEEEEEEEEE     MMMM          MMMM  OOO              OOO      SSSSSS       EEEEEEEEEEE    /////////
////////   HHHHHHHHHHHHHHHH  EEEEEEEEEEE     MMMM          MMMM  OOO              OOO         SSSSSSS   EEEEEEEEEEE    /////////
////////   HHHH        HHHH  EEEEE           MMMM          MMMM   OOO            OOO              SSSS  EEEEE          /////////
////////   HHHH        HHHH  EEEEE           MMMM          MMMM    OOO          OOO     SSS       SSSS  EEEEE          /////////
////////   HHHH        HHHH  EEEEEEEEEEEEE   MMMM          MMMM      OOO      OOO        SSS    SSSS    EEEEEEEEEEEEE  /////////
////////   HHHH        HHHH  EEEEEEEEEEEEE   MMMM          MMMM         OOOOOO             SSSSSSS      EEEEEEEEEEEEE  /////////
////////                                                                                                               /////////
////////                                                                                                               /////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        PrintWriter pw = new PrintWriter(System.out);
        int t = sc.nextInt();
        while (t-->0){
            int n = sc.nextInt();
            int[] a= sc.nextIntArr(n);
            int[]ans = new int[n];
            SparseTable min = new SparseTable(a);
            int need= 1;
            int l =0,r=n-1;
            while (l<=r){
                if (a[l]==need||a[r]==need){
                    if (min.query(l,r)!=need)
                        break;
                    ans[n-need]=1;
                    if (a[l]==need)
                        l++;
                    else r--;
                }else {
                    if (min.query(l,r)==need){
                        ans[n-need]=1;
                    }
                    break;
                }
                need++;
            }
           HashSet<Integer>hs = new HashSet<>();
            for (int x:a)
                hs.add(x);
            if (hs.size()==n)ans[0]=1;
            for (int x:ans)
                pw.print(x);
            pw.println();
        }

        pw.flush();
    }
    static class SparseTable {

        int A[], SpT[][];

        SparseTable(int[] A)
        {
            int n = A.length;	this.A = A;
            int k = (int)Math.floor(Math.log(n) / Math.log(2)) + 1;
            SpT = new int[n][k];

            for (int i = 0; i < n; i++)
                SpT[i][0] = i; 					// RMQ of sub array starting at index i and of length 2^0=1

            //overall time complexity = O(n log n)
            for (int j = 1; (1<<j) <= n; j++)
                for (int i = 0; i + (1<<j) - 1 < n; i++)
                    if (A[SpT[i][j-1]] < A[SpT[i+(1<<(j-1))][j-1]])
                        SpT[i][j] = SpT[i][j-1];    		// start at index i of length 2^(j-1)
                    else                  					// start at index i+2^(j-1) of length 2^(j-1)
                        SpT[i][j] = SpT[i+(1<<(j-1))][j-1];
        }

        int query(int i, int j)
        {

            int k = (int)Math.floor(Math.log(j-i+1) / Math.log(2)); // 2^k <= (j-i+1)

            if (A[SpT[i][k]] <= A[SpT[j-(1<<k)+1][k]])
                return A[SpT[i][k]];
            else
                return A[SpT[j-(1<<k)+1][k]];
        }
    }

    static class Scanner {
        StringTokenizer st;
        BufferedReader br;

        public Scanner(FileReader r) {
            br = new BufferedReader(r);
        }

        public Scanner(InputStream s) {
            br = new BufferedReader(new InputStreamReader(s));
        }

        public String next() throws IOException {
            while (st == null || !st.hasMoreTokens())
                st = new StringTokenizer(br.readLine());
            return st.nextToken();
        }

        public int[] nextIntArr(int n) throws IOException {
            int[] a = new int[n];
            for (int i = 0; i < n; i++) {
                a[i] = nextInt();
            }
            return a;
        }

        public int nextInt() throws IOException {
            return Integer.parseInt(next());
        }

        public long nextLong() throws IOException {
            return Long.parseLong(next());
        }

        public String nextLine() throws IOException {
            return br.readLine();
        }

        public double nextDouble() throws IOException {
            String x = next();
            StringBuilder sb = new StringBuilder("0");
            double res = 0, f = 1;
            boolean dec = false, neg = false;
            int start = 0;
            if (x.charAt(0) == '-') {
                neg = true;
                start++;
            }
            for (int i = start; i < x.length(); i++)
                if (x.charAt(i) == '.') {
                    res = Long.parseLong(sb.toString());
                    sb = new StringBuilder("0");
                    dec = true;
                } else {
                    sb.append(x.charAt(i));
                    if (dec)
                        f *= 10;
                }
            res += Long.parseLong(sb.toString()) / f;
            return res * (neg ? -1 : 1);
        }

        public boolean ready() throws IOException {
            return br.ready();
        }
    }

}
