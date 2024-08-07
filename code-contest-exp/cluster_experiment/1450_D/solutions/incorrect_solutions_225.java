import java.io.*;
import java.util.*;
public class prob2
{
    static PrintWriter out=new PrintWriter((System.out));
    static int val;

    static Reader sc=new Reader();
    public static void main(String args[])throws IOException
    {
        
        int t=sc.nextInt();
        //ArrayList<Integer> primes=sieve(10000000);
        while(t-- >0){
            solve();
        }
        
    	out.close();
 
    }
    static void solve(){
        int n=sc.nextInt();
        int[] arr=new int[n];
        for(int i=0;i<n;i++) arr[i]=sc.nextInt();
        int red=0;
        for(int i=0;i<n-1;i++){
            if(arr[i]==arr[i+1]){
                int curr=1;
                while(i<n-1 && arr[i]==arr[i+1]){
                    i++;
                    curr++;
                }  
                red=Math.max(curr,red);
            }
        }

        StringBuilder sb=new StringBuilder();
        for(int k=1;k<=n;k++){
            if(red>0){
                if(k<red){
                    sb.append("0");
                    continue;
                }
            }
            int len=n-k+1;
            boolean ok=true;
            HashSet<Integer> hs=new HashSet<>();
            Deque<Integer> dq=new ArrayDeque<>();
            for(int i=0;i<k;i++){
                while (!dq.isEmpty() && dq.peekLast() >arr[i]){
                    dq.pollLast();
                }
                dq.addLast(arr[i]);
            }
            hs.add(dq.peekFirst());
            for(int i=k;i<n;i++){
                if(dq.peekFirst()==arr[i-k]){
                    dq.pollFirst();
                }
                while (!dq.isEmpty() && dq.peekLast() >arr[i]){
                    dq.pollLast();
                }
                dq.addLast(arr[i]);
                int curr=dq.peekFirst();
                if(hs.contains(curr) || curr>len){
                    ok=false;
                    break;
                }
            }
            if(ok) sb.append("1");
            else sb.append("0");
        }
        out.println(sb);
    }


    static ArrayList<Integer> sieve(int n) {
        ArrayList<Integer> primes=new ArrayList<>();
        boolean[] isPrime=new boolean[n];
        Arrays.fill(isPrime,true);
        isPrime[0]=isPrime[1]=false;
        for(int i=2;i*i<=n;i++){
            if(isPrime[i]){
                for(int j=i*i;j<n;j+=i){
                    isPrime[j]=false;
                }
            }
        }
        for(int i=2;i<n;i++){
            if(isPrime[i]) primes.add(i);
        }
        return primes;
    }
    public static boolean possible(int i,int j,int n,int m){
        return (i>=0 && j>=0 && i<n && j<m);
    }
    /*
    private static int dfs(ArrayList<Integer>[] adj,int i,int[] nodes){
        //out.println(val);
        if(adj[i].size()==0){
            if(val==0){
                val++;
                nodes[i]=1;
                return 1;
            }else{
                val++;
                nodes[i]=1;
                return 0;
            }
        }
        nodes[i]=1;
        int ans=0;
        for(int j:adj[i]){
            ans+=dfs(adj,j,nodes);
            nodes[i]+=nodes[j];
        }
        val++;
        if(nodes[i]==val){
            ans+=val;
        }
        out.println(ans);
        return ans;
    }
    */ 
    /*
    int n=sc.nextInt();
        	char[] s=sc.next().toCharArray();
        	int res=0;
        	for(int i=1;i<n;i++){
        		if( (s[i]=='1') && s[i]==s[i-1]) res++;
        	}
        	out.println(res);
    */
   
 
    public static long pow(long a,long b){
        if(b==0){
            return 1;
        }
        if(b%2==0){
            return pow(a*a,b/2);
        }else{
            return a*pow(a*a,b/2);
        }
    }
    static class Reader 
    { 
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st=new StringTokenizer("");
        public String next()
        {
            while(!st.hasMoreTokens())
            {
                try
                {
                    st=new StringTokenizer(br.readLine());
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }
        public int nextInt()
        {
            return Integer.parseInt(next());
        }
        public long nextLong()
        {
            return Long.parseLong(next());
        }
        public double nextDouble()
        {
            return Double.parseDouble(next());
        }
        public String nextLine()
        {
            try
            {
                return br.readLine();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return null;
        }
        public boolean hasNext()
        {
            String next=null;
            try
            {
                next=br.readLine();
            }
            catch(Exception e)
            {
            }
            if(next==null)
            {
                return false;
            }
            st=new StringTokenizer(next);
            return true;
        }
    } 
}
 