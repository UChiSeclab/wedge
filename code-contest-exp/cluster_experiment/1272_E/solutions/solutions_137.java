//Created by Aminul on 12/12/2019.

import java.io.*;
import java.util.*;

import static java.lang.Math.*;

public class E {
    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(System.in);
        PrintWriter pw = new PrintWriter(System.out);
        int n = in.nextInt();
        int arr[] = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            arr[i] = in.nextInt();
        }
        int res[] = bfs(n, arr);
        for (int i = 1; i <= n; i++) {
            pw.print(res[i] + " ");
        }
        pw.println();

        pw.close();
    }


    static int[] bfs(int n, int arr[]) {
        int vis[] = new int[n + 1];
        Arrays.fill(vis, -1);
        Queue<Integer> queue = new ArrayDeque<>();
        ArrayDeque<Integer> g[] = genDQ(n + 1);
        for (int i = 1; i <= n; i++) {
            int prev = i - arr[i], next = i + arr[i];
            if (prev >= 1) {
                g[prev].addLast(i);
            }
            if (next <= n) {
                g[next].addLast(i);
            }
            if (prev >= 1 && arr[i] % 2 != arr[prev] % 2 && vis[i] == -1) {
                queue.add(i);
                vis[i] = 1;
            }
            if (next <= n && arr[i] % 2 != arr[next] % 2 && vis[i] == -1) {
                queue.add(i);
                vis[i] = 1;
            }
        }

        while (!queue.isEmpty()) {
            int top = queue.poll();
            for (int child : g[top]) {
                if (vis[child] == -1) {
                    vis[child] = vis[top] + 1;
                    queue.add(child);
                }
            }
        }

        return vis;
    }

    static <T> ArrayDeque<T>[] genDQ(int n) {
        ArrayDeque<T> list[] = new ArrayDeque[n];
        for (int i = 0; i < n; i++) list[i] = new ArrayDeque<T>();
        return list;
    }
    static void debug(Object... obj) {
        System.err.println(Arrays.deepToString(obj));
    }
}