import java.util.*;

public class Solution {
	
	public static void main(String[] args) {
		
		Scanner in=new Scanner(System.in);
		
		int n=in.nextInt();

		int p[][]=new int[n][2];
		
		for (int i=0; i<n; i++)
			p[i][0]=in.nextInt();

		HashSet<Integer> set=new HashSet<>();
		for(int i=0; i<n; i++) {
			p[i][1]=in.nextInt();
			set.add(p[i][1]);
		}

		ArrayList<Integer> list=new ArrayList<>(set);
		Collections.sort(list);
		HashMap<Integer, Integer> map=new HashMap<>();
		for(int i=0; i<list.size(); i++)
			map.put(list.get(i), i);

		for(int i=0; i<n; i++)
			p[i][1]=map.get(p[i][1]);

		Arrays.sort(p, (p1, p2)->{
			return p1[0]-p2[0];
		});

		System.out.println(solve(p, n));
	}

	public static void set(long arr[], int index, int value) {
		for(; index<arr.length; index=index|(index+1))
			arr[index]+=value;
	}

	public static long get(long arr[], int index) {
		long sum=0L;
		for(; index>=0; index=(index&(index+1))-1)
			sum+=arr[index];

		return sum;
	}
	
	public static long solve(int p[][], int n) {
		long bitx[]=new long[n];
		long bity[]=new long[n];

		long ans=0L;
		for(int i=0; i<n; i++) {
			ans+=get(bitx, p[i][1])*p[i][0]-get(bity, p[i][1]);
			set(bitx, p[i][1], 1);
			set(bity, p[i][1], p[i][0]);
			// System.out.println(Arrays.toString(p[i])+" "+Arrays.toString(bitx)+" "+Arrays.toString(bity)+" "+ans);
		}

		return ans;
	}
}



























