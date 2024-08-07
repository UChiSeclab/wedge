import java.util.Scanner;
import java.util.Stack;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Comparator;
public class F{
	// static final int max = 10000; 
	static class Pair{
		int x;
		int speed;
		public String toString(){
			return "[" + x + ", " + speed + "]";
		}
	}
	static class FenvikTree{
		int[] arr;
		int[] T;
		int size;
		FenvikTree(int n){
			size = n;
			arr = new int[n];
			T = new int[n];
		}
		public void add(int pos, int a){
			arr[pos] += a;
			while(pos < size){
				T[pos] += a;
				pos = pos | (pos+1);
			}
		}
		public long sum(int pos){
			if(pos < 0) return 0;
			if(pos == 0) return T[0];
			if(pos == 1) return T[1];
			// System.out.println(pos + " " + ((pos&(pos+1))-1));
			return T[pos] + sum((pos&(pos+1))-1);
		}
	}
	public static void main(String[] args){
			Scanner in = new Scanner(System.in);
			int n = in.nextInt();
			Pair[] a = new Pair[n];
			// long[] speed = new long[n];
			HashMap<Integer,Integer> map= new HashMap<>();
			for(int i = 0;i<n;++i){
				a[i] = new Pair();
				a[i].x = in.nextInt();
			}
			// int[] b=  new int[n];
			for(int i = 0;i<n;++i){
				a[i].speed = in.nextInt();
			//	b[i] = a[i].speed;
			}
			Comparator<Pair> comp1 = new Comparator<Pair>(){
				public int compare(Pair a, Pair b){
					return Integer.compare(a.speed,b.speed);
				}
			};
			
			Arrays.sort(a,comp1);
			// System.out.println(Arrays.toString(b));
			int count = 0;
			for(int i = 0;i<n;++i){
				if(map.get(a[i].speed) == null){
					map.put(a[i].speed,count++);
				}
				a[i].speed = map.get(a[i].speed);
			}
			// }
			Comparator<Pair> comp2 = new Comparator<Pair>(){
				public int compare(Pair a, Pair b){
					return (a.x == b.x) ? (Integer.compare(b.speed,a.speed)) : Integer.compare(a.x,b.x);
				}
			};
			Arrays.sort(a,comp2);
			// System.out.println(Arrays.toString(a));
			
			FenvikTree fenvikCount = new FenvikTree(n);
			FenvikTree fenvikSum = new FenvikTree(n);
			fenvikCount.add(a[0].speed,1);
			fenvikSum.add(a[0].speed,a[0].x);
			long res = 0;
			for(int i = 1;i<n;++i){
				int cur = a[i].speed;
				res += fenvikCount.sum(cur)*a[i].x - fenvikSum.sum(cur);
				fenvikCount.add(cur,1);
				fenvikSum.add(cur,a[i].x);
			}
			System.out.println(res);
						
	}
}