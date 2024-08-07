import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class DilucAndKaeya {
    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            int testCases = Integer.parseInt(reader.readLine());

            for (int i = 0; i < testCases; i++) {
                reader.readLine();
                System.out.println(solveTask(reader.readLine()));
            }
        }
    }

    private static String solveTask(String s) {
        HashMap<String, Integer> map = new HashMap<>();
        StringBuilder sb = new StringBuilder();
        int numberOfD = 0;
        int numberOfK = 0;
        for (int i = 0; i < s.length(); i++) {

            if (s.charAt(i) == 'D') {
                numberOfD++;
            } else {
                numberOfK++;
            }

            String ratio = getRatio(numberOfD, numberOfK);
            if (map.containsKey(ratio)) {
                map.put(ratio, map.get(ratio) + 1);
            } else {
                map.put(ratio, 1);
            }
            sb.append(map.get(ratio)).append(" ");
        }
        return sb.toString();
    }

    private static String getRatio(final int numberOfD, final int numberOfK) {
        int a = numberOfD, b = numberOfK;
        if (a == 0) {
            b = 1;
        } else if (b == 0) {
            a = 1;
        } else {
            int greatestCommonDivisor = getGreatestCommonDivisor(a, b);
            a /= greatestCommonDivisor;
            b /= greatestCommonDivisor;
        }

        return a + ":" + b;
    }

    private static int getGreatestCommonDivisor(int a, int b) {
        if (a > b) {
            int temp = a;
            a = b;
            b = temp;
        }
        return a == 0 ? b : getGreatestCommonDivisor(b % a, a);
    }
}
