import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Stack;

import javax.swing.text.html.HTMLDocument.Iterator;

public class CF {
	
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		int t = sc.nextInt();
		while(t-- > 0) {
			int n = sc.nextInt();
			int[] a = new int[n];
			for(int i = 0; i < n; i++) a[i] = sc.nextInt();
			int count = 0;
			StringBuilder ret = new StringBuilder();
			while(count < n) {
				int[] temp = new int[a.length-count];
				temp = find(temp, count+1, a);
				count++;
				
				ret.append(find1(temp));
			}
			System.out.println(ret);
		}
	}
	
	static int[] find(int[] temp, int k, int[] a) {
		Deque<Integer> b = new LinkedList<>();
		int j = 0;
		for(int i = 0; i < a.length; i++) {
			while(!b.isEmpty() && a[b.peekLast()] <= a[i]) {
				b.pollLast();
			}
			b.addLast(i);
			if(i-j == k-1) {
				temp[j++] = a[b.peekFirst()];
				while(!b.isEmpty() && b.peekFirst() <= i-k+1) b.pollFirst();
			}
		}
		return temp;
	}
	
	static int find1(int[] temp) {
		HashSet<Integer> a = new HashSet<>();
		for(int aa:temp) {
			if(a.contains(aa)) return 0;
			a.add(aa);
		}
		return 1;
	}
	

}