import java.io.*;
import java.util.*;

public class r605p5 {
    private static BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
    private static StringTokenizer tk;
    private static PrintWriter pw = new PrintWriter(System.out);

    private static void next()throws IOException{
        if(tk == null || !tk.hasMoreTokens())   
            tk = new StringTokenizer(r.readLine());
    }

    private static int nextInt()throws IOException{
        next();
        return Integer.parseInt(tk.nextToken());
    }

    private static long nextLong()throws IOException{
        next();
        return Long.parseLong(tk.nextToken());
    }

    private static String readString()throws IOException{
        next();
        return tk.nextToken();
    }

    private static double nextDouble()throws IOException{
        next();
        return Double.parseDouble(tk.nextToken());
    }

    private static int[] intArray(int n)throws IOException{
        next();
        int arr[] = new int[n];

        for(int i=0; i<n; i++)
            arr[i] = nextInt();

        return arr;
    }

    private static long[] longArray(int n)throws IOException{
        next();
        long arr[] = new long[n];

        for(int i=0; i<n; i++)
            arr[i] = nextLong();

        return arr;
    }

    static class Pair{
        int i, j;
        Pair(int i, int j){
            this.i = i;
            this.j = j;
        }
    }

    public static void main(String args[])throws IOException{
        int n = nextInt(), a[] = intArray(n);

        int dist[][] = new int[n][2];
        Queue<Pair> q = new LinkedList<>();
        ArrayList<ArrayList<Integer>> ls = new ArrayList<>();

        for(int i=0; i<n; i++)
            ls.add(new ArrayList<>());

        for(int i=0; i<n; i++){
            int p = a[i]%2;
            dist[i][p] = 0;
            dist[i][1-p] = Integer.MAX_VALUE;
            q.add(new Pair(i, p));
            
            if(i+a[i] < n)
                ls.get(i+a[i]).add(i);
            if(i-a[i] >= 0)
                ls.get(i-a[i]).add(i);
        }

        while(!q.isEmpty()){
            Pair c = q.poll();
            int nd = c.i, p = c.j;

            for(Integer v : ls.get(nd)){
                if(dist[v][p] > dist[nd][p] + 1){
                    dist[v][p] = dist[nd][p] + 1;
                    q.add(new Pair(v, p));
                }
            }
        }

        for(int i=0; i<n; i++){
            int r = Math.max(dist[i][0], dist[i][1]);
            if(r == Integer.MAX_VALUE)
                pw.print(-1+" ");
            else
                pw.print(+r+" ");
        }

        pw.flush();
        pw.close();
    }
}