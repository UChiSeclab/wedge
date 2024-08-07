				
import java.util.*;import java.io.*;import java.math.*;

public class Main
{
	static void randomize( int arr[], int n) 
    { 
       
        Random r = new Random();  
        for (int i = n-1; i > 0; i--) { 
             
            int j = r.nextInt(i+1); 
               
            int temp = arr[i]; 
            arr[i] = arr[j]; 
            arr[j] = temp; 
        } 
    }

    public static void process()throws IOException
    {
    	int n=ni(),arr[]=new int[n+1];
    	HashSet<Integer> set1 = new HashSet<Integer>();
    	for(int i=1;i<=n;i++) set1.add(arr[i]=ni());

    	randomize(arr,n+1);
    	Arrays.sort(arr);
    	
    	HashSet<Integer> set2 = new HashSet<Integer>();

    	for(int i=n;i>=1;i--){
    		if(!set2.contains(arr[i]+1)){
    			set2.add(arr[i]+1);
    			continue;
    		}

    		if(!set1.contains(arr[i]-2) && arr[i]>2 && !set2.contains(arr[i]-1)){
    			set2.add(arr[i]-1);
    			continue;
    		}

    		set2.add(arr[i]);
    	}

    	pn(set2.size());
    	//pn(set2);
    }


   	static AnotherReader sc;
    static PrintWriter out;
    public static void main(String[]args)throws IOException
    {
        out = new PrintWriter(System.out);
        sc=new AnotherReader();
        boolean oj = true;

    	oj = System.getProperty("ONLINE_JUDGE") != null;
    	if(!oj) sc=new AnotherReader(100);

        long s = System.currentTimeMillis();
        int t=1;
        while(t-->0)
            process();
        out.flush();
        if(!oj)
            System.out.println(System.currentTimeMillis()-s+"ms");
        System.out.close();  
    }

    static void pn(Object o){out.println(o);}
    static void p(Object o){out.print(o);}
    static void pni(Object o){out.println(o);System.out.flush();}
    static int ni()throws IOException{return sc.nextInt();}
    static long nl()throws IOException{return sc.nextLong();}
    static double nd()throws IOException{return sc.nextDouble();}
    static String nln()throws IOException{return sc.nextLine();}
    static long gcd(long a, long b)throws IOException{return (b==0)?a:gcd(b,a%b);}
    static int gcd(int a, int b)throws IOException{return (b==0)?a:gcd(b,a%b);}
    static int bit(long n)throws IOException{return (n==0)?0:(1+bit(n&(n-1)));}
    static boolean multipleTC=false;



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
	
	
	