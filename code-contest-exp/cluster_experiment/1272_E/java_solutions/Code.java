import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.*;
public class Code {
	static int[][] dp = new int[200005][2];
	public static int f(int[] arr,int i,int a) {
		if(arr[i]%2!=a%2)
			return 0;
		if(dp[i][a%2]!=-1)return dp[i][a%2];
		int m = Integer.MAX_VALUE;
		int n = Integer.MAX_VALUE;
		boolean t = false;
		if(i+arr[i]<arr.length && arr[i+arr[i]]!=a) {
			m = 1+f(arr,i+arr[i],arr[i]);
			t = true;
		}
		if(i-arr[i]>=0 && arr[i-arr[i]]!=a) {
			t= true;
			n = 1+f(arr,i-arr[i],arr[i]);
		//	System.out.println(n);
		}
		if(t)
			return dp[i][a%2] = Math.min(m, n);
		return Integer.MIN_VALUE;
	}
	public static void main(String[] args) throws IOException{
		Scanner sc = new Scanner(System.in);
		PrintWriter pw = new PrintWriter(System.out);
		int n = sc.nextInt();
		int[] arr = new int[n];
		for(int i=0;i<n;i++)arr[i]=sc.nextInt();
		for(int i=0;i<dp.length;i++)
			for(int j=0;j<dp[i].length;j++)
				dp[i][j] = -1;
		for(int i=0;i<n;i++)
			pw.print(Math.max(f(arr,i,arr[i]),-1) + " ");
		pw.flush();
		pw.close();
	}
}
class Scanner {
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
