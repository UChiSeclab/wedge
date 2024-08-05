import java.io.*;
import java.util.*;
import java.math.*;

public class codeForCessss {
    static Scanner sc = new Scanner(System.in);
    static PrintWriter pw = new PrintWriter(System.out), pw2 = new PrintWriter(System.out);

    public static void main(String[] args) throws IOException {
        int n=sc.nextInt();
        int[] arr=new int[n];
        int[] count=new int[(int)2e5];
        for(int i=0;i<n;i++){
            arr[i]=sc.nextInt();
            if(arr[i]==1){
                if(count[1]==0)count[1]++;
                else if(count[2]==0)count[2]++;
            }
        }
        sort(arr);
        for(int i=0;i<n;i++){
            if(arr[i]==1)continue;
            if(count[arr[i]-1]==0)count[arr[i]-1]++;
            else if(count[arr[i]]==0)count[arr[i]]++;
            else if(count[arr[i]+1]==0)count[arr[i]+1]++;
        }
        int c=0;
        for(int i=0;i<count.length;i++){
            if(count[i]==1)c++;
        }
        pw.println(c);
        pw.close();
    }

    public static <E> void print2D(E[][] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                pw.println(arr[i][j]);
            }
        }
    }

    public static int digitSum(String s) {
        int toReturn = 0;
        for (int i = 0; i < s.length(); i++) toReturn += Integer.parseInt(s.charAt(i) + " ");
        return toReturn;
    }

    public static boolean isPrime(long n) {
        if (n <= 1)
            return false;
        if (n <= 3)
            return true;

        if (n % 2 == 0 || n % 3 == 0)
            return false;

        for (long i = 5; i * i <= n; i = i + 6)
            if (n % i == 0 || n % (i + 2) == 0)
                return false;
        return true;
    }

    public static long pow(long a, long pow) {
        return pow == 0 ? 1 : pow % 2 == 0 ? pow(a * a, pow >> 1) : a * pow(a * a, pow >> 1);
    }

    public static long sumNum(long a) {
        return a * (a + 1) / 2;
    }

    public static long  gcd(long n1, long  n2) {
        return n2 == 0 ? n1 : gcd(n2, n1 % n2);
    }

    public static long factorial(long a) {
        return a == 0 || a == 1 ? 1 : a * factorial(a - 1);
    }
    
    public static  void sort(int arr[]){
        random(arr);
        Arrays.sort(arr);
    }
    public static void random(int arr[]){
        Random rnd = new Random();
        for (int i = arr.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            int temp = arr[index];
            arr[index] = arr[i];
            arr[i] = temp;
        }
    }
    public static Double[] solveQuadratic(double a, double b, double c) {
        double result = (b * b) - 4.0 * a * c;
        double r1;
        if (result > 0.0) {
            r1 = ((double) (-b) + Math.pow(result, 0.5)) / (2.0 * a);
            double r2 = ((double) (-b) - Math.pow(result, 0.5)) / (2.0 * a);
            return new Double[]{r1, r2};
        } else if (result == 0.0) {
            r1 = (double) (-b) / (2.0 * a);
            return new Double[]{r1, r1};
        } else {
            return new Double[]{null, null};
        }
    }

    public static BigDecimal[] solveQuadraticBigDicimal(double aa, double bb, double cc) {
        BigDecimal a = BigDecimal.valueOf(aa), b = BigDecimal.valueOf(bb), c = BigDecimal.valueOf(cc);
        BigDecimal result = (b.multiply(b)).multiply(BigDecimal.valueOf(4).multiply(a.multiply(c)));
        BigDecimal r1;
        if (result.compareTo(BigDecimal.ZERO) > 0) {
            r1 = (b.negate().add(bigSqrt(result)).divide(a.multiply(BigDecimal.valueOf(2))));
            BigDecimal r2 = (b.negate().subtract(bigSqrt(result)).divide(a.multiply(BigDecimal.valueOf(2))));
            return new BigDecimal[]{r1, r2};
        } else if (result.compareTo(BigDecimal.ZERO) == 0) {
            r1 = b.negate().divide(a.multiply(BigDecimal.valueOf(2)));
            return new BigDecimal[]{r1, r1};
        } else {
            return new BigDecimal[]{null, null};
        }
    }

    private static BigDecimal sqrtNewtonRaphson(BigDecimal c, BigDecimal xn, BigDecimal precision) {
        BigDecimal fx = xn.pow(2).add(c.negate());
        BigDecimal fpx = xn.multiply(new BigDecimal(2));
        BigDecimal xn1 = fx.divide(fpx, 2 * BigDecimal.valueOf(16).intValue(), RoundingMode.HALF_DOWN);
        xn1 = xn.add(xn1.negate());
        BigDecimal currentSquare = xn1.pow(2);
        BigDecimal currentPrecision = currentSquare.subtract(c);
        currentPrecision = currentPrecision.abs();
        if (currentPrecision.compareTo(precision) <= -1) {
            return xn1;
        }
        return sqrtNewtonRaphson(c, xn1, precision);
    }

    public static BigDecimal bigSqrt(BigDecimal c) {
        return sqrtNewtonRaphson(c, new BigDecimal(1), new BigDecimal(1).divide(BigDecimal.valueOf(10).pow(16)));
    }

    static class graph {
        int V;
        ArrayList<Integer> adjL[];
        boolean vis[];

        public graph(int V) {
            this.V = V;
            adjL = new ArrayList[V + 1];
            vis = new boolean[V + 1];
            for (int i = 0; i < V + 1; i++) adjL[i] = new ArrayList<>();
        }

        public void addEdge(int v1, int v2) {
            this.adjL[v1].add(v2);
        }

        public void BFS(int n) {
            Queue<Integer> q = new LinkedList<>();
            q.add(n);
            while (!q.isEmpty()) {
                int a = q.poll();
                vis[a] = true;
                for (int x : adjL[a]) {
                    if (!vis[x]) {
                        vis[x] = true;
                        q.add(x);
                    }
                }
            }
        }

        public void DFS(int v) {
            vis[v] = true;
            for (int u : adjL[v]) {
                if (!vis[u]) {
                    DFS(u);
                }
            }
        }

        public void DFS(int v, Stack<Integer> st) {
            vis[v] = true;
            for (int u : adjL[v]) {
                if (!vis[u]) {
                    DFS(u, st);
                }
            }
            st.push(v);
        }
    }

    static class Scanner {
        StringTokenizer st;
        BufferedReader br;

        public Scanner(FileReader r) {
            br = new BufferedReader(r);
        }

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
    }

    static class pair<E1, E2> implements Comparable<pair> {
        E1 x;
        E2 y;

        pair(E1 x, E2 y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public int compareTo(pair o) {
            return x.equals(o.x) ? (Integer) y - (Integer) o.y : (Integer) x - (Integer) o.x;
        }

        @Override
        public String toString() {
            return x + " " + y;
        }

        public double pointDis(pair p1) {
            return Math.sqrt(((Integer) y - (Integer) p1.y) * ((Integer) y - (Integer) p1.y) + ((Integer) x - (Integer) p1.x) * ((Integer) x - (Integer) p1.x));
        }
    }

}
