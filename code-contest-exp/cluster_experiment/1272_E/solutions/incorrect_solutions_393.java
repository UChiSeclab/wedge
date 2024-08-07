import java.util.HashMap;
import java.util.Scanner;

public class Main{
    private static int[] isVisited;
    private static int[] a;
    private static int n;
    private static HashMap<Integer,Integer> map = new HashMap<>();
    private static int dfs(int start, int index, int target) {
        isVisited[index] = start;
        if(map.containsKey(index)){
            return map.get(index);
        }
        if ((a[index] & 1) == target) {
            isVisited[index] = -1;
            return 0;
        }
        int leftJump = index - a[index];
        int rightJump = index + a[index];
        int res = 0x7fffffff;
        int rl = -1;
        if (leftJump >= 0) {
            if (isVisited[leftJump] != start)
                rl = dfs(start, leftJump, target);
        }
        int rr = -1;
        if (rightJump < n) {
            if (isVisited[rightJump] != start)
                rr = dfs(start, rightJump, target);
        }
        if (rl == -1) {
            res = rr;
        } else if (rr == -1) {
            res = rl;
        } else {
            res = rl > rr ? rr : rl;
        }
        isVisited[index] = -1;
        if (res == -1) {
            map.put(index, -1);
        } else {
            map.put(index,res + 1);
        }
        return map.get(index);
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        n = scan.nextInt();
        isVisited = new int[n];
        for(int i = 0; i < n; i ++){
            isVisited[i] = -1;
        }
        a = new int[n];
        for(int i = 0; i < n; i ++){
            a[i] = scan.nextInt();
        }
        for(int i = 0; i < n; i ++){
            map.clear();
            if(i != 0){
                System.out.print(" ");
            }
            int t = 0;
            if((a[i] & 1) == 1) {
                t = dfs(i,i,0);
            }
            else {
                t = dfs(i,i,1);
            }
            System.out.print(t);
        }
        System.out.println();
    }
}