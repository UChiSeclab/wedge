import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.TreeSet;

public class TaskE {
	public static void main(String[] args) throws IOException {
		Scanner s = new Scanner(System.in);
		int n = s.nextInt();
		
		Pos[] arr = new Pos[n];
		for(int i=0;i<n;i++) {
			arr[i] = new Pos();
		}
		
		int[] dis = new int[n];
		Arrays.fill(dis, Integer.MAX_VALUE);
		
		for(int i=0;i<n;i++) {
			int c = s.nextInt();
			arr[i].num = c;
			arr[i].index = i;
			if(i-c >= 0) {
				arr[i-c].reachedBy.add(arr[i]);
			}
			if(i+c < n) {
				arr[i+c].reachedBy.add(arr[i]);
			}
		}
		
		LinkedList<Pos> queue = new LinkedList<Pos>();
		TreeSet<Integer> done = new TreeSet<Integer>();
		
		for(int i=0;i<n;i++) {
			int c = arr[i].num;
			if(i-c >= 0 && arr[i-c].num%2 != c%2) {
				dis[i] = 1;
			}
			if(i+c < n && arr[i+c].num%2 != c%2) {
				dis[i] = 1;
			}
			
			if(dis[i] == 1) {
				done.add(i);
				fill(done,queue,arr[i].reachedBy);
			}
		}
		
		while(!queue.isEmpty()) {
			Pos curr = queue.poll();
			int i = curr.index;
			int c = curr.num;
			int min = dis[i];
			
			if(i-c >= 0) {
				min = Math.min(min, dis[i-c] + 1);
			}
			if(i+c < n) {
				min = Math.min(min, dis[i+c] + 1);
			}
			
			if(min != Integer.MAX_VALUE) {
				done.add(i);
				fill(done,queue,arr[i].reachedBy);
			}
			
			dis[i] = min;
		}
		
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		for(int i=0;i<n;i++) {
			if(dis[i] == Integer.MAX_VALUE) {
				dis[i] = -1;
			}
			bw.write(dis[i] + " ");
		}
		bw.flush();
	}
	
	public static void fill(TreeSet<Integer> done, LinkedList<Pos> q, ArrayList<Pos> e) {
		for(int i=0;i<e.size();i++) {
			int c = e.get(i).index;
			if(!done.contains(c)) {
				q.add(e.get(i));
			}
		}
	}
}

class Pos{
	int index;
	int num;
	public ArrayList<Pos> reachedBy;
	
	public Pos() {
		reachedBy = new ArrayList<Pos>();
	}
}
