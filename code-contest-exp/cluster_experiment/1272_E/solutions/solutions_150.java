import java.util.*;
import java.lang.*;
import java.io.*;

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
            Map<Integer, List<Integer>> graph = new HashMap<>();
            for (int i = 0; i < 200020; i++) {
                graph.put(i, new ArrayList<>());
            }
            for (int i = 0; i < n; i++) {
                if (i - arr[i] >= 0) {
                    graph.get(i - arr[i]).add(i);
                }
                if (i + arr[i] < n) {
                    graph.get(i + arr[i]).add(i);
                }
            }
            int[] ans = new int[n];
            for (int d = 0; d < 2; d++) {
                int[] dist = new int[n];
                Arrays.fill(dist, -1);
                Queue<Integer> q = new LinkedList();
                for (int i = 0; i < n; i++) {
                    if ((arr[i] & 1) == d) {
                        dist[i] = 0;
                        q.add(i);
                    }
                }
                while (!q.isEmpty()) {
                    int u = q.peek();
                    q.poll();
                    for (int v : graph.get(u)) {
                        if (dist[v] == -1) {
                            dist[v] = dist[u] + 1;
                            q.add(v);
                        }
                    }
                }
                for (int i = 0; i < n; i++) {
                    if ((arr[i] & 1) != d) {
                        ans[i] = dist[i];
                    }
                }
            }
            for (int i = 0; i < n; i++) {
                out.print(ans[i] + " ");
            }
         }
        out.close();
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