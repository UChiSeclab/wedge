import java.util.*;
import java.io.*;
import static java.lang.Math.*;

public class A {

    public static void main(String[] args) throws Exception {
        FastScanner scanner = new FastScanner();

        int n = scanner.nextInt();
        int[] array = scanner.nextIntArray();

        int l1, l2;
        l1 = -1; l2 = 0;


        int max = -1;

        int i = 0;

        while (i < n && array[i] == array[0]) {
            l2++;
            i++;
        }

        int type = array[i - 1];

        while (i < n) {
            //System.out.println(l1 + " " + l2);
            if (array[i] != type) {
                type = array[i];
                max = max < min(l1, l2) * 2 ? min(l1, l2) * 2 : max;
                l1 = l2;
                l2 = 1;
            } else {
                l2++;
            }

            i++;
        }
        max = max < min(l1, l2) * 2 ? min(l1, l2) * 2 : max;

        System.out.println(max);
    }

    private static class FastScanner {
        private BufferedReader br;

        public FastScanner() { br = new BufferedReader(new InputStreamReader(System.in)); }
        public int[] nextIntArray() throws IOException {
            String line = br.readLine();
            String[] strings = line.trim().split("\\s+");
            int[] array = new int[strings.length];
            for (int i = 0; i < array.length; i++)
                array[i] = Integer.parseInt(strings[i]);
            return array;
        }
        public int nextInt() throws IOException { return Integer.parseInt(br.readLine()); }
    }
}