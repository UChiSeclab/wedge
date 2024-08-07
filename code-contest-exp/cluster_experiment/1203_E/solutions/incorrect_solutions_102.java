
import java.io.*;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.*;

public class Main {
    static int MAX = (int) (1e7) + 1;//max prime number is 9999991
    static ArrayList<Integer> primes = new ArrayList<>();
    static boolean[] prime = new boolean[MAX];
    static boolean[] visited;
    static int color[];
    static ArrayList<Integer>[] tree;
    static int zeros = 0;
    static int ones = 0;
    static int pigeon[];
    static boolean isArticulation[];
    static int[] dfsNum;
    static int[] dfsLow;
    static int counter;
    static int root;
    static int rootChildren;
    static ArrayList<pair> ans;

    static class pair implements Comparable<pair> {
        int x;
        int y;

        pair(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int compareTo(pair p) {
            if (y > p.y)
                return -1;
            if (y == p.y)
                return x - p.x;
            return 1;
        }

    }

    static long pow(long a, int e) {
        long res = 1;
        while (e > 0) {
            //BigInteger a2=new BigInteger(""+a);
            res = res * a;
            e--;
        }
        return res;
    }

    static void seive() {
        Arrays.fill(prime, true);
        prime[0] = prime[1] = false;
        int i = 2;
        for (; i * i < prime.length; i++) {
            if (prime[i]) {
                primes.add(i);
                for (int j = i * i; j < prime.length; j += i) {
                    prime[j] = false;
                }
            }
        }
        for (; i < prime.length; i++) {
            if (prime[i])
                primes.add(i);
        }
    }

    static long gcd(long a, long b) {
        if (b == 0)
            return a;
        return gcd(b, a % b);
    }

    static long lcm(long a, long b) {
        return a * (b / gcd(a, b));
    }

    static boolean isPrime(long N) {
        if (N < prime.length)
            return prime[(int) N];
        else {
            for (int i = 0; i < primes.size(); i++) {
                if (N % primes.get(i) == 0)
                    return false;
            }
        }
        return true;
    }

    static boolean bipartiteDfs(int u) {
        visited[u] = true;
        if (color[u] == 0)
            zeros++;
        else ones++;
        for (int i = 0; i < tree[u].size(); i++) {
            int v = tree[u].get(i);
            if (visited[v]) {
                if (color[v] == color[u])
                    return false;
            } else {
                color[v] = 1 - color[u];
                if (!bipartiteDfs(v))
                    return false;
            }
        }
        return true;
    }

    static void articulationPoint(int u, int parent) {
        dfsLow[u] = dfsNum[u] = ++counter;
        for (int i = 0; i < tree[u].size(); i++) {
            int v = tree[u].get(i);
            if (v != parent) {
                if (dfsLow[v] == 0 && dfsNum[v] == 0) {
                    if (u == root)
                        rootChildren++;
                    articulationPoint(v, u);
                    dfsLow[u] = Math.min(dfsLow[u], dfsLow[v]);
                    //System.out.println("u "+u+" v "+v);
                    if (dfsLow[v] >= dfsNum[u]) {
                        isArticulation[u] = true;
                        pigeon[u]++;
                    }
                } else {
                    if (dfsNum[v] < dfsLow[u])
                        dfsLow[u] = dfsNum[v];
                }
            }
        }

    }

    static void bridge(int u, int parent) {
        dfsLow[u] = dfsNum[u] = ++counter;
        for (int i = 0; i < tree[u].size(); i++) {
            int v = tree[u].get(i);
            if (v != parent) {
                if (dfsLow[v] == 0 && dfsNum[v] == 0) {
                    if (u == root)
                        rootChildren++;
                    bridge(v, u);
                    dfsLow[u] = Math.min(dfsLow[u], dfsLow[v]);
                    if (dfsLow[v] > dfsNum[u]) {
                        int x;
                        int y;
                        if (u < v) {
                            x = u;
                            y = v;
                        } else {
                            x = v;
                            y = u;
                        }
                        ans.add(new pair(x, y));
                    }

                } else {
                    if (dfsNum[v] < dfsLow[u])
                        dfsLow[u] = dfsNum[v];
                }
            }
        }
    }


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        //BufferedReader br = new BufferedReader(new FileReader("input.txt"));
        StringTokenizer st;
        PrintWriter out = new PrintWriter(System.out);
        int n=Integer.parseInt(br.readLine());
        st=new StringTokenizer(br.readLine());
        int [] a=new int[n];
        for (int i = 0; i <n ; i++) {
            a[i]=Integer.parseInt(st.nextToken());
        }
        if(n==1)
            out.println(1);
        else {
            Arrays.sort(a);
            int min = a[0] > 1 ? a[0] - 1 : a[0];
            int max = a[n - 1] + 1;
            HashSet<Integer> hs = new HashSet<>();
            hs.add(min);
            hs.add(max);
            for (int i = 1; i < n - 1; i++) {
               if(!hs.contains(a[i]))
                   hs.add(a[i]);
               else{
                   if(!hs.contains(a[i]+1))
                       hs.add(a[i]+1);
                   else{
                       if(a[i]>1&&!hs.contains(a[i]-1))
                           hs.add(a[i]-1);
                   }
               }
            }
            out.println(hs.size());
        }
        out.flush();

    }
}