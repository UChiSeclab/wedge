
import java.io.*;
import java.math.*;
import java.util.*;

public class Level {

    public static DS.Scanner in = new DS.Scanner();
    public static DS.Print out = new DS.Print();

    public static void main(String args[]) {
        int n = in.nextInt();
        int[] freq = new int[151000];
        for (int i = 0; i < n; i++) {
            freq[in.nextInt()]++;
        }

        if (freq[1] > 1) {
            freq[2]++;
            freq[1]--;
        }
        for (int i = 2; i <freq.length; i++) {
            if (freq[i] > 0) {
                if (freq[i - 1] == 0) {
                    freq[i - 1]++;
                    freq[i]--;
                }
            }
            if (freq[i] > 1) {
                freq[i]--;
                freq[i + 1]++;
            }
        }
        HashSet<Integer> set = new HashSet<Integer>();
        for (int i = 1; i <= 15001; i++) {
            if (freq[i] > 0) {
                set.add(i);
            }
        }
        System.out.println(set.size());

    }

    public static class Algo {

        public static int lowerBound(ArrayList<Long> list, int length, long value) {
            int low = 0;
            int high = length;
            while (low < high) {
                final int mid = (low + high) / 2;
                if (value <= list.get(mid)) {
                    high = mid;
                } else {
                    low = mid + 1;
                }
            }
            return low;
        }

        public static int upperBound(ArrayList<Long> list, int length, long value) {
            int low = 0;
            int high = length;
            while (low < high) {
                final int mid = (low + high) / 2;
                if (value >= list.get(mid)) {
                    low = mid + 1;
                } else {
                    high = mid;
                }
            }
            return low;
        }

        public static void merge(int arr[], int l, int m, int r) {
            int n1 = m - l + 1;
            int n2 = r - m;

            int L[] = new int[n1];
            int R[] = new int[n2];

            for (int i = 0; i < n1; ++i) {
                L[i] = arr[l + i];
            }
            for (int j = 0; j < n2; ++j) {
                R[j] = arr[m + 1 + j];
            }

            int i = 0, j = 0;

            int k = l;
            while (i < n1 && j < n2) {
                if (L[i] <= R[j]) {
                    arr[k] = L[i];
                    i++;
                } else {
                    arr[k] = R[j];
                    j++;
                }
                k++;
            }

            while (i < n1) {
                arr[k] = L[i];
                i++;
                k++;
            }

            while (j < n2) {
                arr[k] = R[j];
                j++;
                k++;
            }
        }

        public static void sort(int arr[], int l, int r) {
            if (l < r) {
                int m = (l + r) / 2;

                sort(arr, l, m);
                sort(arr, m + 1, r);

                merge(arr, l, m, r);
            }
        }

        public static ArrayList<Integer> sieve(int n) {
            long sqr = (long) Math.sqrt(n);
            boolean[] isPrime = new boolean[n + 1];
            for (int i = 0; i <= n; i++) {
                isPrime[i] = true;
            }
            for (int i = 2; i <= sqr; i++) {
                if (isPrime[i]) {
                    for (int p = i * i; p <= n; p += i) {
                        isPrime[p] = false;
                    }
                }
            }
            ArrayList<Integer> list = new ArrayList<Integer>();
            for (int i = 0; i <= n; i++) {
                if (isPrime[i]) {
                    list.add(i);
                }
            }
            list.remove(0);
            list.remove(0);
            return list;
        }

        public static boolean isPrime(long n) {
            boolean res = true;
            List l = sieve((int) Math.sqrt(n));
            for (int i = 0; i < l.size(); i++) {
                int curPrime = (int) l.get(i);
                if (n % curPrime == 0) {
                    res = false;
                    break;
                }
            }
            return res;
        }

        public static ArrayList factorizatin(int n) {

            ArrayList<Integer> list = new ArrayList<Integer>();
            if (n == 1) {
                list.add(1);
                return list;
            } else if (n == 2) {
                list.add(2);
                return list;
            } else {
                if (isPrime(n)) {
                    System.out.println(-1);
                    return null;
                } else {
                    Iterator ptr = sieve((int) Math.sqrt(n)).iterator();
                    if (ptr.hasNext()) {
                        int cur = (int) ptr.next();
                        // System.out.println(cur);
                        while (n != 1) {
                            System.out.println(n);
                            while (n % cur == 0) {
                                list.add(cur);
                                n /= cur;
                            }
                            if (ptr.hasNext()) {
                                cur = (int) ptr.next();
                            }
                            //System.out.println(cur);
                        }
                    }
                    if (n > 2) {
                        list.add(n);
                    }
                }
                return list;
            }
        }

        public static long gcd(long a, long b) {
            if (b == 0) {
                return a;
            } else {
                return gcd(b, a % b);
            }
        }

        public static long lcm(long a, long b) {
            long ab = a * b;
            long gcd = gcd(a, b);
            return ab / gcd;
        }

        public static int bs(int[] z, int k) {
            int res = 0;
            int l = 0;
            int r = z.length - 1;
            int mid = (l + r) / 2;
            while (l <= r) {

                if (z[mid] == k) {
                    res = 1;
                    break;
                }
                if (z[mid] > k) {
                    r = mid - 1;
                } else {
                    l = mid + 1;
                }
                mid = (l + r) / 2;
            }
            return res;
        }

        public static void solve() {
            int n = Integer.parseInt(in.next());
            int target = Integer.parseInt(in.next());
            int[] z = new int[n];
            for (int i = 0; i < n; i++) {
                z[i] = Integer.parseInt(in.next());
            }
            Arrays.sort(z);
            int ptr1 = 0;
            int ptr2 = n - 1;
            int wind = z[ptr1] + z[ptr2];
            if (wind == target) {
                System.out.println(ptr1 + "-" + ptr2);
            } else {
                while (ptr1 < ptr2 && wind != target) {
                    if (wind > target) {
                        wind -= z[ptr2--];
                        wind += z[ptr2];
                    } else {
                        wind -= z[ptr1++];
                        wind += z[ptr1];
                    }
                }
                if ((ptr1 != 0 || ptr2 != n - 1) && ptr2 != ptr1) {
                    System.out.println(ptr1 + "-" + ptr2);
                } else {
                    System.out.println(-1);
                }
            }
        }

        public static void sliding() {
            int n = Integer.parseInt(in.next());
            int k = Integer.parseInt(in.next());
            int[] z = new int[n];
            for (int i = 0; i < n; i++) {
                z[i] = Integer.parseInt(in.next());
            }
            int wind = 0;
            for (int i = 0; i < k; i++) {
                wind += z[i];
            }
            int max = wind;
            for (int i = k; i < n; i++) {
                wind += z[i];
                wind -= z[i - k];
                max = Math.max(max, wind);
            }
            System.out.println(max);
        }

        public static ArrayList<Integer> dd(int n) {
            ArrayList<Integer> list = new ArrayList<Integer>();
            for (int i = 1; i * i <= n; i++) {
                if (n % i == 0) {
                    list.add(n / i);
                    list.add(i);
                }
            }

            return list;
        }
    }

    private static class DS {

        public static class Print {

            private final BufferedWriter bw;

            public Print() {
                this.bw = new BufferedWriter(new OutputStreamWriter(System.out));
            }

            public void print(Object object) throws IOException {
                bw.append(object + " ");
            }

            public void println(Object object) throws IOException {
                print(object);
                bw.append("\n");
            }

            public void close() throws IOException {
                bw.close();
            }
        }

        private static class Graph {

            int V = 0;
            ArrayList<Integer>[] list = null;

            public Graph(int v) {
                this.V = v + 1;
                this.list = new ArrayList[this.V];
                for (int i = 0; i < list.length; i++) {
                    list[i] = new ArrayList<Integer>();
                }
            }

            public void addEdge(int s, int v) {
                list[s].add(v);
                list[v].add(s);
            }

            public int count(int s, boolean[] v, int[] cmpOfNode, int cmp) {
                int count = 1;
                Queue<Integer> q = new LinkedList();
                q.add(s);
                v[s] = true;
                while (q.size() > 0) {
                    int cur = q.poll();
                    cmpOfNode[cur] = cmp;
                    v[cur] = true;
                    ArrayList<Integer> st = list[cur];
                    for (int val : st) {
                        if (!v[val]) {
                            q.add(val);
                            cmpOfNode[val] = cmp;
                            v[val] = true;
                            count++;
                        }
                    }
                }
                return count;
            }
        }

        private static class Stack {

            private int Size;
            Node top;
            Node last;

            Stack() {

                Size = 0;
                top = null;
                last = null;
            }

            public int size() {
                return this.Size;
            }

            public void push(Object item) {
                Node node = new Node();
                node.data = item;
                node.next = top;
                top = node;
                Size++;
            }

            public void pop() {
                if (top != null) {
                    top = top.next;
                }
                Size--;
            }

            public boolean isEmpty() {
                return top == null;
            }

            public Object getTop() {
                if (top != null) {
                    return top.data;
                } else {
                    return null;
                }
            }

            public void display() {
                if (top != null) {
                    Node temp = top;
                    while (temp != null) {
                        System.out.print(temp.data + " ");
                        temp = temp.next;
                    }
                    System.out.println();
                }
            }

            private class Node {

                Object data;
                Node next;
            }
        }

        private static class Scanner {

            BufferedReader br;
            StringTokenizer st;

            public Scanner() {
                br = new BufferedReader(new InputStreamReader(System.in));
            }

            String next() {
                while (st == null || !st.hasMoreElements()) {
                    try {
                        st = new StringTokenizer(br.readLine());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return st.nextToken();
            }

            int nextInt() {
                return Integer.parseInt(next());
            }

            long nextLong() {
                return Long.parseLong(next());
            }

            double nextDouble() {
                return Double.parseDouble(next());
            }

            String nextLine() {
                String str = "";
                try {
                    str = br.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return str;
            }

            BigInteger nextBigInteger() {
                return new BigInteger(next());
            }

        }
    }

    public static class Graph {

        int V;
        ArrayList<Integer>[] list;

        public Graph(int V) {
            this.V = V;
            list = new ArrayList[V + 1];
            for (int i = 1; i <= V; i++) {
                list[i] = new ArrayList<>();
            }

        }

        public void addEdge(int x, int y) {
            ArrayList<Integer> src = list[x];
            ArrayList<Integer> dest = list[y];
            src.add(y);
            dest.add(x);
        }

        public int count(int s, boolean[] contain) {
            int count = 0;
            LinkedList<Integer> queue = new LinkedList<Integer>();
            boolean[] visited = new boolean[this.V + 1];
            visited[s] = true;
            queue.add(s);
            while (!queue.isEmpty()) {
                s = queue.poll();
                if (contain[s]) {
                    count++;
                } else {
                    ArrayList<Integer> li = list[s];
                    for (int i = 0; i < li.size(); i++) {
                        int cur = li.get(i);
                        if (visited[cur] != true) {
                            visited[cur] = true;
                            queue.add(cur);
                        }
                    }
                }
            }
            return count;
        }
    }
}