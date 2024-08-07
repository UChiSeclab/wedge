import java.io.*;
import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    private static final boolean FROM_FILE = false;
    private static final boolean TO_FILE = false;

    public static void main(String[] args) throws IOException {
        BufferedReader br;
        if (FROM_FILE) {
            br = new BufferedReader(new FileReader("C:/home/google/c_memorable_moments.txt"));
        } else {
            br = new BufferedReader(new InputStreamReader(System.in));
        }
        PrintWriter pw;
        if (TO_FILE) {
            pw = new PrintWriter("C:/home/google/c_output.txt");
        } else {
            pw = new PrintWriter(System.out);
        }

        // Algorithm:
        int n = Integer.parseInt(br.readLine());
        int[] t = new int[n];
        String[] line = br.readLine().split(" ");
        for (int i = 0; i < n; i++) {
            t[i] = Integer.parseInt(line[i]);
        }
        int max = 0;
        int count1 = 0;
        int count2 = 0;
        int current = -1;
        for (int i = 0; i < n; i++) {
            int a = t[i];
            if (a != current) {
                max = Math.max(max, Math.min(count1, count2));
                if (a == 1) {
                    count1 = 0;
                } else {
                    count2 = 0;
                }
            }
            if (a == 1) {
                count1++;
            } else {
                count2++;
            }
            current = a;
        }
        max = Math.max(max, Math.min(count1, count2));
        pw.println(max * 2);

        pw.flush();
    }
}