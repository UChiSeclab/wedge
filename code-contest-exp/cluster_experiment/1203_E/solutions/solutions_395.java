import java.util.*;
import java.io.*;

public class Boxers {
    public static void main(String[] args) throws IOException {
        int N = nextInt();
        TreeSet<Integer> set = new TreeSet<>();
        set.add(0);
        int[] boxers = new int[150001];
        for (int i = 0; i < N; i++)
        {
            boxers[nextInt()] += 1;
        }
        for (int i = 1; i < boxers.length; i++)
        {
            while (boxers[i] > 0)
            {
                int floor = set.floor(i);
                if (floor < (i - 1))
                {
                    set.add(i - 1);
                } else if (floor == (i - 1))
                {
                    set.add(i);
                } else {
                    set.add(i + 1);
                }
                boxers[i]--;
            }
        }
        System.out.println(set.size() - 1);
    }
    private static StringTokenizer line = new StringTokenizer("");
    private static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static int nextInt() throws IOException
    {
        return Integer.parseInt(nextString());
    }
    private static String nextString() throws IOException
    {
        if (line.hasMoreTokens())
        {
            return line.nextToken();
        } else {
            line = new StringTokenizer(in.readLine());
            return line.nextToken();
        }
    }
}
