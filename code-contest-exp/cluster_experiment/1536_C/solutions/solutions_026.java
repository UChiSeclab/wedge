import java.io.*;
import java.util.*;

public class A {

    static boolean LOCAL = false;

    static int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a%b);
    }

    public static void main(String[] args) throws  java.lang.Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(LOCAL ? (new FileInputStream("/home/xiongjx751/Workspace/AlgJava/src/in.txt")) : System.in));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(LOCAL ? (new FileOutputStream("/home/xiongjx751/Workspace/AlgJava/src/out.txt")) : System.out));

        int T = Integer.parseInt(in.readLine());
        while (T-- > 0) {
            int n = Integer.parseInt(in.readLine());
            char[] s = in.readLine().toCharArray();
            int D = 0, K = 0;
            HashMap<String, Integer> mp = new HashMap<>();
            for (int i = 0; i < n; i++) {
                D += (s[i] == 'D') ? 1 : 0;
                K += (s[i] == 'K') ? 1 : 0;
                int g = gcd(D, K);
                String str = new String((D/g) + ":" + (K/g));
                int a = mp.containsKey(str) ? mp.get(str) + 1 : 1;
                out.write(a + " ");
                mp.put(str, a);
            }
            out.write("\n");
            out.flush();
        }

    }

}

