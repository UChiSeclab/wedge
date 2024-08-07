import java.util.*;
public class nearestParity {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] ls = new int[n];
        ArrayDeque<Integer>[] edgesIn = new ArrayDeque[n];
        for(int i = 0; i < n; i++){
            edgesIn[i] = new ArrayDeque<>();
            ls[i] = sc.nextInt();
        }
        boolean[] seen = new boolean[n];
        int[] minDistance = new int[n];
        ArrayDeque<Integer> explore = new ArrayDeque<>();
        for(int i = 0; i < n; i++){
            if(0<=i-ls[i] && i-ls[i]<n){
                edgesIn[i-ls[i]].add(i);
                if(ls[i]%2!=ls[i-ls[i]]%2){
                    minDistance[i]=1;
                    seen[i]=true;
                    explore.add(i);
                }
            }
            if(0<=i+ls[i] && i+ls[i]<n){
                edgesIn[i+ls[i]].add(i);
                if(ls[i]%2!=ls[i+ls[i]]%2){
                    minDistance[i]=1;
                    seen[i]=true;
                    explore.add(i);
                }
            }
        }
        while(explore.size()>0){
            int a = explore.poll();
            for(int i : edgesIn[a]){
                if(seen[i]==false){
                    minDistance[i]=minDistance[a]+1;
                    seen[i]=true;
                    explore.add(i);
                }
            }
        }
        for(int i = 0; i < n; i++){
            if(minDistance[i]==0) minDistance[i]=-1;
            System.out.println(minDistance[i]+" ");
        }
    }
}