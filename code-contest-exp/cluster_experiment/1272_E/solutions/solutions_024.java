//package main;

import java.io.*;
import java.util.*;

public final class Main {
    BufferedReader br;
    StringTokenizer stk;
    
    public static void main(String[] args) throws Exception {
        new Main().run();
    }
    
    {
        stk = null;
        br = new BufferedReader(new InputStreamReader(System.in));
    }
    
    long mod = 998244353;
    StringBuilder res = new StringBuilder(100005);
    
    void run() throws Exception {
        int n = ni();
        int[] a = new int[n + 1];
        for(int i = 1; i <= n; i++) {
            a[i] = ni();
        }
        
        Node[] graph = new Node[n + 1];
        for(int i = 1; i <= n; i++) {
            graph[i] = new Node();
        }
        for(int i = 1; i <= n; i++) {
            int back = i - a[i];
            int frnt = i + a[i];
            if(back >= 1 && back <= n) {
                graph[back].adj.add(i);
            }
            if(frnt >= 1 && frnt <= n) {
                graph[frnt].adj.add(i);
            }
        }
        
        int[] ans = new int[n + 1];
        
        Queue<Integer> queue;
        boolean[] visited;
        
        visited = new boolean[n + 1];
        queue = new LinkedList<>();
        for(int i = 1; i <= n; i++) {
            if(a[i] % 2 == 0) {
                queue.add(i);
                graph[i].dist = 0;
            }
        }
        while(!queue.isEmpty()) {
            int cur = queue.remove();
            if(visited[cur]) continue;
            visited[cur] = true;
            for(int adj : graph[cur].adj) {
                if(graph[adj].dist > graph[cur].dist + 1) {
                    graph[adj].dist = graph[cur].dist + 1;
                    queue.add(adj);
                }
            }
        }
        //System.out.println(Arrays.toString(graph));
        for(int i = 1; i <= n; i++) {
            if(a[i] % 2 == 1) {
                ans[i] = (graph[i].dist >= Integer.MAX_VALUE / 2) ? -1 : graph[i].dist;
            }
            graph[i].dist = Integer.MAX_VALUE / 2;
        }
        //System.out.println(Arrays.toString(ans));
        visited = new boolean[n + 1];
        queue = new LinkedList<>();
        for(int i = 1; i <= n; i++) {
            if(a[i] % 2 == 1) {
                queue.add(i);
                graph[i].dist = 0;
            }
        }
        while(!queue.isEmpty()) {
            int cur = queue.remove();
            if(visited[cur]) continue;
            visited[cur] = true;
            for(int adj : graph[cur].adj) {
                if(graph[adj].dist > graph[cur].dist + 1) {
                    graph[adj].dist = graph[cur].dist + 1;
                    queue.add(adj);
                }
            }
        }
        //System.out.println(Arrays.toString(graph));
        for(int i = 1; i <= n; i++) {
            if(a[i] % 2 == 0) {
                ans[i] = (graph[i].dist == Integer.MAX_VALUE / 2) ? -1 : graph[i].dist;
            }
        }
        
        for(int i = 1; i <= n; i++) {
            res.append(ans[i]).append(" ");
        }
        
        System.out.println(res.deleteCharAt(res.length() - 1));
    }
    
    class Node {
        int dist;
        HashSet<Integer> adj;
        public Node() {
            adj = new HashSet<>();
            dist = Integer.MAX_VALUE / 2;
        }
        @Override
        public String toString() {
            return "" + dist;
        }
    }
    
    //Reader & Writer
    String nextToken() throws Exception {
        if (stk == null || !stk.hasMoreTokens())
            stk = new StringTokenizer(br.readLine(), " ");
        return stk.nextToken();
    }

    String nt() throws Exception {
        return nextToken();
    }

    int ni() throws Exception {
        return Integer.parseInt(nextToken());
    }

    long nl() throws Exception {
        return Long.parseLong(nextToken());
    }
    
    double nd() throws Exception {
        return Double.parseDouble(nextToken());
    }
    
    //Some Misc methods
    long get(int l, int r, long[] a) {
        return l == 0 ? a[r] : a[r] - a[l - 1];
    }
    
    void shuffle(long[] a) {
        Random r = new Random();
        for(int i = 0; i < a.length; i++) {
            int idx = r.nextInt(a.length);
            long temp = a[i];
            a[i] = a[idx];
            a[idx] = temp;
        }
    }
}