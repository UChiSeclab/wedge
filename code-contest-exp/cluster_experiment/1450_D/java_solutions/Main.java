import java.util.*;
import java.io.*;
import java.math.*;
 
public class Main
{
    static class SegTreeMinQuery{
        int tree[], arr[];
        int n;
 
        public SegTreeMinQuery(int n){
            arr = new int[n + 1];
            Arrays.fill(arr, n + 1);
            this.n = n;
            tree = new int[4 * n + 4];
        }
 
        void build(int s, int e, int idx){
            if(s == e){
                tree[idx] = arr[s];
                return ;
            }
 
            int mid = s + (e - s)/2;
            build(s, mid , 2 * idx);
            build(mid + 1, e, 2 * idx + 1);
 
            tree[idx] = Math.min(tree[2 * idx], tree[2 * idx + 1]);
        }
 
        void update(int pos, int v, int s, int e, int idx){
            if(pos < s || pos > e)
                return ;
 
            if(s == e){
                tree[idx] = v;
                return ;
            } 
 
            int mid = s + (e - s) / 2;
            update(pos, v, s, mid, 2 * idx);
            update(pos, v, mid + 1, e, 2 * idx + 1);
 
            tree[idx] = Math.min(tree[2 * idx], tree[2 * idx +1]);
        }
 
        int query(int qs, int qe, int s, int e, int idx){
            if(qs <= s && qe >= e)
                return tree[idx];
 
            if(qs > e || qe < s)
                return n + 1;
 
            int mid = s + (e - s)/2;
            return Math.min(query(qs, qe, s, mid, 2 * idx),
                query(qs, qe, mid + 1, e, 2 * idx + 1));
        }
    }

    static class SegTreeMaxQuery{
        int tree[], arr[];
        int n;
 
        public SegTreeMaxQuery(int n){
            arr = new int[n + 1];
            this.n = n;
            tree = new int[4 * n + 4];
        }
 
        void build(int s, int e, int idx){
            if(s == e){
                tree[idx] = arr[s];
                return ;
            }
 
            int mid = s + (e - s)/2;
            build(s, mid , 2 * idx);
            build(mid + 1, e, 2 * idx + 1);
 
            tree[idx] = Math.max(tree[2 * idx], tree[2 * idx + 1]);
        }
 
        void update(int pos, int v, int s, int e, int idx){
            if(pos < s || pos > e)
                return ;
 
            if(s == e){
                tree[idx] = v;
                return ;
            } 
 
            int mid = s + (e - s) / 2;
            update(pos, v, s, mid, 2 * idx);
            update(pos, v, mid + 1, e, 2 * idx + 1);
 
            tree[idx] = Math.max(tree[2 * idx], tree[2 * idx +1]);
        }
 
        int query(int qs, int qe, int s, int e, int idx){
            if(qs <= s && qe >= e)
                return tree[idx];
 
            if(qs > e || qe < s)
                return 0;
 
            int mid = s + (e - s)/2;
            return Math.max(query(qs, qe, s, mid, 2 * idx),
                query(qs, qe, mid + 1, e, 2 * idx + 1));
        }
    }

    public static void process(int test_number)throws IOException
    {
        int n = ni(), arr[] = new int[n + 1], leftDist[] = new int[n + 1], rightDist[] = new int[n + 1];
        for(int i = 1; i <= n; i++)
            arr[i] = ni();

        SegTreeMaxQuery maxQuery = new SegTreeMaxQuery(n); maxQuery.build(1, n, 1);
        SegTreeMinQuery minQuery = new SegTreeMinQuery(n); minQuery.build(1, n, 1);

        for(int i = 1; i <= n; i++){ // find closest idx from left which has an element < arr[i]
            int startIdx = i;

            int idx = maxQuery.query(1, arr[i] - 1, 1, n, 1);
            if(idx < 1 || idx > n)
                leftDist[i] = i;
            else
                leftDist[i] = idx;

            while(i + 1 <= n && arr[i + 1] == arr[startIdx]){
                ++i;
                leftDist[i] = leftDist[startIdx];
            }

            maxQuery.update(arr[startIdx], startIdx, 1, n, 1);
        }

        for(int i = n; i >= 1; i--){ // find closest idx from right which has an element < arr[i]
            int startIdx = i;

            int idx = minQuery.query(1, arr[i] - 1, 1, n, 1);
            if(idx < 1 || idx > n)
                rightDist[i] = i;
            else
                rightDist[i] = idx;

            while(i - 1 >= 1 && arr[i - 1] == arr[startIdx]){
                --i;
                rightDist[i] = rightDist[startIdx];
            }

            minQuery.update(arr[startIdx], startIdx, 1, n, 1);
        }

        int mx = 0, res[] = new int[n + 1];
        Arrays.fill(res, 1);
        
        boolean isPerm[] = new boolean[n + 1]; Arrays.fill(isPerm, false); isPerm[0] = true;
        for(int i = 1; i <= n; i++)
            isPerm[arr[i]] = true; 

        for(int i = 1; i <= n; i++){
            isPerm[i] = isPerm[i] && isPerm[i - 1];        
            if(!isPerm[n - i + 1])
                res[i] = 0;
        }
        
        for(int i = 1; i <= n; i++){
            if(i - leftDist[i] > 1 && rightDist[i] - i > 1)
                mx = Math.max(mx, Math.max(i - leftDist[i], rightDist[i] - i));
        }

        if(arr[1] == 1 && arr[n] == 1)
            Arrays.fill(res, 0);

        if(isPerm[1])
            res[n] = 1;

        if(!isPerm[n])
            res[1] = 0;

        for(int i = 2; i <= mx; i++)
            res[i] = 0;

        for(int i = 1; i <= n; i++)
            p(res[i]);
        p('\n');
    }
 
    static final long mod = (long)1e9+7l;
    
    static FastReader sc;
    static PrintWriter out;
    public static void main(String[]args)throws IOException
    {
        out = new PrintWriter(System.out);
        sc = new FastReader();
 
        long s = System.currentTimeMillis();
        int t = 1;
        t = ni();
        for(int i = 1; i <= t; i++)
            process(i);
 
        out.flush();
        System.err.println(System.currentTimeMillis()-s+"ms");
    }

    static void trace(Object... o){ System.err.println(Arrays.deepToString(o)); };
    static void pn(Object o){ out.println(o); }
    static void p(Object o){ out.print(o); }
    static int ni()throws IOException{ return Integer.parseInt(sc.next()); }
    static long nl()throws IOException{ return Long.parseLong(sc.next()); }
    static double nd()throws IOException{ return Double.parseDouble(sc.next()); }
    static String nln()throws IOException{ return sc.nextLine(); }
    static long gcd(long a, long b)throws IOException{ return (b==0)?a:gcd(b,a%b);}
    static int gcd(int a, int b)throws IOException{ return (b==0)?a:gcd(b,a%b); }
    static int bit(long n)throws IOException{ return (n==0)?0:(1+bit(n&(n-1))); }
    
    static class FastReader{ 
        BufferedReader br; 
        StringTokenizer st; 
  
        public FastReader(){ 
            br = new BufferedReader(new InputStreamReader(System.in)); 
        } 
  
        String next(){ 
            while (st == null || !st.hasMoreElements()){ 
                try{ st = new StringTokenizer(br.readLine()); } catch (IOException  e){ e.printStackTrace(); } 
            } 
            return st.nextToken(); 
        } 
  
        String nextLine(){ 
            String str = ""; 
            try{ str = br.readLine(); } catch (IOException e) { e.printStackTrace(); } 
            return str; 
        } 
    } 
}
