import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
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
        StringBuilder ans = new StringBuilder();
        boolean noline = false;
        Vector<Vector<Integer>> pos = new Vector<Vector<Integer>>();
        for (int i = 0; i < 26; i++)
            pos.add(new Vector<Integer>());

        long[] count = new long[26];
        int[] enough = new int[26];

        while (true) {
            Arrays.fill(count, 0);
            Arrays.fill(enough, 0);
            for (int i = 0; i < 26; i++)
                pos.elementAt(i).clear();

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

            boolean changed = false;
            positions.clear();

            for (int i = 0; i < count.length; i++)
                if (k <= count[i]) {
                    positions.addAll(pos.elementAt(i));
                    changed = true;
                    enough_last = enough[i];
                    ans.append((char) ('a' + i));
                    break;
                } else
                    k -= count[i];

            if (!changed) {
                noline = true;
                break;
            }
        }

        out.println(noline ? "No such line." : ans);
        out.close();
    }
}
