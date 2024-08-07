import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class P1138ASushi {

    public static void doJob(String[] args) {
        int n = nInts(1, args[0])[0];
        int[] s = nInts(n, args[1]);

        int pL = 0;
        int cur = -1;
        int cL = 0;

        int max = 0;

        for (int i = 0; i < n; i++) {
            if (cur == s[i]) {
                cL++;
                max = Math.max(Math.min(pL, cL), max);
            } else {
                max = Math.max(Math.min(pL, cL), max);
                pL = cL;
                cur = s[i];
                cL = 1;
            }
        }

        System.out.println(max);
    }

    public static void main(String[] args) throws IOException {
        doJob(readLines(System.in));
    }

    public static String readAll(InputStream stream) throws IOException {
        return new BufferedReader(new InputStreamReader(new BufferedInputStream(stream))).readLine();
    }

    public static String[] readLines(InputStream stream) throws IOException {
        List<String> arr = new ArrayList<>();
        BufferedReader r = new BufferedReader(new InputStreamReader(stream));

        String line;
        while ((line = r.readLine()) != null) {
            arr.add(line);
        }

        return arr.toArray(new String[0]);
    }

    public static int[] nInts(int n, String line) {
        int[] ints = new int[n];
        for (int i = 0, length = line.length(), cnt = 0, prev = 0; i < length && cnt < n; i++) {
            if (line.charAt(i) == ' ') {
                continue;
            }

            prev = i;
            while (i < length && line.charAt(i) != ' ') {
                i++;
            }

            ints[cnt++] = Integer.parseInt(line.substring(prev, i));
        }
        return ints;
    }

    public static void test(String str) throws IOException {
        doJob(readLines(new ByteArrayInputStream(str.getBytes())));
    }

}
