import java.util.*;
import java.io.*;
import java.lang.*;
import java.math.*;
public class E {
    public static void main(String[] args) throws Exception {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        int n = Integer.parseInt(bf.readLine());
        StringTokenizer st = new StringTokenizer(bf.readLine());
        Integer[] aa = new Integer[n]; for(int i=0; i<n; i++) aa[i] = Integer.parseInt(st.nextToken());
        Arrays.sort(aa);
        int[] a = new int[n];
        for(int i=0; i<n; i++) a[i] = aa[i].intValue();
        Set<Integer> finals = new HashSet<Integer>();
        for(int i=0; i<n; i++) {
          if((a[i] > 1) && (!finals.contains(a[i]-1))) a[i] -= 1;
          else if(!finals.contains(a[i])) a[i] -= 0;
          else if(!finals.contains(a[i]+1)) a[i] += 1;
          finals.add(a[i]);
        }
        out.println(finals.size());
        // int n = Integer.parseInt(st.nextToken());

        out.close(); System.exit(0);
    }
}
