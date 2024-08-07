import java.util.*;

public class Codeforces685 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        for(int i = 0; i<t; i++) {
            System.out.println(solve(sc));
        }
    }

    public static String solve(Scanner sc) {
        int n = sc.nextInt();
        int st[][] = new int[n+1][25];
        int log[] = new int[25];
        for(int i = 2; i<n+1; i++){
            log[i] = log[i/2] + 1;
        }
        int[] a = new int[n];
        for (int i = 0; i<n; i++)
            a[i] = sc.nextInt();

        for(int i = 0; i<n; i++){
            st[i][0] = a[i];
        }

        for (int i=1; i<=log[n]; i++){
            for (int j = 0; j+(1<<i)<=n; j++){
                st[j][i] = Math.min(st[j][i-1], st[j+(1<<(i-1))][i-1]);
            }
        }

        for (int[] r: st)
            System.out.println(Arrays.toString(r));

        StringBuilder r = new StringBuilder("");
        for (int k = 1; k<=n; k++){
            List<Integer> l = new LinkedList<>();
            int lg = log[k];
            for (int i = 0; i+k-1<n; i++){
                l.add(Math.min(st[i][lg], st[i+k-(1<<lg)][lg]));
            }
            boolean xx = true;
            Collections.sort(l);
            for (int i = 1; i<=l.size(); i++){
                if(l.get(i-1)!=i){
                    xx = false;
                    break;
                }
            }
            if (xx)
                r.append("1");
            else r.append("0");
        }
        return r.toString();
    }

}