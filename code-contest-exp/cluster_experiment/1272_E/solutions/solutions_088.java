

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class E {

	private static Scanner in;
	private static int n;
	private static int[] a;
	
	public static void main(String[] args) {
		in = new Scanner(System.in);
		n = in.nextInt();
		a = new int[n];
		for(int i=0; i<n; i++) a[i] = in.nextInt();
		
		boolean[] even = new boolean[n];  //target even
		for(int i=0; i<n; i++) {
			if (a[i]%2==0) continue;
			if (i-a[i]>=0 && a[i-a[i]]%2==0) {
				even[i] = true;
			} else if (i+a[i]<n && a[i+a[i]]%2==0) {
				even[i] = true;
			}
		}
		Queue<Integer> queue = new LinkedList<>();
		boolean[] mark = new boolean[n];
		for(int i=0; i<n; i++) {
			if (even[i]) {
				queue.add(i);
				mark[i] = true;
			}
		}
		
		HashMap<Integer, ArrayList<Integer>> gr = new HashMap<>();
		for(int i=0; i<n; i++) {
			gr.put(i, new ArrayList<>());
		}
		
		for(int i=0; i<n; i++) {
			if (i+a[i]<n) {
				gr.get(i+a[i]).add(i);
			}
			if (i-a[i]>=0) {
				gr.get(i-a[i]).add(i);
			}
		}
		
		int[] evenDist = new int[n];
		
		while(!queue.isEmpty()) {
			int u = queue.poll();
			
			for(int v: gr.get(u)) {
				if (mark[v]) continue;
				
				mark[v] = true;
				queue.add(v);
				evenDist[v] = evenDist[u]+1;
			}
			
		}
		
		boolean[] odd = new boolean[n];  //target even
		for(int i=0; i<n; i++) {
			if (a[i]%2!=0) continue;
			if (i-a[i]>=0 && a[i-a[i]]%2!=0) {
				odd[i] = true;
			} else if (i+a[i]<n && a[i+a[i]]%2!=0) {
				odd[i] = true;
			}
		}
		queue = new LinkedList<>();
		boolean[] mark2 = new boolean[n];
		for(int i=0; i<n; i++) {
			if (odd[i]) {
				queue.add(i);
				mark2[i] = true;
			}
		}
		
		int[] oddDist = new int[n];
		
		while(!queue.isEmpty()) {
			int u = queue.poll();
			
			for(int v: gr.get(u)) {
				if (mark2[v]) continue;
				
				mark2[v] = true;
				queue.add(v);
				oddDist[v] = oddDist[u]+1;
			}
			
		}
		
		for(int i=0; i<n; i++) {
			if (a[i]%2==0) {
				if (mark2[i]) {
					System.out.print((oddDist[i]+1)+" ");
				} else {
					System.out.print(-1+" ");
				}
			} else {
				if (mark[i]) {
					System.out.print((evenDist[i]+1)+" ");
				} else {
					System.out.print(-1+" ");
				}
			}
		}
	}

}
