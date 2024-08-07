import java.io.*;import java.util.*;import java.math.*;
public class Main
{
	public void tq()throws Exception
	{
		st=new StringTokenizer(bq.readLine());
		int tq=i();
		sb=new StringBuilder(1000000);
		o:
		while(tq-->0)
		{
			int n=i();
			String s=s();
			HashMap<Integer,Integer> h=new HashMap<>();
			int a=0;
			int b=0;
			int i=-1;
			int f[]=new int[n];
			for(char c:s.toCharArray())
			{
				i++;
				if(c=='D')a++;
				else b++;
				if(a==0||b==0)
				{
					f[i]=i+1;
					continue;
				}
				int v=gcd(a,b);
				v=(a/v)*1000000+(b/v);
				Integer aa=h.get(v);
				if(aa==null)
				{
					h.put(v,1);
					f[i]=1;
				}
				else
				{
					h.put(v,aa+1);
					f[i]=aa+1;
				}
				//s(f);
			}
			s(f);
		}
		p(sb);
	}
    
    long mod=1000000007l;int max=Integer.MAX_VALUE,min=Integer.MIN_VALUE;
    long maxl=Long.MAX_VALUE,minl=Long.MIN_VALUE;
    BufferedReader bq=new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st;StringBuilder sb;
    public static void main(String[] a)throws Exception{new Main().tq();}
    int[] so(int ar[]){Integer r[]=new Integer[ar.length];for(int x=0;x<ar.length;x++)r[x]=ar[x];
    Arrays.sort(r);for(int x=0;x<ar.length;x++)ar[x]=r[x];return ar;}
    long[] so(long ar[]){Long r[]=new Long[ar.length];for(int x=0;x<ar.length;x++)r[x]=ar[x];
    Arrays.sort(r);for(int x=0;x<ar.length;x++)ar[x]=r[x];return ar;}
    char[] so(char ar[])
    {Character r[]=new Character[ar.length];for(int x=0;x<ar.length;x++)r[x]=ar[x];
    Arrays.sort(r);for(int x=0;x<ar.length;x++)ar[x]=r[x];return ar;}
    void s(String s){sb.append(s);}void s(int s){sb.append(s);}void s(long s){sb.append(s);}
    void s(char s){sb.append(s);}void s(double s){sb.append(s);}
    void ss(){sb.append(' ');}void sl(String s){sb.append(s);sb.append("\n");}
    void sl(int s){sb.append(s);sb.append("\n");}
    void sl(long s){sb.append(s);sb.append("\n");}void sl(char s){sb.append(s);sb.append("\n");}
    void sl(double s){sb.append(s);sb.append("\n");}void sl(){sb.append("\n");}
    int l(int v){return 31-Integer.numberOfLeadingZeros(v);}
    long l(long v){return 63-Long.numberOfLeadingZeros(v);}
    int min(int a,int b){return a<b?a:b;}
    int min(int a,int b,int c){return a<b?a<c?a:c:b<c?b:c;}
    int max(int a,int b){return a>b?a:b;}
    int max(int a,int b,int c){return a>b?a>c?a:c:b>c?b:c;}
    long min(long a,long b){return a<b?a:b;}
    long min(long a,long b,long c){return a<b?a<c?a:c:b<c?b:c;}
    long max(long a,long b){return a>b?a:b;}
    long max(long a,long b,long c){return a>b?a>c?a:c:b>c?b:c;}
    int abs(int a){return Math.abs(a);}
    long abs(long a){return Math.abs(a);}
    int sq(int a){return (int)Math.sqrt(a);}long sq(long a){return (long)Math.sqrt(a);}
    long gcd(long a,long b){while(b>0l){long c=a%b;a=b;b=c;}return a;}
    int gcd(int a,int b){while(b>0){int c=a%b;a=b;b=c;}return a;}
    boolean pa(String s,int i,int j){while(i<j)if(s.charAt(i++)!=s.charAt(j--))return false;return true;}
    boolean[] si(int n)
    {boolean bo[]=new boolean[n+1];bo[0]=true;bo[1]=true;for(int x=4;x<=n;x+=2)bo[x]=true;
    for(int x=3;x*x<=n;x+=2){if(!bo[x]){int vv=(x<<1);for(int y=x*x;y<=n;y+=vv)bo[y]=true;}}
    return bo;}long mul(long a,long b,long m) 
    {long r=1l;a%=m;while(b>0){if((b&1)==1)r=(r*a)%m;b>>=1;a=(a*a)%m;}return r;}
    int i()throws IOException{if(!st.hasMoreTokens())st=new StringTokenizer(bq.readLine());
    return Integer.parseInt(st.nextToken());}
    long l()throws IOException{if(!st.hasMoreTokens())st=new StringTokenizer(bq.readLine());
    return Long.parseLong(st.nextToken());}String s()throws IOException
    {if(!st.hasMoreTokens())st=new StringTokenizer(bq.readLine());return st.nextToken();}
    double d()throws IOException{if(!st.hasMoreTokens())st=new StringTokenizer(bq.readLine());
    return Double.parseDouble(st.nextToken());}void p(Object p){System.out.print(p);}
    void p(String p){System.out.print(p);}void p(int p){System.out.print(p);}
    void p(double p){System.out.print(p);}void p(long p){System.out.print(p);}
    void p(char p){System.out.print(p);}void p(boolean p){System.out.print(p);}
    void pl(Object p){System.out.println(p);}void pl(String p){System.out.println(p);}
    void pl(int p){System.out.println(p);}void pl(char p){System.out.println(p);}
    void pl(double p){System.out.println(p);}void pl(long p){System.out.println(p);}
    void pl(boolean p){System.out.println(p);}void pl(){System.out.println();}
    void s(int a[]){for(int e:a){sb.append(e);sb.append(' ');}sb.append("\n");}
    void s(long a[]){for(long e:a){sb.append(e);sb.append(' ');}sb.append("\n");}
    void s(int ar[][]){for(int a[]:ar){for(int e:a){sb.append(e);sb.append(' ');}sb.append("\n");}}
    void s(char a[]){for(char e:a){sb.append(e);sb.append(' ');}sb.append("\n");}
    void s(char ar[][]){for(char a[]:ar){for(char e:a){sb.append(e);sb.append(' ');}sb.append("\n");}}
    int[] ari(int n)throws IOException
    {int ar[]=new int[n];if(!st.hasMoreTokens())st=new StringTokenizer(bq.readLine());
    for(int x=0;x<n;x++)ar[x]=Integer.parseInt(st.nextToken());return ar;}
    int[][] ari(int n,int m)throws IOException
    {int ar[][]=new int[n][m];for(int x=0;x<n;x++){if(!st.hasMoreTokens())st=new StringTokenizer(bq.readLine());
    for(int y=0;y<m;y++)ar[x][y]=Integer.parseInt(st.nextToken());}return ar;}
    long[] arl(int n)throws IOException
    {long ar[]=new long[n];if(!st.hasMoreTokens())st=new StringTokenizer(bq.readLine());
    for(int x=0;x<n;x++) ar[x]=Long.parseLong(st.nextToken());return ar;}
    long[][] arl(int n,int m)throws IOException
    {long ar[][]=new long[n][m];for(int x=0;x<n;x++)
    {if(!st.hasMoreTokens())st=new StringTokenizer(bq.readLine());
    for(int y=0;y<m;y++)ar[x][y]=Long.parseLong(st.nextToken());}return ar;}
    String[] ars(int n)throws IOException
    {String ar[]=new String[n];if(!st.hasMoreTokens())st=new StringTokenizer(bq.readLine());
    for(int x=0;x<n;x++) ar[x]=st.nextToken();return ar;}
    double[] ard(int n)throws IOException
    {double ar[]=new double[n];if(!st.hasMoreTokens())st=new StringTokenizer(bq.readLine());
    for(int x=0;x<n;x++)ar[x]=Double.parseDouble(st.nextToken());return ar;}
    double[][] ard(int n,int m)throws IOException
    {double ar[][]=new double[n][m];for(int x=0;x<n;x++)
    {if(!st.hasMoreTokens())st=new StringTokenizer(bq.readLine());
    for(int y=0;y<m;y++)ar[x][y]=Double.parseDouble(st.nextToken());}return ar;}
    char[] arc(int n)throws IOException{char ar[]=new char[n];if(!st.hasMoreTokens())st=new StringTokenizer(bq.readLine());
    for(int x=0;x<n;x++)ar[x]=st.nextToken().charAt(0);return ar;}
    char[][] arc(int n,int m)throws IOException{char ar[][]=new char[n][m];
    for(int x=0;x<n;x++){String s=bq.readLine();for(int y=0;y<m;y++)ar[x][y]=s.charAt(y);}return ar;}
    void p(int ar[]){StringBuilder sb=new StringBuilder(2*ar.length);
    for(int a:ar){sb.append(a);sb.append(' ');}System.out.println(sb);}
    void p(int ar[][]){StringBuilder sb=new StringBuilder(2*ar.length*ar[0].length);
    for(int a[]:ar){for(int aa:a){sb.append(aa);sb.append(' ');}sb.append("\n");}p(sb);}
    void p(long ar[]){StringBuilder sb=new StringBuilder(2*ar.length);
    for(long a:ar){sb.append(a);sb.append(' ');}System.out.println(sb);}
    void p(long ar[][]){StringBuilder sb=new StringBuilder(2*ar.length*ar[0].length);
    for(long a[]:ar){for(long aa:a){sb.append(aa);sb.append(' ');}sb.append("\n");}p(sb);}
    void p(String ar[]){int c=0;for(String s:ar)c+=s.length()+1;StringBuilder sb=new StringBuilder(c);
    for(String a:ar){sb.append(a);sb.append(' ');}System.out.println(sb);}
    void p(double ar[]){StringBuilder sb=new StringBuilder(2*ar.length);
    for(double a:ar){sb.append(a);sb.append(' ');}System.out.println(sb);}
    void p(double ar[][]){StringBuilder sb=new StringBuilder(2*ar.length*ar[0].length);
    for(double a[]:ar){for(double aa:a){sb.append(aa);sb.append(' ');}sb.append("\n");}p(sb);}
    void p(char ar[]){StringBuilder sb=new StringBuilder(2*ar.length);
    for(char aa:ar){sb.append(aa);sb.append(' ');}System.out.println(sb);}
    void p(char ar[][]){StringBuilder sb=new StringBuilder(2*ar.length*ar[0].length);
    for(char a[]:ar){for(char aa:a){sb.append(aa);sb.append(' ');}sb.append("\n");}p(sb);}
}