import java.io.BufferedReader;
import java.util.HashMap;
import java.io.InputStreamReader;

// C. Diluc and Kaeya
public class C {
    public static StringBuilder sb;

    // prints the possible chunks at every "ith" index
    public static void printChunks(String str, int n) {
        int[] dcount = new int[n]; // prefix array of D
        int[] kcount = new int[n]; // prefix array of K

        // setting first value in both arrays
        if (str.charAt(0) == 'D') {
            dcount[0] = 1;
        }
        if (str.charAt(0) == 'K') {
            kcount[0] = 1;
        }

        // setting rest of the arrays
        for (int i = 1; i < n; i++) {
            if (str.charAt(i) == 'D') {
                dcount[i] = dcount[i - 1] + 1;
            } else {
                dcount[i] = dcount[i - 1];
            }

            if (str.charAt(i) == 'K') {
                kcount[i] = kcount[i - 1] + 1;
            } else {
                kcount[i] = kcount[i - 1];
            }
        }

        // hashmap stores the ocurences of sumplified (D/K) fractions at every "ith"
        // index
        // Dummy data:-
        // "0" --> 1
        // "inf" --> 0
        // "1/2" --> 2
        // "1/1" --> 1
        // "3/2" --> 2

        HashMap<String, Integer> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            int d = dcount[i]; // get D count till ith index
            int k = kcount[i]; // get K count till ith index

            String code = "";
            if (d == 0) {
                code = "0"; // numerator 0
            } else if (k == 0) {
                code = "inf"; // denominator 0
            } else {
                int hcf = gcd(d, k); // gcd to simplify the fraction
                d /= hcf;
                k /= hcf;
                code = d + "/" + k;
            }

            int val = map.getOrDefault(code, 0);
            map.put(code, val + 1);

            sb.append(map.get(code) + " ");
        }
        sb.append("\n");
    }

    // returns gcd of two numbers
    public static int gcd(int a, int b) {
        if (b == 0) {
            return a;
        }
        return gcd(b, a % b);
    }

    // ************ input ************
    static BufferedReader rd = new BufferedReader(new InputStreamReader(System.in));

    // integer
    static int i() {
        try {
            return Integer.parseInt(rd.readLine());
        } catch (Exception e) {
            return -1;
        }
    }

    // string
    static String s() {
        try {
            return rd.readLine();
        } catch (Exception e) {
            return "-1";
        }
    }

    // main function
    public static void main(String[] args) {
        sb = new StringBuilder();
        int test = i();
        for (int i = 0; i < test; i++) {
            int n = i();
            String str = s();

            try {
                printChunks(str, n);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        System.out.println(sb);
    }
}
