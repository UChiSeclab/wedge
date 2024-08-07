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
        int ch=a[0];
        for(int i=1;i<n;i++){
            if(a[i]!=ch){ 
                c2=c1;
                c1=0;
                ch=a[i];
            }
            c1++;
            len=Math.min(c1,c2);
        }
        
        System.out.println(len*2);
    }
}