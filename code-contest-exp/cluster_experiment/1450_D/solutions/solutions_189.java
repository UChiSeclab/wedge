import java.io.*;
import java.util.*;

/**
    
 */
 
public class A{
    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        InputReader in = new InputReader(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        Task solver = new Task();
        solver.solve(in, out);
        out.close();
    }
    // main solver
    
    static class Node implements Comparable<Node>{
        public int t,x;
        public Node(int t, int x){
            this.t= t;
            this.x= x;
        }

        @Override
        public int compareTo(Node o) {
            if(this.t==o.t) return o.x-this.x;
            return this.t-o.t;
        }
    }
    static class RMQ {
        int[][] st;
        int[] arr;
        int[] log;
        int n,k;
    
        public RMQ(int[] arr) {
            this.arr= arr;
            this.n= this.arr.length;
            this.k= (int)(Math.floor(Math.log(n)/Math.log(2))) +1;
            this.st= new int[n][k+1];
    
            //build sparse table: st[i][j] query range from arr[i] with the length og 2^j
            for(int i=0;i<n;i++){ st[i][0]=arr[i];}
            for(int j=1;j<=k;j++) {
                for(int i=0;i+ (1<<j) <=n ;i++) {
                    st[i][j]= Math.min(st[i][j-1], st[i+(1<<(j-1))][j-1]);
                }
            }
    
            //build log array
            this.log= new int[n+1];
            log[1]=0;
            for(int i=2;i<=n;i++) {
                log[i]= log[i/2]+1;
            }
        }
    
        public int query(int l, int r) {
            int j= this.log[r-l+1];
            return Math.min(st[l][j], st[r-(1<<j)+1][j]);
        }
    }

    static int[] tree,lazy;
    static class SEG{
        public static int query(int l, int r, int t, int L, int R) {
            if (l >= L && r <= R) return tree[t];
            if (l > R || r < L) return 0;
            int mid = (l + r) >> 1;
            int d = 0;
            lazy[t << 1] = Math.max(lazy[t], lazy[t << 1]);
            lazy[t << 1 | 1] = Math.max(lazy[t], lazy[t << 1 | 1]);
            tree[t << 1] = Math.max(lazy[t << 1], tree[t << 1]);
            tree[t << 1|1] = Math.max(lazy[t << 1 | 1], tree[t << 1 | 1]);
            if (mid >= L) d = Math.max(d, query(l, mid, t << 1, L, R));
            if (mid < R) d = Math.max(d, query(mid + 1, r, t << 1 | 1, L, R));
            return d;
        }
        public static void update(int l, int r, int t, int L, int R, int d) {
            if (l >= L && r <= R) {
                lazy[t] = Math.max(lazy[t], d);
                tree[t] = Math.max(tree[t], lazy[t]);
            }
            else {
                lazy[t << 1] = Math.max(lazy[t], lazy[t << 1]);
                lazy[t << 1 | 1] = Math.max(lazy[t], lazy[t << 1 | 1]);
                tree[t << 1] = Math.max(lazy[t << 1], tree[t << 1]);
                tree[t << 1|1] = Math.max(lazy[t << 1 | 1], tree[t << 1 | 1]);
                int mid = (l + r) >> 1;
                if (mid >= L)update(l, mid, t << 1, L, R, d);
                if (mid < R) update(mid + 1, r, t << 1 | 1, L, R, d);
                
            }
        }
    }
    
    static class Pair implements Comparable<Pair>{
        public int x, y;
        public Pair(int x, int y){
            this.x = x;
            this.y = y;
        }
 
        @Override
        public int compareTo(Pair o) {
            if (this.x > o.x){
                return 1;
            }
            else if (this.x < o.x){
                return -1;
            }
            else{
                return Integer.compare(this.y, o.y);
            }
        }
    }
    static class Task{
        static int MAXN= 100010;
        RMQ rmq;
        public void solve(InputReader in, PrintWriter out) {
           int t= in.nextInt();
           while(t-->0){
               int n= in.nextInt();
               int[] arr= new int[n];
               for(int i=0;i<n;i++){
                   arr[i]= in.nextInt();
               }
               this.rmq= new RMQ(arr);
               int l=2, r= n+1;
               while(l<r){
                   int mid= (l+r)/2;
                   if(check(arr,mid)){
                       r=mid;
                   }
                   else{
                       l= mid+1;
                   }
               }
            //    out.println(r);
               String res;
               if(check(arr,1)){
                   out.print("1");
               }
               else out.print("0");
               for(int i=0;i<r-2;i++){
                   out.print("0");
               }
               for(int i=0;i<n-r+1;i++){
                   out.print("1");
               }
               out.println();
           }
            
        }
        // public boolean check(int[] arr, int k){
        //     int n= arr.length;
        //     boolean[] per= new boolean[n-k+2];
        //     per[0]=true;
        //     for(int i=0;i<=n-k;i++){
        //         int min= rmq.query(i,i+k-1);
        //         if(min>n-k+1) return false;
        //         per[min]=true;
        //     }
        //     for(boolean e: per){
        //         if(!e) return false;
        //     }
        //     return true;
        // }
        public boolean check(int arr[], int k)
        {
            int n= arr.length;
            boolean[] per= new boolean[n-k+2];
            per[0]=true;
            
            // Create a Double Ended Queue, Qi 
            // that will store indexes of array elements
            // The queue will store indexes of 
            // useful elements in every window and it will
            // maintain decreasing order of values 
            // from front to rear in Qi, i.e.,
            // arr[Qi.front[]] to arr[Qi.rear()] 
            // are sorted in decreasing order
            Deque<Integer> Qi = new LinkedList<Integer>();
    
            /* Process first k (or first window)
            elements of array */
            int i;
            for (i = 0; i < k; ++i) 
            {
                
                // For every element, the previous 
                // smaller elements are useless so
                // remove them from Qi
                while (!Qi.isEmpty() && arr[i] <= 
                            arr[Qi.peekLast()])
                
                    // Remove from rear
                    Qi.removeLast(); 
    
                // Add new element at rear of queue
                Qi.addLast(i);
            }
    
            // Process rest of the elements, 
            // i.e., from arr[k] to arr[n-1]
            for (; i < n; ++i) 
            {
            
                // The element at the front of the
                // queue is the largest element of
                // previous window, so print it
                // System.out.print(arr[Qi.peek()] + " ");
                int min= arr[Qi.peek()];
                if(min>n-k+1) return false;
                per[min]=true;
    
                // Remove the elements which 
                // are out of this window
                while ((!Qi.isEmpty()) && Qi.peek() <= 
                                                i - k)
                    Qi.removeFirst();
    
                // Remove all elements smaller 
                // than the currently
                // being added element (remove 
                // useless elements)
                while ((!Qi.isEmpty()) && arr[i] <=
                                arr[Qi.peekLast()])
                    Qi.removeLast();
    
                // Add current element at the rear of Qi
                Qi.addLast(i);
            }
    
            // Print the maximum element of last window
            // System.out.print(arr[Qi.peek()]);
            int min2= arr[Qi.peek()];
            if(min2>n-k+1) return false;
            per[min2]=true;
            for(boolean e: per){
                if(!e) return false;
            }
            return true;
        }
    }

    // fast input reader class;
    static class InputReader {
        BufferedReader br;
        StringTokenizer st;
 
        public InputReader(InputStream stream) {
            br = new BufferedReader(new InputStreamReader(stream));
        }
 
        public String nextToken() {
            while (st == null || !st.hasMoreTokens()) {
                String line = null;
                try {
                    line = br.readLine();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                if (line == null) {
                    return null;
                }
                st = new StringTokenizer(line);
            }
            return st.nextToken();
        }
 
        public int nextInt() {
            return Integer.parseInt(nextToken());
        }
        public double nextDouble(){
            return Double.parseDouble(nextToken());
        }
        public long nextLong(){
            return Long.parseLong(nextToken());
        }
    }
}