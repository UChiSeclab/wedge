import java.io.*;
import java.util.*;


import java.lang.*;

public final class Main {
    static final long lim_m = (long) (double) 1e17;
    static boolean check(int m, long x[], long a[], long b[], int n) {
    			for(int i = 0; i < n; i++){
          		if ( !( (i-m) >= 0 && (i+m) < n) ){
              	continue;
              }
              if(a[i] - a[i-m] == m && b[i+m] - b[i] == m){
              		return true;
              }
              if(b[i] - b[i-m] == m && a[i+m] - a[i] == m){
              		return true;
              }

          }
          return false;
    }
    static long run() {
        Scanner in = new Scanner(System.in);
        int n;
        n = in.nextInt();
        long x[] = new long[(int) n];
        long a[] = new long[(int) n];
        long b[] = new long[(int) n];
				
        for (int i = 0; i < n; i++) {
            x[i] = in.nextInt();
            a[i] = (x[i] == 1) ? 1 : 0;
            b[i] = (x[i] == 2) ? 1 : 0;
            
            if(i == 0) continue;
            
						a[i] += a[i-1];
            b[i] += b[i-1];
        }
        if (a[n - 1] == n || b[n - 1] == n) {
            return 0;
        }
        
        int l = 1;
        int r = n;
        int mid = (1+n)/2;
        while(l+1 < r){
            // System.out.println(l + " " + mid + " " + r);
            mid = (l + r) / 2;
        		if(check(mid, x, a, b, n) == true){
            		l = mid;
            } else {
            		r = mid;
            }
        }
        
        return l*2;
        
    }
    public static void main(String[] args) {
        System.out.println(run());
    }

}