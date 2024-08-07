import java.util.*;
import java.lang.*;
import java.io.*;

public class Main
{
	PrintWriter out = new PrintWriter(System.out);
	BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer tok = new StringTokenizer("");
    String next() throws IOException {
        if (!tok.hasMoreTokens()) { tok = new StringTokenizer(in.readLine()); }
        return tok.nextToken();
    }
    int ni() throws IOException { return Integer.parseInt(next()); }
    long nl() throws IOException { return Long.parseLong(next()); }
    
    long mod=1000000007;
    
    void solve() throws IOException {
        for (int tc=ni();tc>0;tc--) {
            int n=ni();
            int[]A=new int[n+1];
            int[]C=new int[n+1];
            boolean perm=true;
            for (int i=1;i<=n;i++) {
                A[i]=ni();
                C[A[i]]++;
                if (C[A[i]]==2) perm=false;
            }
            
            int[]X=new int[n+1];
            if (C[1]>0) {
                X[n]=1;
                int p=1;
                int q=n;
                int curr=1;
                int j=n-1;
                while (curr<n) {
                    if (C[curr]>1) break;
                    if (C[curr+1]==0) break;
                    if (A[p]==curr) {
                        X[j]=1;
                        j--;
                        p++;
                        curr++;
                        continue;
                    }
                    if (A[q]==curr) {
                        X[j]=1;
                        j--;
                        q--;
                        curr++;
                        continue;
                    }
                    break;
                }
                
                if (perm) X[1]=1;
            }
            
            for (int i=1;i<=n;i++) out.print(X[i]);
            out.println();
            
        }
        out.flush();
    }
    
    int gcd(int a,int b) { return(b==0?a:gcd(b,a%b)); }
    long gcd(long a,long b) { return(b==0?a:gcd(b,a%b)); }
    long mp(long a,long p) { long r=1; while(p>0) { if ((p&1)==1) r=(r*a)%mod; p>>=1; a=(a*a)%mod; } return r; }
    
    public static void main(String[] args) throws IOException {
        new Main().solve();
    }
}