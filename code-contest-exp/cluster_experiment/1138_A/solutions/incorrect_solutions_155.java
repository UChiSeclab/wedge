/*package whatever //do not write package name here */

import java.util.*;
import java.lang.*;
import java.io.*;

public class Solution {
    public static void main (String[] args) {
        //code
        Scanner obj=new Scanner(System.in);
        int n=obj.nextInt();
        int []arr=new int[n];
        for(int i=0;i<n;i++){
            arr[i]=obj.nextInt();
        }
        int maxLength=0;

        int len1=0,len2=0;
        int last=arr[0];
        if(last==1){
            len1++;
        }else{
            len2++;
        }
        for(int i=1;i<n;i++){
            if(arr[i]==last){
                if(arr[i]==1){
                    len1++;
                }else{
                    len2++;
                }
                //System.out.println(len1+" "+len2+"####");
                maxLength=Math.max(maxLength,Math.min(len1,len2));
            }else{
                //System.out.println(len1+" "+len2);
                maxLength=Math.max(maxLength,Math.min(len1,len2));
                if(arr[i]==1){
                    len1=1;
                    last=1;
                }else{
                    len2=1;
                    last=2;
                }
            }
        }
        System.out.println(maxLength);
    }
}
