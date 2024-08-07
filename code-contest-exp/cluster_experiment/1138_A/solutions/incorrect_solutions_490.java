import java.util.*;
public class SushiForTwo {
	static Scanner sc = new Scanner(System.in);
	public static void main(String arg[]) {
		solve();
	}
	static void solve() {
		int n = sc.nextInt();
		int a[] = new int[n];
		int on=0, tw=0, O=0,T=0;
		for(int i=0; i<n; i++) {
			a[i] = sc.nextInt();
			if(a[i] == 2) {
				if(tw == 0) {
					T=i;
					tw++;
				}
				else {
					tw++;
				}
			}else {
				if(on==0) {
					O=i;
					on++;
				}else {
					on++;
				}
			}
		}
		if(n == 2) {
			System.out.println("2");
		}else {
			int z=0;
			List<Integer> l = new ArrayList<Integer>();
			if(tw <= on) {
				for(int i=T; i<n; i++) {
					if(a[i] == 2) {
						z++;
					}else {
						l.add(z);
						z=0;
					}
				}
			}
			else {
				for(int i=O; i<n; i++) {
					if(a[i] == 1) {
						z++;
					}else {
						l.add(z);
						z=0;
					}
				}
			}
			Collections.sort(l);
			//System.out.println(l);
			System.out.println(2*l.get(l.size()-1));
		}
	}
}
