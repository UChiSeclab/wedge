import java.io.*;
import java.util.*;
public class sushi{
public static void main(String args[]){
Scanner sc=new Scanner(System.in);
int n=sc.nextInt();
int ar[]=new int[n];
for(int i=0;i<n;i++) ar[i]=sc.nextInt();
int c[]=new int[n];
int i=0,j=1,k=0;
Arrays.fill(c,1);
while(j<n){
if(ar[i]==ar[j]) {c[k]++;j++;}
else {i+=c[k];j=i+1;k++;}
}
int max=0,min=0;
for(int z=1;z<c.length;z++){
min=c[z-1]<c[z]?c[z-1]:c[z];
if(min>max) max=min;
}
System.out.println(2*max);
}}
