
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.*;
import java.util.Map.Entry;

public class Main {
	private static int gcd(int a, int b) {
		if(a<b)gcd(b,a);
		if(b==0)return a;
		return gcd(b,a%b);
	}
	static class FastReader {
        BufferedReader br;
        StringTokenizer st;
 
        public FastReader()
        {
            br = new BufferedReader(
                new InputStreamReader(System.in));
        } 
 
        String next()
        {
            while (st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }
 
        int nextInt() { return Integer.parseInt(next()); }
 
        long nextLong() { return Long.parseLong(next()); }
 
        double nextDouble()
        {
            return Double.parseDouble(next());
        }
 
        String nextLine()
        {
            String str = "";
            try {
                str = br.readLine();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            return str;
        }
    }
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FastReader s=new FastReader();
		StringBuilder s1 = new StringBuilder();
		int t=s.nextInt();
		while(t-->0)
		{
			int n=s.nextInt();
			String str=s.next();
			Map<String,Integer>map=new HashMap<>();
			int cntd=0,cntk=0;
			for(int i=0;i<str.length();i++)
			{
				char c=str.charAt(i);
				if(c=='D')++cntd;
				else ++cntk;
				int D=cntd/gcd(cntd,cntk);
		        int K=cntk/gcd(cntd,cntk);
				pair p=new pair(D,K);
		        s1.append(p.x+" "+p.y);
				map.put(s1.toString(), map.getOrDefault(s1.toString(), 0)+1);
				System.out.print(map.get(s1.toString())+" ");
			}
			System.out.println();
		}
	}
	

}

class pair{
	int x=0,y=0;
	public pair(int x,int y)
	{
		this.x=x;
		this.y=y;
	}
}

