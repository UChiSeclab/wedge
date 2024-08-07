import java.util.*;
import java.io.*;
import java.lang.*;
import java.math.*;
public class D {
    public static void main(String[] args) throws Exception {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        // Scanner scan = new Scanner(System.in);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out)); 
        int tt = Integer.parseInt(bf.readLine());
        for(int t=0; t<tt; t++) {
            int n = Integer.parseInt(bf.readLine());
            StringTokenizer st = new StringTokenizer(bf.readLine());
            int[] a = new int[n]; for(int i=0; i<n; i++) a[i] = Integer.parseInt(st.nextToken());
            int counter1 = 0;
            int counter2 = n-1;
            int c = 1;
            int[] ans = new int[n];
            int[] counts = new int[n+1];
            for(int i=0; i<n; i++) counts[a[i]]++;
            if(isPermutation(a, n)) ans[n-1] = 1;
            while(counter1 <= counter2) {
                if(counts[c] > 1) break;
                if(counts[c] == 0) {
                    c -= 1;
                    break;
                }
                if(a[counter1] == c) {
                    c++;
                    counter1++;
                }
                else if(a[counter2] == c) {
                    c++;
                    counter2--;
                }
                else {
                    break;
                }
            }
            for(int i=0; i<Math.min(n,c); i++) ans[i]=1;
            StringBuilder anss = new StringBuilder();
            for(int i=n-1; i>=0; i--) {
                anss.append(ans[i]);
            }
            out.println(anss.toString());

        }
        // StringTokenizer st = new StringTokenizer(bf.readLine());
        // int[] a = new int[n]; for(int i=0; i<n; i++) a[i] = Integer.parseInt(st.nextToken());
        // int n = Integer.parseInt(st.nextToken());
        // int n = scan.nextInt();
        
        out.close(); System.exit(0);
    }
    public static boolean isPermutation(int[] a, int n) {
        boolean[] visited = new boolean[n];
        for(int i=0; i<n; i++) {
            if(a[i]-1 < n) visited[a[i]-1] = true;
        }
        for(int i=0; i<n; i++) if(!visited[i]) return false;
        return true;
    }
}
