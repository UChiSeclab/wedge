			
import java.util.*;import java.io.*;import java.math.*;

public class Main
{
	static class Node{
		int idx,val;
		public Node(int idx,int val){
			this.idx=idx;
			this.val=val;
		}
	}

	static int arr[];
	static boolean visited[];
	static int dp[];

	static void bfs(Queue<Node> q,int n){
		while(!q.isEmpty()){
			Node nn=q.poll();

			int nxt=nn.val+nn.idx;

			if(nxt>0 && nxt<=n){

				if(dp[nxt]==-1)
					dp[nxt]=1+dp[nn.idx];
				else
					dp[nxt]=Math.min(dp[nxt], dp[nn.idx]+1);

				if(!visited[nxt]){
					visited[nxt]=true;
					q.add(new Node(nxt,arr[nxt]));
				}
			}

			nxt=nn.idx-nn.val;
			if(nxt>0 && nxt<=n){
				
				if(dp[nxt]==-1)
					dp[nxt]=1+dp[nn.idx];
				else
					dp[nxt]=Math.min(dp[nxt], dp[nn.idx]+1);

				if(!visited[nxt]){
					visited[nxt]=true;
					q.add(new Node(nxt,arr[nxt]));
				}
			}
		}

	}

    public static void process()throws IOException
    {
    	int n=ni(),res[]=new int[n+2];
    	arr=new int[n+2];
    	visited=new boolean[n+2];
    	dp=new int[n+2];

    	Arrays.fill(res,-1);

    	for(int i=1;i<=n;i++) arr[i]=ni();

		Queue<Node> q=new LinkedList<>();

		Arrays.fill(dp,-1);   	
		for(int i=1;i<=n;i++){
			if((arr[i] & 1)==0){
				q.add(new Node(i,arr[i]));
				dp[i]=0;
				visited[i]=true;
			}
		}

		bfs(q,n);
		for(int i=1;i<=n;i++)
			if((arr[i] & 1)==1 )
				res[i]=dp[i];


////////////////////////////////////////////////////////////////////////////////////////

		q.clear();
		Arrays.fill(dp,-1);   
		Arrays.fill(visited,false);	
		for(int i=1;i<=n;i++){
			if((arr[i] & 1)==1){
				q.add(new Node(i,arr[i]));
				dp[i]=0;
				visited[i]=true;
			}
		}
		bfs(q,n);
		for(int i=1;i<=n;i++)
			if((arr[i] & 1)==0)//res of even no.s
				res[i]=dp[i];


		for(int i=1;i<=n;i++)
			p(res[i]+" ");
		p("\n");
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
		//t=ni();
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
    static long mod=(long)1e9+7l;

    static void r_sort(int arr[],int n){
        Random r = new Random(); 
        for (int i = n-1; i > 0; i--){ 
            int j = r.nextInt(i+1); 
                  
            int temp = arr[i]; 
            arr[i] = arr[j]; 
            arr[j] = temp; 
        } 
        Arrays.sort(arr); 
    }

    static long mpow(long x, long n) {
        if(n == 0)
            return 1;
        if(n % 2 == 0) {
            long root = mpow(x, n / 2);
            return root * root % mod;
        }else {
            return x * mpow(x, n - 1) % mod;
        }
    }
    
    static long mcomb(long a, long b) {
        if(b > a - b)
            return mcomb(a, a - b);
        long m = 1;
        long d = 1;
        long i;
        for(i = 0; i < b; i++) {
            m *= (a - i);
            m %= mod;
            d *= (i + 1);
            d %= mod;
        }
        long ans = m * mpow(d, mod - 2) % mod;
        return ans;
    }
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