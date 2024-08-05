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

    public static void main(String[] args) throws Exception {
        int t = getInt();
        while (t-- > 0) {
            int n = getInt();
            String s = br.readLine();
            int D = 0, K = 0;
            List<String> list = new ArrayList<>();

            for (int i = 0; i < s.length(); i++) {
                char subStr = s.charAt(i);

                if (subStr == 'D') {
                    D += 1;
                } else {
                    K += 1;
                }

                int d = D, k = K;

                String sub = "";
                if (d == 0) {
                    k = 1;
                    sub += d + "" + k;
                } else if (k == 0) {
                    d = 1;
                    sub += d + "" + k;
                } else if (d % k == 0) {
                    sub += d / k + "" + 1;
                } else if (k % d == 0) {
                    sub += 1 + "" + k / d;
                } else {
                    sub += d + "" + k;
                }
                list.add(sub);

            }

            HashMap<String, Integer> hashMap = new HashMap<>();

            for (int i = 0; i < list.size(); i++) {
                hashMap.put(list.get(i), hashMap.getOrDefault(list.get(i), 0) + 1);

                if (hashMap.containsKey(list.get(i))) {
                    System.out.print(hashMap.get(list.get(i)) + " ");
                } else {
                    System.out.print(1 + " ");
                }
                // System.out.println(hashMap);
            }
            System.out.println();

        }
    }

}