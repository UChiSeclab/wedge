
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Andy Phan
 */
public class e {
    public static void main(String[] args) {
        FS in = new FS(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int[] arr = new int[n];
        ArrayList<Integer>[] can = new ArrayList[n];
        for(int i = 0; i < n; i++) can[i] = new ArrayList<>();
        for(int i = 0; i < n; i++) {
            arr[i] = in.nextInt();
            if(i-arr[i] >= 0) can[i-arr[i]].add(i);
            if(i+arr[i] < n) can[i+arr[i]].add(i);
        }
        int[] res = new int[n];
        Arrays.fill(res, -1);
        {
            ArrayDeque<Integer> q = new ArrayDeque<>();
            boolean[] vis = new boolean[n];
            for(int i = 0; i < n; i++) {
                if(arr[i]%2 == 0) {
                    vis[i] = true;
                    q.add(i);
                    q.add(0);
                }
            }
            while(!q.isEmpty()) {
                int next = q.poll();
                int dist = q.poll();
                if(arr[next]%2 == 1) res[next] = dist;
                for(int e : can[next]) {
                    if(!vis[e]) {
                        vis[e] = true;
                        q.add(e);
                        q.add(dist+1);
                    }
                }
            }
        }
        {
            ArrayDeque<Integer> q = new ArrayDeque<>();

            boolean[] vis = new boolean[n];
            for(int i = 0; i < n; i++) {
                if(arr[i]%2 == 1) {
                    vis[i] = true;
                    q.add(i);
                    q.add(0);
                }
            }
            while(!q.isEmpty()) {
                int next = q.poll();
                int dist = q.poll();
                if(arr[next]%2 == 0) res[next] = dist;
                for(int e : can[next]) {
                    if(!vis[e]) {
                        vis[e] = true;
                        q.add(e);
                        q.add(dist+1);
                    }
                }
            }
        }
        for(int i = 0; i < n; i++) {
            out.print(res[i] + " ");
        }
        out.println();
        out.close();
    }
    
    static class FS {

        BufferedReader in;
        StringTokenizer token;
        
        public FS(InputStream str) {
            in = new BufferedReader(new InputStreamReader(str));
        }
        
        public String next() {
            if (token == null || !token.hasMoreElements()) {
                try {
                    token = new StringTokenizer(in.readLine());
                } catch (IOException ex) {
                }
                return next();
            }
            return token.nextToken();
        }
        
        public int nextInt() {
            return Integer.parseInt(next());
        }
    }
}
