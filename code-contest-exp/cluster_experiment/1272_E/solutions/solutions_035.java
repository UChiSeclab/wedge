import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Day9 {
    public static ArrayList<Integer>[] graph;
    public static boolean[] used;
    public static int[] array;
    public static int[] dist1;
    public static int[] dist2;
    public static void bfs1(){
        Queue<Integer> aaaa = new LinkedList<>();
        for(int i = 0; i < array.length; ++i){
            if(array[i] % 2 == 0){
                used[i] = true;
                dist1[i] = 0;
                aaaa.add(i);
            }
        }
        while(!aaaa.isEmpty()){
            int node = aaaa.poll();
            for(Integer to : graph[node]){
                if(!used[to]){
                    dist1[to] = dist1[node] + 1;
                    used[to] = true;
                    aaaa.add(to);
                }
            }
        }
    }
    public static void bfs2(){
        Queue<Integer> aaaa = new LinkedList<>();
        for(int i = 0; i < array.length; ++i){
            if(array[i] % 2 == 1){
                used[i] = true;
                dist2[i] = 0;
                aaaa.add(i);
            }
        }
        while(!aaaa.isEmpty()){
            int node = aaaa.poll();
            for(Integer to : graph[node]){
                if(!used[to]){
                    dist2[to] = dist2[node] + 1;
                    used[to] = true;
                    aaaa.add(to);
                }
            }
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(reader.readLine());
        StringTokenizer st = new StringTokenizer(reader.readLine());
        array = new int[n];
        dist1 = new int[n];
        dist2 = new int[n];
        for(int i = 0; i < n; ++i){
            dist1[i] = -1;
            dist2[i] = -1;
        }
        graph = new ArrayList[n];
        for(int i = 0; i < n; ++i){
            graph[i] = new ArrayList<>();
        }
        for(int i = 0; i < n; ++i){
            array[i] = Integer.parseInt(st.nextToken());
            if(array[i] + i < n){
                graph[array[i] + i].add(i);
            }
            if(i - array[i] >= 0){
                graph[i - array[i]].add(i);
            }
        }
        used = new boolean[n];
        bfs1();
        used = new boolean[n];
        bfs2();
        for(int i = 0; i < n; ++i){
            if(array[i] % 2 == 1){
                System.out.print(dist1[i] + " ");
            }
            else{
                System.out.print(dist2[i] + " ");
            }
        }
    }
}