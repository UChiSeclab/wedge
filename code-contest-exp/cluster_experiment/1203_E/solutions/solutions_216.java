import java.util.*;
import java.io.*;
import java.math.*;
import java.lang.*;
public class A {
    public static void main(String[] args) throws Exception {
        Scanner sc=new Scanner(System.in);
        PrintWriter pw=new PrintWriter(System.out);
        int n=sc.nextInt();
        int[] f=new int[150002];
        for(int i=0;i<n;i++)
            f[sc.nextInt()]++;
        for(int i=1;i<=150000;i++) {
            if((f[i]>1) && (f[i+1]<=1)) {
                f[i]--;
                f[i+1]++;
            }
        }
        for(int i=150000;i>1;i--) {
            if((f[i]>1) && (f[i-1]<=1)) {
                f[i]--;
                f[i-1]++;
            }
        }
        int ans=0;
        for(int i=1;i<f.length;i++)
            ans=(f[i]>0)?ans+1:ans;
        pw.println(ans);
        pw.close();
    }
}