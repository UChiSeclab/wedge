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
        for(int i=1;i<=n;i++){
            int tmp=a[i];
            int curr1=0,curr2=0;
            if(tmp==1)
                curr1=1;
            else
                curr2=1;
            while(i<=n&&a[i]==tmp)
            {
                if(tmp==1){
                curr1++;
                i++;
                }
                else{
                    curr2++;
                    i++;
                } 
            }
            if(i<=n)
            tmp=a[i];
            while(i<=n&&a[i]==tmp)
            {
                if(tmp==2){
                curr2++;
                i++;
                }
                else{
                    curr1++;
                    i++;
                }
            }
            max=Math.max(max,Math.min(curr1,curr2)*2);
        }
        System.out.println(max);
    }
}