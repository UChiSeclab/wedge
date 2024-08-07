import java.util.*;

public class SushiforTwo {
	
	public static void main(String args[]) {
		Scanner in = new Scanner(System.in);
		
		int n = in.nextInt();
		int[] ar = new int[n];
		int i;
		for(i=0;i<n;i++)
			ar[i]=in.nextInt();
		
		Vector<Integer> v = new Vector<Integer>();
		v.insertElementAt(0, 0);
		
		for(i=1;i<n;i++) {
			if(ar[i]!=ar[i-1]) {
				v.add(i);
			}
		}
		
		v.add(n);
		int dif = -1;
		int le = v.size();
		for(i=1;i<le-1;i++) {
			int a = v.get(i);
			int b = v.get(i-1);
			int c = v.get(i+1);
			int dif1 = Math.abs(a-b);
			int dif2 = Math.abs(a-c);
			dif=Math.max(dif, Math.min(dif1, dif2));
		}
		
		System.out.println(2*dif);
		
		
		
		
		
		
	}
	
}
