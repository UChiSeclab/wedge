import java.util.*;

public class Main {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		int n = sc.nextInt();
		int k = sc.nextInt();
		
		if (k > 0) {
			
			long x = 0;
			HashMap<Integer, Long> m = new HashMap<Integer, Long>();
		
			for (int i=0; i<n; i++) {
				int w = sc.nextInt();
				if (m.containsKey(w)) {
					m.put(w, m.get(w)+1);
				} else {
					m.put(w, (long) 1);
				}
			}
			
			for (int i=0; i<16384; i++) {
				if (Integer.bitCount(i) == k) {
					for (Map.Entry<Integer, Long> me : m.entrySet()) {
						int t = i ^ me.getKey();
						if (m.containsKey(t)) {
							x += m.get(t) * me.getValue();
						}
					}
				}
			}
			
			System.out.println(x/2);
		
		} else {
			
			long x = 0;
			HashMap<Integer, Long> m = new HashMap<Integer, Long>();
		
			for (int i=0; i<n; i++) {
				int w = sc.nextInt();
				if (m.containsKey(w)) {
					long wn = m.get(w);
					x += wn;
					m.put(w, wn+1);
				} else {
					m.put(w, (long) 1);
				}
			}
			
			System.out.println(x);
			
		}
		

	}

}