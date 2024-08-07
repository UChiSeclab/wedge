import java.util.*;
import java.io.*;

public class Main {
    static Scanner sc = new Scanner(System.in);
    static PrintWriter out = new PrintWriter(System.out);
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    //    long mod = 998244353;
    long mod = (long)1e9+7;
    public static void main(String[] args) throws Exception {
        Main main = new Main();
        int t = sc.nextInt();
//        int t= 1;
        /*
        while(t-->0){
            main.solve();
        }*/
        for(int i=1; i<=t; i++){
            main.solve();
        }
        out.flush();
    }

    void solve() throws Exception {
        int n = neInt();
        int[] a = new int[n+2];
        for(int i=1; i<=n; i++) a[i] = neInt();
        a[0] = -1; a[n+1] = -1;
        int[] prev = new int[n+2], suf = new int[n+2];
        Stack<Integer> st = new Stack<>();
        // front to end
        st.push(0);
        for(int i=1; i<=n; i++){
            while(st.size()>0&&a[st.peek()]>=a[i]) st.pop();
            prev[i] = i-st.peek();
            st.push(i);
        }
        st.clear();
        st.push(n+1);
        for(int i=n; i>0; i--){
            while(st.size()>0&&a[st.peek()]>=a[i]) st.pop();
            suf[i] = st.peek()-i;
            st.push(i);
        }
        List<Integer>[] rec = new List[n+1];
        for(int i=0; i<=n; i++) rec[i] = new LinkedList<>();
        for(int i=1; i<=n; i++){
            int v = a[i], len = prev[i]+suf[i]-1;
            rec[v].add(len);
        }
        StringBuilder sb = new StringBuilder();
        int mini = Integer.MAX_VALUE/2;
        for(int v=1; v<=n; v++){
            int tempMax=0;
            for(int i:rec[v]) tempMax = Math.max(tempMax, i);
            mini = Math.min(mini, tempMax);
            if(mini<n+1-v) sb.append(0);
            else sb.append(1);
        }
        out.println(sb.reverse().toString());
    }
    String neS(){
        return sc.next();
    }
    int neInt(){
        return Integer.parseInt(sc.next());
    }
    long neLong(){
        return Long.parseLong(sc.next());
    }

    int paIn(String s){return Integer.parseInt(s);}

}