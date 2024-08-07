//package CodeForces;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class A1134 {

	public static void main(String[] args) throws NumberFormatException, IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.valueOf(br.readLine());
		StringTokenizer st = new StringTokenizer(br.readLine());
		int[] a = new int[n];
		for(int i=0;i<n;i++) {
			a[i] = Integer.valueOf(st.nextToken());
		}
		int[] l1 = new int[n];
		int[] l2 = new int[n];
		int[] r1 = new int[n];
		int[] r2 = new int[n];
		for(int i=0;i<n;i++) {
			if(a[i]==1) {
				if(i==0) {
					l1[i] = 1;
				}
				else {
					l1[i] = l1[i-1]+1;
				}
				l2[i] = 0;
			}
			else {
				if(i==0) {
					l2[i] = 1;
				}
				else {
					l2[i] = l2[i-1]+1;
				}
				l1[i] = 0;
			}
		}
		for(int i=n-1;i>=0;i--) {
			if(a[i]==1) {
				if(i==n-1) {
					r1[i] = 1;
				}
				else {
					r1[i] = r1[i+1]+1;
				}
				r2[i] = 0;
			}
			else {
				if(i==n-1) {
					r2[i] = 1;
				}
				else {
					r2[i] = r2[i+1]+1;
				}
				r1[i] = 0;
			}
		}
		int ans = 0;
		for(int i=0;i<n-1;i++) {
			ans = Math.max(ans,Math.max(Math.min(l1[i], r2[i+1]),Math.min(l2[i], r1[i+1]) ));
		}
		ans = ans*2;
		System.out.println(ans);
	}

}
