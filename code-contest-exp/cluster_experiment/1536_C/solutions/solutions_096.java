//package currentContest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.StringTokenizer;

public class P3 {
	public static int gcd(int a, int b) {
		if (a == 0)
			return b;

		return gcd(b % a, a);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int t = 1;
		t = sc.nextInt();
		StringBuilder st = new StringBuilder();
		while (t-- != 0) {
			int n=sc.nextInt();
			String s=sc.nextLine();
			int d=0,k=0;
			HashMap<String,Integer> map=new HashMap<>();
			for(int i=0;i<n;i++) {
				if(s.charAt(i)=='D')
					d++;
				else
					k++;
				int r1=d/(gcd(d,k));
				int r2=k/(gcd(d,k));
				String key=r1+" "+r2;
				int ans=1;
				if(map.containsKey(key)) {
					ans=map.get(key)+1;
					map.put(key, map.get(key)+1);
				}else {
					map.put(key,1);
				}
				st.append(ans+" ");
			}
			st.append("\n");
		}
		System.out.println(st);
	}

	static FastReader sc = new FastReader();

	public static void solvegraph() {
		int n = sc.nextInt();
		int edge[][] = new int[n - 1][2];
		for (int i = 0; i < n - 1; i++) {
			edge[i][0] = sc.nextInt() - 1;
			edge[i][1] = sc.nextInt() - 1;
		}
		ArrayList<ArrayList<Integer>> ad = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			ad.add(new ArrayList<Integer>());
		}
		for (int i = 0; i < n - 1; i++) {
			ad.get(edge[i][0]).add(edge[i][1]);
			ad.get(edge[i][1]).add(edge[i][0]);
		}
		int parent[] = new int[n];
		Arrays.fill(parent, -1);
		parent[0] = n;
		ArrayDeque<Integer> queue = new ArrayDeque<>();
		queue.add(0);
		int child[] = new int[n];
		Arrays.fill(child, 0);
		ArrayList<Integer> lv = new ArrayList<Integer>();
		while (!queue.isEmpty()) {
			int toget = queue.getFirst();
			queue.removeFirst();
			child[toget] = ad.get(toget).size() - 1;
			for (int i = 0; i < ad.get(toget).size(); i++) {
				if (parent[ad.get(toget).get(i)] == -1) {
					parent[ad.get(toget).get(i)] = toget;
					queue.addLast(ad.get(toget).get(i));
				}
			}
			lv.add(toget);
		}
		child[0]++;
	}

	static class FastReader {
		BufferedReader br;
		StringTokenizer st;

		public FastReader() {
			br = new BufferedReader(new InputStreamReader(System.in));
		}

		String next() {
			while (st == null || !st.hasMoreElements()) {
				try {
					st = new StringTokenizer(br.readLine());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return st.nextToken();
		}

		int nextInt() {
			return Integer.parseInt(next());
		}

		long nextLong() {
			return Long.parseLong(next());
		}

		double nextDouble() {
			return Double.parseDouble(next());
		}

		String nextLine() {
			String str = "";
			try {
				str = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return str;
		}
	}
}
