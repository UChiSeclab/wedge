import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Objects;
import java.util.StringTokenizer;

public final class Main {
    static char[] chars = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    static char[] chars1 = {'#', '*', '&'};
    static char[] chars2 = {'1', '2', '3', '4', '5', '6', '7', '8', '9', '0'};

    public void solve() {

        int n = in.nextInt();
        int k = in.nextInt();
        int a[] = new int[n];
        int f[] = new int[(int) 1e4 + 1];
        for (int i = 0; i < n; i++) {
            a[i] = in.nextInt();
            f[a[i]]++;
        }
        int[] hm = new int[(int) 1e7];
        Arrays.fill(hm, -1);
        Arrays.sort(a);
        long answer = 0;
        for (int i = 0; i < n; i++) {
            f[a[i]]--;
            long answtmp = answer;
            for (int j = i + 1; j < n; j++) {
                int z = a[i] ^ a[j];
                if (hm[z] < 0) {
                    hm[z] = toB(z);
                }
                if (hm[z] == k) {
                    answer += f[a[j]];
                }
                j += f[a[j]] - 1;
            }
            answtmp = answer - answtmp;
            int itmp=i;
            while (i+1<n && a[i + 1] == a[i]) {
                answer += answtmp;
                if (k == 0) {
                    answer-=(i-itmp+1);
                }
                f[a[i]]--;
                i++;
            }
        }
        out.println(answer);
    }

    int toB(int i) {
        i = i - ((i >> 1) & 0x55555555);
        i = (i & 0x33333333) + ((i >> 2) & 0x33333333);
        return (((i + (i >> 4)) & 0x0F0F0F0F) * 0x01010101) >> 24;
    }


    static class Library {
        static boolean isPrime(int a) {
            if (a == 1) {
                return false;
            }
            for (int i = 2; i * i <= a; i++) {
                if (a % i == 0)
                    return false;
            }
            return true;
        }

        static long bpow(long x, long n, long m) {
            long count = 1;
            if (n == 0) {
                return 1;
            }
            while (n > 0) {
                if ((n & 1) == 1) {
                    count *= x;
                    count %= m;
                }
                x *= x;
                x %= m;
                n >>= 1;
            }
            return count % m;
        }

        static long GCD(long a, long b) {
            if (b == 0) return a;
            return GCD(b, a % b);
        }

        static long lcm(long a, long b) {
            return (a * b) / Library.GCD(a, b);
        }
    }

    class Pair<X, Y> implements Comparable<Pair<X, Y>> {

        public X first;
        public Y second;

        public Pair(X first, Y second) {
            this.first = first;
            this.second = second;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 11 * hash + Objects.hashCode(this.first);
            hash = 11 * hash + Objects.hashCode(this.second);
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Pair<?, ?> other = (Pair<?, ?>) obj;
            if (!Objects.equals(this.first, other.first)) {
                return false;
            }
            if (!Objects.equals(this.second, other.second)) {
                return false;
            }
            return true;
        }

        @Override
        public int compareTo(Pair<X, Y> p) {
            return ((Comparable<X>) first).compareTo(p.first);
        }

        @Override
        public String toString() {
            return first + " " + second;
        }
    }

    class Node<E> {
        E element;
        Node<E> left;
        Node<E> right;
        Node<E> prev;

        Node(E element, Node left, Node right, Node prev) {
            this.element = element;
            this.left = left;
            this.right = right;
            this.prev = prev;
        }
    }

    //    -------------I/O-------------    \\
    private FScanner in;

    private PrintWriter out;

    public static void main(String[] args) {
        new Main().runIO();
    }


    void run() {
        try {
            in = new FScanner(new File("input.txt"));
            out = new PrintWriter(new BufferedWriter(new FileWriter(new File("output.txt"))));
            solve();
            out.close();
            in.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    void runIO() {
        in = new FScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        solve();
        out.close();
        in.close();
    }

    class FScanner {

        private BufferedReader br;
        private StringTokenizer st;

        public FScanner() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        public FScanner(File f) {
            try {
                br = new BufferedReader(new FileReader(f));
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }

        void useString(String s) {
            if (s != null) {
                st = new StringTokenizer(s);
            }
        }

        void updateST() {
            while (st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        String next() {
            updateST();
            return st.nextToken();
        }

        Long[] nextLongArray() {
            updateST();
            LinkedList<Long> ll = new LinkedList<>();
            while (st.hasMoreElements()) {
                ll.add(Long.parseLong(st.nextToken()));
            }
            Long[] arr = new Long[1];
            return ll.toArray(arr);
        }

        Integer[] nextIntArray() {
            updateST();
            LinkedList<Integer> ll = new LinkedList<>();
            while (st.hasMoreElements()) {
                ll.add(Integer.parseInt(st.nextToken()));
            }
            Integer[] arr = new Integer[1];
            return ll.toArray(arr);
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
            updateST();
            StringBuilder sb = new StringBuilder(st.nextToken());
            while (st.hasMoreElements()) {
                sb.append(st.nextToken());
            }
            return sb.toString();
        }

        void close() {
            try {
                br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }
}