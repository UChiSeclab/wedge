//package sept;

import java.io.*;
import java.util.*;


public class TimePass implements Runnable {
    InputStream is;
    PrintWriter out;
    String INPUT = "";
    //boolean debug=false;
    boolean debug=true;
    static long mod=998244353;
    static long mod2=1000000007;
    
    void solve() throws IOException
	{
	    int n=ni();
	    int[] a=na(n);
	    int cnt=1;
	    ArrayList<Integer> l=new ArrayList<>();
	    for(int i=1;i<n;i++)
	    {
	        if(a[i]==a[i-1])
	        {
	            cnt++;
	        }
	        else
	        {
	            l.add(cnt);
	            cnt=1;
	        }
	    }
	    
	    l.add(cnt);
	    //out.println(l.toString());
	    int ans=0;
	    for(int i=1;i<l.size();i++)
	    {
	        ans=2*Math.min(l.get(i),l.get(i-1));
	    }
	    out.println(ans);
	}
	
	static long fnc(int a,int b)
	{
	    return a+(long)1000000007*b;
	}
	
	static Pair[][] packU(int n,int[] from,Pair[] to)
	{
		Pair[][] g=new Pair[n][];
		int[] p=new int[n];
		for(int f:from)
		{
			p[f]++;
		}
		int m=from.length;
		for(int i=0;i<n;i++)
		{
			g[i]=new Pair[p[i]];
		}
		for(int i=0;i<m;i++)
		{
			g[from[i]][--p[from[i]]]=to[i];
		}
		return g;
	}

	static int[][] packD(int n,int[] from,int[] to)
	{
		int[][] g=new int[n][];
		int[] p=new int[n];
		for(int f:from)
		{
			p[f]++;
		}
		int m=from.length;
		for(int i=0;i<n;i++)
		{
			g[i]=new int[p[i]];
		}
		for(int i=0;i<m;i++)
		{
			g[from[i]][--p[from[i]]]=to[i];
		}
		return g;
	}
	    
    static class Pair
    {
        int a,b,c;
        public Pair(int a,int b
        //,int c
        )
        {
            this.a=a;
            this.b=b;
            //this.c=c;
        }
    }
    
    static long lcm(int a,int b)
    {
        long val=a;
        val*=b;
        return (val/gcd(a,b));
    }
    
    static long gcd(long a,long b)
    {
        if(a==0)return b;
        return gcd(b%a,a);
    }
    
    static int pow(int a, int b, int p)
    {
        long ans = 1, base = a;
        while (b!=0)
        {
            if ((b & 1)!=0)
            {
                ans *= base;
                ans%= p;
            }
            base *= base;
            base%= p;
            b >>= 1;
        }
        return (int)ans;
    }

    static int inv(int x, int p)
    {
        return pow(x, p - 2, p);
    }
    
    public static long[] radixSort(long[] f){ return radixSort(f, f.length); }
	public static long[] radixSort(long[] f, int n)
	{
		long[] to = new long[n];
		{
			int[] b = new int[65537];
			for(int i = 0;i < n;i++)b[1+(int)(f[i]&0xffff)]++;
			for(int i = 1;i <= 65536;i++)b[i]+=b[i-1];
			for(int i = 0;i < n;i++)to[b[(int)(f[i]&0xffff)]++] = f[i];
			long[] d = f; f = to;to = d;
		}
		{
			int[] b = new int[65537];
			for(int i = 0;i < n;i++)b[1+(int)(f[i]>>>16&0xffff)]++;
			for(int i = 1;i <= 65536;i++)b[i]+=b[i-1];
			for(int i = 0;i < n;i++)to[b[(int)(f[i]>>>16&0xffff)]++] = f[i];
			long[] d = f; f = to;to = d;
		}
		{
			int[] b = new int[65537];
			for(int i = 0;i < n;i++)b[1+(int)(f[i]>>>32&0xffff)]++;
			for(int i = 1;i <= 65536;i++)b[i]+=b[i-1];
			for(int i = 0;i < n;i++)to[b[(int)(f[i]>>>32&0xffff)]++] = f[i];
			long[] d = f; f = to;to = d;
		}
		{
			int[] b = new int[65537];
			for(int i = 0;i < n;i++)b[1+(int)(f[i]>>>48&0xffff)]++;
			for(int i = 1;i <= 65536;i++)b[i]+=b[i-1];
			for(int i = 0;i < n;i++)to[b[(int)(f[i]>>>48&0xffff)]++] = f[i];
			long[] d = f; f = to;to = d;
		}
		return f;
	}

    
    public void run()
    {
        if(debug)oj=true;
        is = oj ? System.in : new ByteArrayInputStream(INPUT.getBytes());
        out = new PrintWriter(System.out);
        
        long s = System.currentTimeMillis();
        try {
			solve();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        out.flush();
        tr(System.currentTimeMillis()-s+"ms");
    }
    
    public static void main(String[] args) throws Exception {new Thread(null,new TimePass(),"Main",1<<26).start();}
    
    private byte[] inbuf = new byte[1024];
    public int lenbuf = 0, ptrbuf = 0;
    
    private int readByte()
    {
        if(lenbuf == -1)throw new InputMismatchException();
        if(ptrbuf >= lenbuf){
            ptrbuf = 0;
            try { lenbuf = is.read(inbuf); } catch (IOException e) { throw new InputMismatchException(); }
            if(lenbuf <= 0)return -1;
        }
        return inbuf[ptrbuf++];
    }
    
    private boolean isSpaceChar(int c) { return !(c >= 33 && c <= 126); }
    private int skip() { int b; while((b = readByte()) != -1 && isSpaceChar(b)); return b; }
    
    private double nd() { return Double.parseDouble(ns()); }
    private char nc() { return (char)skip(); }
    
    private String ns()
    {
        int b = skip();
        StringBuilder sb = new StringBuilder();
        while(!(isSpaceChar(b))){ // when nextLine, (isSpaceChar(b) && b != ' ')
            sb.appendCodePoint(b);
            b = readByte();
        }
        return sb.toString();
    }
    
    private char[] ns(int n)
    {
        char[] buf = new char[n];
        int b = skip(), p = 0;
        while(p < n && !(isSpaceChar(b))){
            buf[p++] = (char)b;
            b = readByte();
        }
        return n == p ? buf : Arrays.copyOf(buf, p);
    }
    
    private char[][] nm(int n, int m)
    {
        char[][] map = new char[n][];
        for(int i = 0;i < n;i++)map[i] = ns(m);
        return map;
    }
    
    private int[] na(int n)
    {
        int[] a = new int[n];
        for(int i = 0;i < n;i++)a[i] = ni();
        return a;
    }
    
    private int ni()
    {
        int num = 0, b;
        boolean minus = false;
        while((b = readByte()) != -1 && !((b >= '0' && b <= '9') || b == '-'));
        if(b == '-'){
            minus = true;
            b = readByte();
        }
        
        while(true){
            if(b >= '0' && b <= '9'){
                num = num * 10 + (b - '0');
            }else{
                return minus ? -num : num;
            }
            b = readByte();
        }
    }
    
    private long nl()
    {
        long num = 0;
        int b;
        boolean minus = false;
        while((b = readByte()) != -1 && !((b >= '0' && b <= '9') || b == '-'));
        if(b == '-'){
            minus = true;
            b = readByte();
        }
        
        while(true){
            if(b >= '0' && b <= '9'){
                num = num * 10 + (b - '0');
            }else{
                return minus ? -num : num;
            }
            b = readByte();
        }
    }
    
    private boolean oj = System.getProperty("ONLINE_JUDGE") != null;
    private void tr(Object... o) { if(!oj)System.out.println(Arrays.deepToString(o)); }
}