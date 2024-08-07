import java.text.DecimalFormat;
import java.util.*;
public class CodeForces1203E {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        int n=sc.nextInt();
        boolean x[]=new boolean[150002];
        Integer aa[]=new Integer[n];
        for (int i=0;i<n;i++)aa[i]=sc.nextInt();
        Arrays.sort(aa);
        for (int i=n-1;i>=0;i--){
            int a=aa[i];
            if (!x[a+1]){
                x[a+1]=true;
            }
            else {
                if (!x[a])x[a]=true;
                else x[a-1]=true;
            }
        }
        int cnt=0;
        for (int i=1;i<150002;i++){
            if (x[i])cnt++;
        }
        System.out.println(cnt);
    }
 
}