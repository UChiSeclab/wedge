import java.io.*;
import java.util.*;

public class Codeforces {
	public static class Count{
		int dcount;
		int kcount;
		public Count(int d, int k) {
			dcount = d;
			kcount = k;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + dcount;
			result = prime * result + kcount;
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Count other = (Count) obj;
			if (dcount != other.dcount)
				return false;
			if (kcount != other.kcount)
				return false;
			return true;
		}
		
	}
	public static void main(String[] args) throws IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int cases = Integer.parseInt(br.readLine());
		while(cases-- > 0) {
			int n = Integer.parseInt(br.readLine());
			String s = br.readLine();
			int dc = 0;
			int kc = 0;
			HashMap<Count, Integer> map = new HashMap<>();
			StringBuffer buf = new StringBuffer();
			for(int i=0; i<n; i++) {
				if(s.charAt(i) == 'D') {
					dc++;
				}else {
					kc++;
				}
				int d = dc, k = kc;
				if(d == 0) {
					k = 1;
				}else if(k == 0) {
					d = 1;
				}else {
					int gcd = gcd(d, k);
					d /= gcd;
					k /= gcd;
				}
				Count curr = new Count(d, k);
				if(!map.containsKey(curr)) {
					map.put(curr, 1);
				}else {
					map.put(curr, map.get(curr)+1);
				}
				buf.append(map.get(curr)).append(" ");
			}
			System.out.println(buf.toString());
		}
	}
	public static int gcd(int a, int b) {
		if(a > b) {
			int t = a;
			a = b;
			b = t;
		}
		if(a == 0) {
			return b;
		}else {
			return gcd(b%a, a);
		}
	}

}
