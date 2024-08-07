
import java.io.*;
import java.util.*;

public class Solution {

    private static final int MOD_1 = 1000000000 + 7;

    static class FastReader {

        BufferedReader br;
        StringTokenizer st;

        public FastReader() {
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

        /* --------------------------------------------------------------------------------------------------------------------------------------------------------------------- */
        void printArray(int[] arr) {
            System.out.println(Arrays.toString(arr));
        }

        void printArray(String[] arr) {
            System.out.println(Arrays.toString(arr));
        }

        void printArray(long[] arr) {
            System.out.println(Arrays.toString(arr));
        }

        void print(int data) {
            System.out.println(data);
        }

        void print(String data) {
            System.out.println(data);
        }

        void print(long data) {
            System.out.println(data);
        }

        int[] II(int n) {
            int[] d = new int[n];
            String[] arr = nextLine().split(" ");
            for (int i = 0; i < n; i++) {
                d[i] = Integer.parseInt(arr[i]);
            }

            return d;
        }

        String[] IS(int n) {
            return nextLine().split(" ");
        }

        long[] IL(int n) {
            long[] d = new long[n];
            String[] arr = nextLine().split(" ");
            for (int i = 0; i < n; i++) {
                d[i] = Long.parseLong(arr[i]);
            }
            return d;
        }

        static long gcd(long a, long b) {
            if (a == 0) {
                return b;
            }
            return gcd(b % a, a);
        }

        public long power(long x, long y, long p) {
            long res = 1;
            // out.println(x+" "+y);
            x = x % p;
            if (x == 0) {
                return 0;
            }

            while (y > 0) {
                if ((y & 1) == 1) {
                    res = (res * x) % p;
                }
                y = y >> 1;
                x = (x * x) % p;
            }
            return res;
        }

        void sieveOfEratosthenes(boolean prime[], int size) {
            Arrays.fill(prime, true);
            prime[0] = prime[1] = false;
            prime[2] = true;

            for (int p = 2; p * p < size; p++) {
                if (prime[p] == true) {
                    for (int i = p * p; i < size; i += p) {
                        prime[i] = false;
                    }
                }
            }
        }

        public long fact(long n) {
            long ans = 1;
            for (int i = 2; i <= n; i++) {
                ans = (ans * i) % MOD_1;
            }
            return ans;
        }

    }

    /* ---------------------------------------------------------------------------------------------------------------------------------------------------------------------- */
   
    public static void main(String[] args) {
        FastReader fs = new FastReader();
        int t =  Integer.parseInt(fs.nextLine());
        StringBuilder sb = new StringBuilder();
        while(t-- > 0){
            int n = fs.nextInt();
            String s =  fs.nextLine();
            Map<Double, Long> freq = new HashMap<>();
            long dCount = 0, kCount = 0;
            for(int i = 0; i < n; i++){
                if(s.charAt(i) == 'D') dCount++;
                else kCount++;
                double d;
                if(kCount == 0) d = -1.0;
                else d = (double) dCount / kCount;
                if(freq.containsKey(d)){
                    sb.append((freq.get(d) + 1) + " ");
                    freq.put(d, freq.get(d) + 1);
                }
                else{
                    sb.append("1 ");
                    freq.put(d, 1l);
                }
            }
            sb.append("\n");
        }
        System.out.print(sb);
    }
        

    /*  
      Collections.sort(arr, (a, b) -> Integer.compare(a[0], b[0]))
      arr.toArray(new int[arr.size()][])
     */
}
