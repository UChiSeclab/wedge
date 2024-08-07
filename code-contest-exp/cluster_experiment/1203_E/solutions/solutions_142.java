import java.io.*;
import java.util.*;

public class Boxer {
    public static void main(String[] args) throws IOException
    {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(read.readLine());
        HashSet<Integer> hs = new HashSet<>();
        Integer[] w = new Integer[n];
        String[] st = read.readLine().split(" ");
        for (int i = 0; i < n; i++) {
            w[i] = Integer.parseInt(st[i]);
        }
        Arrays.sort(w);
        for (int i = 0; i < n; i++) {
            if (w[i] > 1 && !hs.contains(w[i] - 1)){
                hs.add(w[i] - 1);
            }
            else if (!hs.contains(w[i])){
                hs.add(w[i]);
            }
            else if (!hs.contains(w[i] + 1)){
                hs.add(w[i] + 1);
            }
        }
        System.out.println(hs.size());
    }
}
