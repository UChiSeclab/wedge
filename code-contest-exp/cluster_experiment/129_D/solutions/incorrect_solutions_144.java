import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Vector;

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);

        String s = in.readLine();
        int k = Integer.parseInt(in.readLine());

        Vector<Integer> positions = new Vector<Integer>();
        for (int i = 0; i < s.length(); i++)
            positions.add(i - 1);

        int enough_last = 0;
        String ans = "";

        while (true) {
            long[] count = new long[26];
            int[] enough = new int[26];
            Vector<Vector<Integer>> pos = new Vector<Vector<Integer>>();
            for (int i = 0; i < count.length; i++)
                pos.add(new Vector<Integer>());

            for (Integer j : positions) {
                int i = j + 1;
                if (i >= s.length())
                    break;
                int index = s.charAt(i) - 'a';
                count[index] += s.length() - i;
                pos.elementAt(index).add(i);
                enough[index]++;
            }

            if (k <= enough_last)
                break;
            else
                k -= enough_last;

            for (int i = 0; i < count.length; i++) {
                if (k <= count[i]) {
                    positions = (Vector<Integer>) pos.elementAt(i).clone();
                    enough_last = enough[i];
                    ans += (char) ('a' + i);
                    break;
                } else {
                    k -= count[i];
                }
            }
        }

        out.print(ans);
        out.close();
    }
}
