import java.util.ArrayList;
        import java.util.Arrays;
        import java.util.Scanner;

public class Singleton_Pattern {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int a[] = new int[n];
        for (int i = 0; i <n ; i++) {
            a[i]=sc.nextInt();
        }
        int temp =0;
        int count=0;
        int max1=0;
        int max2=0;
        for (int i = 0; i <n; i++) {
            if (i==0) {
                temp = a[i];
            }
            if (temp==a[i]){
                count++;
            }
            if (temp!=a[i]){
                //System.out.println("in");
                if (temp==1){
                    if (max1<=count)
                        max1=count;
                }
                else {
                    if (max2<=count)
                        max2=count;
                }
                temp=a[i];
                count=1;
            }
            if (i+1==n){
                //System.out.println("in2");
                if (temp==1){
                    if (max1<=count)
                        max1=count;
                }
                else {
                    if (max2<=count)
                        max2=count;
                }
            }

        }
        //System.out.println(max1);
       // System.out.println(max2);
        System.out.println(2*(Integer.min(max1,max2)));
    }
}
