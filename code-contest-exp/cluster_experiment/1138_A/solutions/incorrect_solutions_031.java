import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;
public class Solution {
    public static void main(String[] args) throws IOException{
        Scanner s=new Scanner(System.in);
        int n=s.nextInt();
        int[] a=new int[n];
        for(int i=0;i<n;i++){
            a[i]=s.nextInt();
        }
        int c1=0;
        int c2=0;
        int len=0;
        if(a[0]==1) c1=1;
        else c2=1;
        for(int i=1;i<n;i++){
            if(a[i]==2 && a[i-1]==1){ 
                c2=1;
                continue;
            }
            if(a[i]==1 && a[i-1]==2){
                c1=1;
                continue;
            }
            if(a[i]==1) c1++;
            if(a[i]==2) c2++;
            if(c1==c2){
                len=Math.max(len,c1+c2);
            } 
        }
        int c=0;
        for(int i=0;i<n-1;i++){
            if(a[i]!=a[i+1]){
                c++;
            }
            if(c==n-1) len=2;
            
        }
        System.out.println(len);
    }
}