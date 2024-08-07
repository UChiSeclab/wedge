import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class DilucAndKaeya {
    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            int testCases = Integer.parseInt(reader.readLine());

            for (int i = 0; i < testCases; i++) {
                int n = Integer.parseInt(reader.readLine());
                System.out.println(solveTask(reader.readLine()));
            }
        }
    }

    private static String solveTask(String s) {
        HashMap<String, Integer> map = new HashMap<>();
        StringBuilder sb = new StringBuilder();
        int d = 0;
        int k = 0;
        for (int i = 0; i < s.length(); i++) {

            if (s.charAt(i) == 'D') {
                d++;
            } else {
                k++;
            }

            String ratio = getRatio(d, k);
            map.put(ratio, map.containsKey(ratio) ? map.get(ratio) + 1 : 1);
            sb.append(map.get(ratio)).append(" ");
        }
        return sb.toString();
    }

    private static String getRatio(final int numberOfD, final int numberOfK) {
        int a = numberOfD;
        int b = numberOfK;

        if (a == 0) {
            b = 1;
        } else if (b == 0) {
            a = 1;
        } else {
            if (a > b) {
                int temp = a;
                a = b;
                b = temp;
            }

            int gcd = gcd(a, b);
            a /= gcd;
            b /= gcd;
        }
        return a + ":" + b;
    }

    private static int gcd(int a, int b) {
        return a == 0 ? b : gcd(b % a, a);
    }
}
