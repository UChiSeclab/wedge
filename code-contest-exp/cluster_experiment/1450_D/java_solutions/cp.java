import java.util.Arrays;
import java.util.Scanner;
public class cp
{
public int min(int ar[]){
int min=ar[0];
for (int i=0;i<ar.length;i++){
if (ar[i]<=min){
min= ar[i];
}
}
return min;
}
public String permutation(int ar[],int m){
boolean flag = true;
if(m==ar.length){
Arrays.sort(ar);
for (int i=0;i<m;i++){
if (i+1 != ar[i]){flag = false;break;}
}}
else flag = false;
if (flag == true) return "1";
else return "0";
}
public static void main(String[] args){
cp ob = new cp();
Scanner sc= new Scanner(System.in);
int n = sc.nextInt();
int a[]= new int[n];
for (int i=0;i<n;i++){a[i]=sc.nextInt();}
String r="";
for (int i=1;i<=n;i++){
r += ob.permutation(ob.compression(a,i,n),(n-i+1));}
System.out.println (r);
}
public int[] compression(int a[],int k,int n){
int b[]= new int[n-k+1];
int s[]= new int[k];
int ch=0;
for (int i=0;i<(n-k+1);i++){
ch=0;
for (int j=i;j<(i+k);j++){
s[ch]=a[j];
ch++;
}
b[i]= min(s);
}
return b;
}
}