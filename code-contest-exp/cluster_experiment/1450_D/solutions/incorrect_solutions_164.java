import java.io.*;
import java.util.*;

public class Main {
    public static boolean check(long sum, int n){ return sum == (long) n *(n+1)/2; }

    public static void main(String[] args) throws IOException {
        int tc = sc.nextInt();
        while(tc-- > 0) {
            int n = sc.nextInt();
            int N = 1; while(N < n) N <<= 1;
            int[] arr = new int[N+1];
            Arrays.fill(arr, (int) 1e9);
            long sum = 0;
            for (int i = 1; i <= n; i++) {
                arr[i] = sc.nextInt();
                sum += arr[i];
            }
            SegmentTree sTree = new SegmentTree(arr);
            StringBuilder sb = new StringBuilder();
            sb.append(check(sum,n)? '1':'0');
            int k = 2;
            StringBuilder s = new StringBuilder();
            while(k <= n){
                sum = 0;
                int currN = n - (k-1);
                int[] freqArr = new int[currN+1];
                for (int i = 1; i <= currN; i++) {
                    int x = sTree.query(i, i+(k-1));
                    if(x > currN) break;
                    freqArr[x]++;
                    if(freqArr[x] > 1) break;
                    sum += x;
//                    s.append(x + " ");
                }
                sb.append(check(sum,n-(k-1))? '1':'0');
//                s.append('\n');
                ++k;
            }
//            pw.println(s);
            pw.println(sb);

        }
        pw.close();
    }


    public static void pairSort(Pair[] arr) {
        ArrayList<Pair> l = new ArrayList<>();
        for (Pair i : arr) l.add(i);
        Collections.sort(l);
        for (int i = 0; i < arr.length; i++) {
            arr[i] = l.get(i);
        }
    }
    public static void longSort(long[] arr) {
        ArrayList<Long> l = new ArrayList<>();
        for (long i : arr) l.add(i);
        Collections.sort(l);
        for (int i = 0; i < arr.length; i++) {
            arr[i] = l.get(i);
        }
    }
    public static void intSort(int[] arr) {
        ArrayList<Integer> l = new ArrayList<>();
        for (int i : arr) l.add(i);
        Collections.sort(l);
        for (int i = 0; i < arr.length; i++) {
            arr[i] = l.get(i);
        }
    }

    static class Scanner {
        StringTokenizer st;
        BufferedReader br;

        public Scanner(InputStream s) {
            br = new BufferedReader(new InputStreamReader(s));
        }

        public String next() throws IOException {
            while (st == null || !st.hasMoreTokens())
                st = new StringTokenizer(br.readLine());
            return st.nextToken();
        }

        public int nextInt() throws IOException {
            return Integer.parseInt(next());
        }

        public long nextLong() throws IOException {
            return Long.parseLong(next());
        }

        public String nextLine() throws IOException {
            return br.readLine();
        }

        public double nextDouble() throws IOException {
            String x = next();
            StringBuilder sb = new StringBuilder("0");
            double res = 0, f = 1;
            boolean dec = false, neg = false;
            int start = 0;
            if (x.charAt(0) == '-') {
                neg = true;
                start++;
            }
            for (int i = start; i < x.length(); i++)
                if (x.charAt(i) == '.') {
                    res = Long.parseLong(sb.toString());
                    sb = new StringBuilder("0");
                    dec = true;
                } else {
                    sb.append(x.charAt(i));
                    if (dec)
                        f *= 10;
                }
            res += Long.parseLong(sb.toString()) / f;
            return res * (neg ? -1 : 1);
        }

        public boolean ready() throws IOException {
            return br.ready();
        }

        public int[] nextIntArr(int n) throws IOException {
            int[] arr = new int[n];
            for (int i = 0; i < n; i++) {
                arr[i] = Integer.parseInt(next());
            }
            return arr;
        }

        public long[] nextLongArr(int n) throws IOException {
            long[] arr = new long[n];
            for (int i = 0; i < n; i++) {
                arr[i] = Long.parseLong(next());
            }
            return arr;
        }

    }
    static class Pair implements Comparable<Pair>{
        int first, second;
        public Pair(int first, int second){
            this.first = first; this.second = second;
        }

        @Override
        public int compareTo(Pair p2) {
            return first - p2.first;
        }

        @Override
        public String toString() {
            return "("+
                    first +
                    "," + second +
                    ')';
        }

    }
    static class SegmentTree {
        int N; 			//the number of elements in the array as a power of 2 (i.e. after padding)
        int[] array, sTree;

        SegmentTree(int[] in)
        {
            array = in; N = in.length - 1;
            sTree = new int[N<<1];		//no. of nodes = 2*N - 1, we add one to cross out index zero
            build(1,1,N);
        }

        void build(int node, int b, int e)	// O(n)
        {
            if(b == e)
                sTree[node] = array[b];
            else
            {
                int mid = b + e >> 1;
                build(node<<1,b,mid);
                build(node<<1|1,mid+1,e);
                sTree[node] = Math.min(sTree[node<<1],sTree[node<<1|1]);
            }
        }


        int query(int i, int j)
        {
            return query(1,1,N,i,j);
        }

        int query(int node, int b, int e, int i, int j)	// O(log n)
        {
            if(i>e || j <b)
                return (int) 1e9;
            if(b>= i && e <= j)
                return sTree[node];
            int mid = b + e >> 1;
            int q1 = query(node<<1,b,mid,i,j);
            int q2 = query(node<<1|1,mid+1,e,i,j);
            return Math.min(q1,q2);
        }
    }

    static PrintWriter pw = new PrintWriter(System.out);
    static Scanner sc = new Scanner(System.in);
}