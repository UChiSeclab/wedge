// package codeforce.Training1900;

import java.io.PrintWriter;
import java.util.*;

public class NearestOppositeParity {
    //    MUST SEE BEFORE SUBMISSION
//    check whether int part would overflow or not, especially when it is a * b!!!!

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        PrintWriter pw = new PrintWriter(System.out);
//        int t = sc.nextInt();
        int t = 1;
        for (int i = 0; i < t; i++) {
            solve(sc, pw);
        }
        pw.close();
    }

    static void solve(Scanner in, PrintWriter out){
        int n = in.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = in.nextInt();
        }
        int[] dp = new int[n];
        Arrays.fill(dp, -1);
        Map<Integer, List<Integer>> mp = new HashMap<>();
        LinkedList<Integer> q = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            int lm = i - arr[i], rm = i + arr[i];
            boolean find = false;
            if (lm >= 0){
                if (!mp.containsKey(lm)) mp.put(lm, new ArrayList<>());
                mp.get(lm).add(i);
                if (arr[lm] % 2 != arr[i] % 2) find = true;
            }
            if (rm < n){
                if (!mp.containsKey(rm)) mp.put(rm, new ArrayList<>());
                mp.get(rm).add(i);
                if (arr[rm] % 2 != arr[i] % 2) find = true;
            }
            if(find) {
                dp[i] = 1;
                q.add(i);
            }
        }
        while (!q.isEmpty()){
            int v = q.poll();
            int cost = dp[v];
            if (!mp.containsKey(v)) continue;
            for(int nxt : mp.get(v)){
                if (dp[nxt] != -1) continue;
                dp[nxt] = cost + 1;
                q.add(nxt);
            }
        }
        for (int i = 0; i < n; i++) {
            out.print(dp[i]+" ");
        }
        out.println();
    }


}
