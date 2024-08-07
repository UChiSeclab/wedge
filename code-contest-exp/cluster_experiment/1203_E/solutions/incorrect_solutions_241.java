
import java.util.*;

public class Result {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args){
        final int n=scanner.nextInt();
        int[] a=new int[150002];
        int num;
        for (int i=0;i<n;i++){
            num=scanner.nextInt();
            a[num]++;
        }
        for (int i=1;i<=150001;i++){
            if (a[i]>0&&a[i-1]==0&& i !=1){
                a[i-1]++;
                a[i]--;
            }
            if (a[i]>1){
                a[i+1]++;
                a[i]--;
            }
        }
        long ans=0;
        for (int i=1;i<150001;i++){
            if (a[i]>0)
                ans++;
        }
        System.out.println(ans);
        scanner.close();
    }
}

