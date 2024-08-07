import java.util.*;
import java.io.*;
public class Solution {
    static Scanner in =new Scanner(System.in);
    static final Random random=new Random();
    static int mod=1000_000_007;
    public static void main(String[] args) {
     //int tt=in.nextInt();in.nextLine();
    // outer: while(tt-->0) {
         int n=in.nextInt();int a[]=readAr(n);
         Set<Integer>set=new HashSet<>();
         ruffleSort(a);
        for(int x:a){
            if(set.contains(x)){
                if(set.contains(x-1))set.add(x+1);
                else {
                    if(x-1<=0)set.add(x+1);
                    else set.add(x-1);
                }
            }
            else {
                if(!set.contains(x-1) && (x-1)!=0)set.add(x-1);
                else set.add(x);
            }
        }
        //for(int x:set)System.out.print(x+" ");
         System.out.println(set.size());
      // }
    }
   
    static int nCr(int n,int r,int p){
        if (r == 0) return 1; 
        if(r==1)return n;
        return ((int)fact(n)*modInverse((int)fact(r),p)%p * modInverse((int)fact(n-r),p) % p)%p; 
    }
    static int modInverse(int n, int p) { 
        return power(n, p - 2, p); 
    } 
    static int power(int x,int y,int p) { 
        int res = 1;
        x= x % p;
        while (y>0) {
            if (y%2==1) 
                res= (res *x)% p;
            y= y >> 1;
            x= (x * x)% p; 
        }
        return res; 
    } 
    static int find(int p){
        int m=0;int l=0,r=p;
        while(l<=r){
            int mid=l+(r-l)/2;
            if(mid!=r)m++;
            if(mid==r)break;
            if(mid<r)l=mid+1;
        }return m;
    }
    static int[]readAr(int n) {
        int[] a=new int[n];
        for (int i=0; i<n; i++) {a[i]=in.nextInt();}
        return a;
	}
    static int fact(int k){
        long[]f=new long[10001];f[1]=1;
        for(int i=2;i<10001;i++){
            f[i]=(i*f[i-1]% mod);
        }
        return (int)f[k];
    }
	static void ruffleSort(int[] a) {
		int n=a.length;
		for (int i=0; i<n; i++) {
			int oi=random.nextInt(n), temp=a[oi];
			a[oi]=a[i]; a[i]=temp;
		}
		Arrays.sort(a);
	}
	static void reverse(int []a){
        int n= a.length;
        for(int i=0;i<n/2;i++){
            int temp=a[i];
            a[i]=a[n-i-1];
            a[n-i-1]=temp;
            a[i]=a[i];
        }
        //debug(a);
	}
	static void debug(int []a) {
        for (int i=0; i<a.length;i++) System.out.print(a[i]+" ");
        //System.out.println();
	}
	static void print(List<Integer>s) {
        for(int x:s) System.out.print(x+",");
        System.out.println();
	}
	static long gcd(long a, long b) {
        return b==0 ? a : gcd(b, a % b);
	}
    static int find_max(int []a){
        int m=Integer.MIN_VALUE;
        for(int x:a)m=Math.max(x,m);
        return m;
    }
    static int find_min(int []a){
        int m=Integer.MAX_VALUE;
        for(int x:a)m=Math.min(x,m);
        return m;
    }
}