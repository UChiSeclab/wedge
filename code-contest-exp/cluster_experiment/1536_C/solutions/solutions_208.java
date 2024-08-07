import java.io.*;
import java.util.*;

public class Codeforces {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static long mod = (long) (Math.pow(10, 9) + 7);

    static int getInt() throws IOException {
        return Integer.parseInt(br.readLine());
    }

    static long getLong() throws IOException {
        return Long.parseLong(br.readLine());
    }

    static int[] getIntArray() throws IOException {
        String[] arString = br.readLine().split(" ");
        int[] ar = new int[arString.length];
        for (int i = 0; i < ar.length; i++) {
            ar[i] = Integer.parseInt(arString[i]);
        }
        return ar;
    }

    static long[] getLongArray() throws IOException {
        String[] arString = br.readLine().split(" ");
        long[] ar = new long[arString.length];
        for (int i = 0; i < ar.length; i++) {
            ar[i] = Long.parseLong(arString[i]);
        }
        return ar;
    }

    static List<Integer> getIntList() throws IOException {
        String[] arString = br.readLine().split(" ");
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < arString.length; i++) {
            list.add(Integer.parseInt(arString[i]));
        }
        return list;
    }

    static List<Long> getLongList() throws IOException {
        String[] arString = br.readLine().split(" ");
        List<Long> list = new ArrayList<>();
        for (int i = 0; i < arString.length; i++) {
            list.add(Long.parseLong(arString[i]));
        }
        return list;
    }

    static class Pair {
        Integer first;
        Integer second;

        Pair(Integer f, Integer s) {
            this.first = f;
            this.second = s;
        }

        public String toString() {
            return "(" + this.first + ", " + this.second + ")";
        }

        @Override
        public boolean equals(Object object) {
            if (((Pair) object).first == this.first && ((Pair) object).second == this.second
                    && object instanceof Pair) {
                return true;
            } else {
                return false;
            }
        }
    }

    public static int gcd(int a, int b) {
        if (a > b) {
            int t = a;
            a = b;
            b = t;
        }
        if (a == 0)
            return b;
        return gcd(b % a, a);
    }

    public static void main(String[] args) throws Exception {
        int t = getInt();
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            getInt();
            char[] s = br.readLine().toCharArray();
            int D = 0, K = 0;
            HashMap<String, Integer> hashMap = new HashMap<>();

            for (char subStr: s) {

                if (subStr == 'D') {
                    D += 1;
                } else {
                    K += 1;
                }

                int d = D, k = K;

                if (d == 0) {
                    k = 1;
                } else if (k == 0) {
                    d = 1;
                } else {
                    int gcd = gcd(k, d);
                    k /= gcd;
                    d /= gcd;
                }
                String sub = k + ":" + d;
                hashMap.put(sub, hashMap.getOrDefault(sub, 0) + 1);
                sb.append(hashMap.get(sub) + " ");
            }
            sb.append("\n");
        }
        System.out.println(sb);
    }

}