import java.util.*;
import java.io.*;
public class Solution implements Runnable {
    FastScanner sc;
    PrintWriter pw;
    
    final class FastScanner {
        BufferedReader br;
        StringTokenizer st;
 
        public FastScanner() {
            try {
                br = new BufferedReader(new InputStreamReader(System.in));
                st = new StringTokenizer(br.readLine());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
 
        public long nlo() {
            return Long.parseLong(next());
        }
 
        public String next() {
            if (st.hasMoreTokens()) return st.nextToken();
            try {
                st = new StringTokenizer(br.readLine());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return st.nextToken();
        }
 
        public int ni() {
            return Integer.parseInt(next());
        }
 
        public String nli() {
            String line = "";
            if (st.hasMoreTokens()) line = st.nextToken();
            else try {
                return br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            while (st.hasMoreTokens()) line += " " + st.nextToken();
            return line;
        }
 
        public double nd() {
            return Double.parseDouble(next());
        }
    }
    public static void main(String[] args) throws Exception
    {
        new Thread(null,new Solution(),"codeforces",1<<28).start();
    }
    public void run()
    {
        sc=new FastScanner();
        pw=new PrintWriter(System.out);
        try{
        solve();}
        catch(Exception e)
        {
            pw.println(e);
        }
        pw.flush();
        pw.close();
    }
    public long gcd(long a,long b)
    {
        return b==0L?a:gcd(b,a%b);
    }
    public long ppow(long a,long b,long mod)
    {
        if(b==0L)
        return 1L;
        long tmp=1;
        while(b>1L)
        {
            if((b&1L)==1)
            tmp*=a;
            a*=a;
            a%=mod;
            tmp%=mod;
            b>>=1;
        }
        return (tmp*a)%mod;
    }
    public long pow(long a,long b)
    {
        if(b==0L)
        return 1L;
        long tmp=1;
        while(b>1L)
        {
            if((b&1L)==1)
            tmp*=a;
            a*=a;
            b>>=1;
        }
        return (tmp*a);
    }
    public  int gcd(int x,int y)
    {
        return y==0?x:gcd(y,x%y);
    }
   
    //////////////////////////////////
    /////////////  LOGIC  ///////////
    ////////////////////////////////
    
    public void solve() throws Exception{
        int t=sc.ni();
        while(t-->0){
            int n=sc.ni();
            int[] arr=new int[n];
            for(int i=0;i<n;i++)
            arr[i]=sc.ni();
            int[] left,right;
            left=new int[n];
            right=new int[n];
            Arrays.fill(left,-1);
            Arrays.fill(right,n);
            Stack<Integer> stk=new Stack();
            int[] ans=new int[n+1];
            int[] map=new int[n+1];
            ArrayList<Integer>[] num=new ArrayList[n+1];
            for(int i=0;i<=n;i++)
            num[i]=new ArrayList();
            Arrays.fill(ans,1);
            for(int i=0;i<n;i++){
                while(stk.size()>0&&arr[stk.peek()]>=arr[i])
                stk.pop();
                left[i]=stk.size()==0?0:stk.peek()+1;
                if(map[arr[i]]!=0)
                ans[1]=0;
                map[arr[i]]++;
                stk.push(i);
            }
            stk=new Stack();
            for(int i=n-1;i>=0;i--){
                while(stk.size()>0&&arr[stk.peek()]>=arr[i])
                stk.pop();
                right[i]=stk.size()==0?n-1:stk.peek()-1;
                stk.push(i);
            }
            HashSet<Integer>[] pos=new HashSet[n+1];
            for(int i=0;i<=n;i++)
            pos[i]=new HashSet();
            for(int i=0;i<n;i++){
                int x=i-left[i];
                int y=right[i]-i;
                if(Math.min(x,y)>=1)
                    ans[Math.min(n-1,Math.max(x,y)+1)]=0;
                if(!pos[arr[i]].contains(left[i]))
                num[arr[i]].add(Math.min(n-1,x+y));
                pos[arr[i]].add(left[i]);
            }
            for(int i=1;i<=n;i++){
                Collections.sort(num[i]);
                if(map[i]>1)
                    ans[num[i].get(num[i].size()-1)]=0;
            }
            for(int i=1;i<=n;i++){
                if(pos[i].size()==0){
                    ans[n-i+1]=0;
                    break;
                }
            }
            for(int i=n;i>1;i--){
                if(ans[i]==0){
                    i--;
                    while(i>1)
                        ans[i--]=0;
                }
            }
            for(int i=1;i<=n;i++)
            pw.print(ans[i]);
            pw.println();
        }
   }
}