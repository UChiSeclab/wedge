import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class F {
	
	static class FastReader 
    { 
        BufferedReader br; 
        StringTokenizer st; 
  
        public FastReader() 
        { 
            br = new BufferedReader(new
                     InputStreamReader(System.in)); 
        } 
  
        String next() 
        { 
            while (st == null || !st.hasMoreElements()) 
            { 
                try
                { 
                    st = new StringTokenizer(br.readLine()); 
                } 
                catch (IOException  e) 
                { 
                    e.printStackTrace(); 
                } 
            } 
            return st.nextToken(); 
        } 
  
        int nextInt() 
        { 
            return Integer.parseInt(next()); 
        } 
  
        long nextLong() 
        { 
            return Long.parseLong(next()); 
        } 
  
        double nextDouble() 
        { 
            return Double.parseDouble(next()); 
        } 
  
        String nextLine() 
        { 
            String str = ""; 
            try
            { 
                str = br.readLine(); 
            } 
            catch (IOException e) 
            { 
                e.printStackTrace(); 
            } 
            return str; 
        } 
    }
	static class Node
	{
		int s;
		int f;
		int c;
		int r;
		Node(int s,int f,int c,int r)
		{
			this.s = s;
			this.f = f;
			this.c = c;
			this.r = r;
		}
	}
	static int a[];
	static boolean check(long ans,Node p)
	{	
		long curf = ans;
		int count = 0;
		for(int i = p.s+1; i <= p.f; i++)
		{
			if(curf - (a[i]-a[i-1])*p.c < 0)
			{
				return false;
			}
			curf -= (a[i]-a[i-1])*p.c;
			if(i < p.f && curf - (a[i+1]-a[i])*p.c < 0)
			{
				curf = ans;
				count++;
				if(count > p.r) return false;
			}
		}
		return true;
	}
	static long search(Node p){
        long l=1,r=1000000000;
        r*=r;
        long ans= 0;
        while(l<=r){
            long m=(l+r)>>1;
            if(check(m,p)){
                ans=m;
                r=m-1;
            }else l=m+1;
        }
        return ans;
    }
	public static void main(String[] args) 
	{
		OutputStream outputStream = System.out;
        FastReader sc = new FastReader();
        PrintWriter out = new PrintWriter(outputStream);
        int n = sc.nextInt();
        int m = sc.nextInt();
        a = new int[n+1];
        for(int i = 0; i < n; i++)
        	a[i] = sc.nextInt();
        
        ArrayList<Node> ar = new ArrayList<>();
        for(int i = 0; i < m; i++)
        	ar.add(new Node(sc.nextInt(),sc.nextInt(),sc.nextInt(),sc.nextInt()));
        
        Collections.shuffle(ar);
        long ans = 0;
        for(Node p: ar)
        {
        	if(!check(ans,p))
        	{
        		ans = search(p);
        	}
        }
        out.println(ans);
        out.close();
	}
	
}
