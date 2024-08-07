import java.io.*;
import java.util.*;

public class Main{
    public static void main(String agrgs[]){
        Scanner sc=new Scanner(System.in);
        int n=sc.nextInt();
        int a[]=new int[n+1];
        for(int i=1;i<=n;i++){
            a[i]=sc.nextInt();
        }
        int max=0;
        int curr1=0,curr2=0;
        int i=1;
        if(a[i]==1){
            while(i<=n&&a[i]==1){
                i++;
                curr1++;
            }
        }
        else{
            while(i<=n&&a[i]==2){
                i++;
                curr2++;
            }
        }
        while(i<=n){
            if(a[i]==1){
                while(i<=n&&a[i]==1){
                curr1++;
                i++;
                }
                max=Math.max(max,Math.min(curr1,curr2)*2);
                curr2=0;
            }
            else{
                while(i<=n&&a[i]==2){
                curr2++;
                i++;
                }
                max=Math.max(max,Math.min(curr1,curr2)*2);
                curr1=0;
            }
        }
        System.out.println(max);
    }
}