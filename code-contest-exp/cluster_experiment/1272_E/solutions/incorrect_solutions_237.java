import java.awt.*;
import java.awt.desktop.PrintFilesEvent;
import java.lang.reflect.Array;
import java.util.*;
import java.lang.*;
import java.io.*;
import java.util.List;

public class Solution {
    public static void main(String[] args) throws java.lang.Exception {
        out = new PrintWriter(new BufferedOutputStream(System.out));
        sc = new FastReader();
        int test = 1;
        for (int t = 1; t <= test; t++) {
            int n = sc.nextInt();
            int[] arr = new int[n];
            for (int i = 0; i < n; i++) {
                arr[i] = sc.nextInt();
            }
            int[] min_jumps = new int[n];
            Arrays.fill(min_jumps, -1);
            boolean[] visited = new boolean[n];
            for (int i = 0; i < n; i++) {
                if (!visited[i]) {
                    dfs(min_jumps, visited, arr, n, i);
                }
            }
            for (int i = 0; i < n; i++) {
                out.print(min_jumps[i] + " ");
            }
        }
        out.close();
    }

    private static int dfs(int[] min_jumps, boolean[] visited, int[] arr, int n, int i) {
        if (visited[i]) {
            return min_jumps[i];
        }
        visited[i] = true;
        int left = 0, right = 0;
        if (i - arr[i] < 0 && i + arr[i] >= n) {
            min_jumps[i] = -1;
            return min_jumps[i];
        }
        if (i + arr[i] < n) {
            int parity = arr[i + arr[i]];
            right = dfs(min_jumps, visited, arr, n, i + arr[i]);
            if ((parity % 2 == 0 && arr[i] % 2 != 0) || (parity % 2 != 0 && arr[i] % 2 == 0)) {
                min_jumps[i] = 1;
            }
        }
        if (i - arr[i] >= 0) {
            int parity = arr[i - arr[i]];
            left = dfs(min_jumps, visited, arr, n, i - arr[i]);
            if ((parity % 2 == 0 && arr[i] % 2 != 0) || (parity% 2 != 0 && arr[i] % 2 == 0)) {
                min_jumps[i] = 1;
            }
        }
        if (min_jumps[i] == 1) {
            return min_jumps[i];
        }
        if (left == -1 && right == -1) {
            return min_jumps[i];
        }
        int min = Integer.MAX_VALUE;
        if (left > 0) {
            min = Math.min(min, left);
        }
        if (right > 0) {
            min = Math.min(min, right);
        }
        min_jumps[i] = min + 1;
        return min_jumps[i];
    }

    public static FastReader sc;
    public static PrintWriter out;
    static class FastReader
    {
        BufferedReader br;
        StringTokenizer st;

        public FastReader()
        {
            br = new BufferedReader(new
                    InputStreamReader(System.in));
        }

        String next()
        {
            while (st == null || !st.hasMoreElements())
            {
                try
                {
                    st = new StringTokenizer(br.readLine());
                }
                catch (IOException  e)
                {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt()
        {
            return Integer.parseInt(next());
        }

        long nextLong()
        {
            return Long.parseLong(next());
        }

        double nextDouble()
        {
            return Double.parseDouble(next());
        }

        String nextLine()
        {
            String str = "";
            try
            {
                str = br.readLine();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            return str;
        }
    }
}