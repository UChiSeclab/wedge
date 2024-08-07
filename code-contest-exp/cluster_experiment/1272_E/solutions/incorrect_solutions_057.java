import java.util.*;

public class Main {

    static int n;
    static int[] a;
    static int[] ans;
    static int[] cnt;

    public static int search(int i) {
        if (ans[i]!=0) return ans[i];

        cnt[i]++;
        if (cnt[i]>n) {
            ans[i] = -1;
            return ans[i];
        }
        if (0<=i+a[i] && i+a[i]<n && a[i+a[i]]%2!=a[i]%2) {
            ans[i]=1;
            return ans[i];
        } else if (0<=i-a[i] && i-a[i]<n && a[i-a[i]]%2!=a[i]%2) {
            ans[i]=1;
            return ans[i];
        } else {
            ans[i] = 1_000_000_000;
            if (0<=i+a[i] && i+a[i]<n && a[i+a[i]]%2==a[i]%2) {
                if (search(i+a[i])!=-1) {
                    ans[i]=Math.min(ans[i], search(i+a[i])+1);
                }
            }
            if (0<=i-a[i] && i-a[i]<n && a[i-a[i]]%2==a[i]%2) {
                if (search(i-a[i])!=-1) {
                    ans[i]=Math.min(ans[i], search(i-a[i])+1);
                }
            }
            if (ans[i]==1_000_000_000) {
                ans[i] = -1;
                return ans[i];
            } else {
                return ans[i];
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        n = Integer.parseInt(sc.next());
        a = new int[n];
        ans = new int[n];
        cnt = new int[n];
        for (int i=0;i<n;i++) {
            a[i] = Integer.parseInt(sc.next());
        }

        for (int i=0;i<n;i++) {
            search(i);
        }

        for (int i=0;i<n;i++) {
            if (i!=n-1) {
                System.out.print(ans[i]+" ");
            } else {
                System.out.println(ans[i]);
            }
        }
    }
}