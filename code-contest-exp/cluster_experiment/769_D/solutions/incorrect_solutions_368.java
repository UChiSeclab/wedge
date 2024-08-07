import java.util.*;

public class Main {
	
	public static int count(int x) {
		x = x - ((x>>1)& 0x55555555);
		x = (x & 0x33333333) + ((x>>2)& 0x33333333);
		x = (x+(x>>4))& 0x0F0F0F0F;
		x = x+(x>>8);
		x = x+(x>>16);
		x = x & 0x0000003F;
		return x;
	}

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		int n = sc.nextInt();
		int k = sc.nextInt();
		
		if (k > 0) {
			
			int x = 0;
			HashMap<Integer, Integer> m = new HashMap<Integer, Integer>();
		
			for (int i=0; i<n; i++) {
				int w = sc.nextInt();
				if (m.containsKey(w)) {
					m.put(w, m.get(w)+1);
				} else {
					m.put(w, 1);
				}
			}
			
			for (int i=0; i<16384; i++) {
				if (count(i) == k) {
					for (Map.Entry<Integer, Integer> me : m.entrySet()) {
						int t = i ^ me.getKey();
						if (m.containsKey(t)) {
							x += m.get(t) * me.getValue();
						}
					}
				}
			}
			
			System.out.println(x/2);
		
		} else {
			
			int x = 0;
			HashMap<Integer, Integer> m = new HashMap<Integer, Integer>();
		
			for (int i=0; i<n; i++) {
				int w = sc.nextInt();
				if (m.containsKey(w)) {
					int wn = m.get(w);
					x += wn;
					m.put(w, wn+1);
				} else {
					m.put(w, 1);
				}
			}
			
			System.out.println(x);
			
		}
		

	}

}