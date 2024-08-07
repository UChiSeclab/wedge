/*
 ID: tommatt1
 LANG: JAVA
 TASK: 
*/
import java.util.*;
import java.io.*;
public class cf1379d{

	public static void main(String[] args)throws IOException {
		PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		BufferedReader bf=new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st=new StringTokenizer(bf.readLine());
		int n=Integer.parseInt(st.nextToken());
		int h=Integer.parseInt(st.nextToken());
		int m=Integer.parseInt(st.nextToken());
		int k=Integer.parseInt(st.nextToken());
		int[] init=new int[n];
		int[] id=new int[n];
		for(int i=0;i<n;i++) {
			st=new StringTokenizer(bf.readLine());
			int no=Integer.parseInt(st.nextToken());
			int x=Integer.parseInt(st.nextToken());
			init[i]=id[i]=x%(m/2);
		}
		Arrays.sort(init);
		int[] a=new int[2*n];
		for(int i=0;i<n;i++) {
			a[i]=init[i];
			a[i+n]=init[i]+(m/2);
		}
		int lo=0;
		int max=0;
		int idx=0;
		for(int i=0;i<2*n;i++) {
			while(a[i]-a[lo]>(m/2)-k) {
				lo++;
			}
			if(i-lo+1>max) {
				max=i-lo+1;
				idx=a[i];
			}
		}
		idx%=(m/2);
		int start=(idx+k)%(m/2);
		out.println(n-max+" "+start);
		for(int i=0;i<n;i++) {
			if(id[i]>idx&&id[i]<idx+k) {
				out.print(i+1+" ");
			}
			else if(id[i]<idx&&id[i]>idx+k-(m/2)) {
				out.println(i+1+" ");
			}
		}
		out.println();
		out.close();
	}
	static void sort(int[] a) {
		ArrayList<Integer> l=new ArrayList<>();
		for (int i:a) l.add(i);
		Collections.sort(l);
		for (int i=0; i<a.length; i++) a[i]=l.get(i);
	}

}
