import java.util.*;import java.io.*;import java.math.*;
public class Main
{
    public static void process()throws IOException
    {
        int n=ni();
        int[]A=nai(n);
        HashSet<Integer> set = new HashSet<Integer>();
        for(int i=0;i<n;i++)
            set.add(A[i]);
        int[]ans=new int[n];
        if(set.contains(1))
            ans[n-1]=1;
        else
        {
            for(int i=0;i<n;i++)
                p(0);
            pn("");
            return;
        }
        int l=0,r=n-1;
        int curr=1;
        while(l<=r)
        {
            if(A[l]==A[r])
                break;
            if(A[l]==curr && set.contains(curr+1))
            {
                l++;
                ans[r-l]=1;
            }
            else if(A[r]==curr && set.contains(curr+1))
            {
                r--;
                ans[r-l]=1;
            }
            else
                break;
            curr++;
        }
        Arrays.sort(A);
        int flag=0;
        for(int i=0;i<n;i++)
            if(A[i]!=i+1)
                flag=1;
        if(flag==0)
            ans[0]=1;
        for(int i=0;i<n;i++)
            p(ans[i]);
        pn("");
    }

    static AnotherReader sc;
    static PrintWriter out;
    public static void main(String[]args)throws IOException
    {
        boolean oj = System.getProperty("ONLINE_JUDGE") != null;
        if(oj){sc=new AnotherReader();out=new PrintWriter(System.out);}
        else{sc=new AnotherReader(100);out=new PrintWriter("output.txt");}
        int t=1;
        t=ni();
        while(t-->0) {process();}
        out.flush();out.close();  
    }

    static void pn(Object o){out.println(o);}
    static void p(Object o){out.print(o);}
    static void pni(Object o){out.println(o);out.flush();}
    static int ni()throws IOException{return sc.nextInt();}
    static long nl()throws IOException{return sc.nextLong();}
    static double nd()throws IOException{return sc.nextDouble();}
    static String nln()throws IOException{return sc.nextLine();}
    static int[] nai(int N)throws IOException{int[]A=new int[N];for(int i=0;i!=N;i++){A[i]=ni();}return A;}
    static long[] nal(int N)throws IOException{long[]A=new long[N];for(int i=0;i!=N;i++){A[i]=nl();}return A;}
    static long gcd(long a, long b)throws IOException{return (b==0)?a:gcd(b,a%b);}
    static int gcd(int a, int b)throws IOException{return (b==0)?a:gcd(b,a%b);}
    static int bit(long n)throws IOException{return (n==0)?0:(1+bit(n&(n-1)));}

/////////////////////////////////////////////////////////////////////////////////////////////////////////

    static class AnotherReader{BufferedReader br; StringTokenizer st;
    AnotherReader()throws FileNotFoundException{
    br=new BufferedReader(new InputStreamReader(System.in));}
    AnotherReader(int a)throws FileNotFoundException{
    br = new BufferedReader(new FileReader("input.txt"));}
    String next()throws IOException{
    while (st == null || !st.hasMoreElements()) {try{
    st = new StringTokenizer(br.readLine());}
    catch (IOException  e){ e.printStackTrace(); }}
    return st.nextToken(); } int nextInt() throws IOException{
    return Integer.parseInt(next());}
    long nextLong() throws IOException
    {return Long.parseLong(next());}
    double nextDouble()throws IOException { return Double.parseDouble(next()); }
    String nextLine() throws IOException{ String str = ""; try{
    str = br.readLine();} catch (IOException e){
    e.printStackTrace();} return str;}}
   
/////////////////////////////////////////////////////////////////////////////////////////////////////////////
}