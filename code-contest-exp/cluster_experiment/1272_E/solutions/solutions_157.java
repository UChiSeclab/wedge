//package cf;
import java.io.*;
import java.util.*;
import java.lang.*;
import java.math.*;
public class USACO {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter (System.out);
        //BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("input.txt")));
        //StringTokenizer st = new StringTokenizer(reader.readLine()," ");
        int n = Integer.parseInt(reader.readLine());
        StringTokenizer st = new StringTokenizer(reader.readLine()," ");
        int[] a = new int[n];
        for (int i=0;i<n;i++) a[i]=Integer.parseInt(st.nextToken());
        HashMap<Integer,List<Integer>> reverse = new HashMap<>();
        for (int i=0;i<n;i++) reverse.put(i,new ArrayList<>());
        int[] dist = new int[n];
        LinkedList<Integer> newDist = new LinkedList<Integer>();
        for (int i=0;i<n;i++) {
        	if (i-a[i]>=0) {
        		reverse.get(i-a[i]).add(i);
        		if (((a[i]-a[i-a[i]])&1)!=0) {
        			newDist.add(i);
        			dist[i]=1;
        		}
        	}
        	if (i+a[i]<n) {
        		reverse.get(i+a[i]).add(i);
        		if (((a[i]-a[i+a[i]])&1)!=0) {
        			newDist.add(i);
        			dist[i]=1;
        		}
        	}
        }
        int count=2;
        int dead=0;
        newDist.add(-1);
        while (!newDist.isEmpty()&&dead<2){
        	int i=newDist.getFirst();
        	if (i==-1) {
        		count++;
        		newDist.add(-1);
        		dead++;
        	} else {
        		dead=0;
	    		for (int j : reverse.get(i)) {
	    			if (((a[j]-a[i]) & 1)==0) {
	    				if (dist[j]==0) {
	    					dist[j]=dist[i]+1;
		    				newDist.add(j);
	    				}
	    			}
	    		}
        	}
        	newDist.remove(0);
        }
        for (int i=0;i<n;i++) {
        	if (dist[i]==0) dist[i]--;
        }
        for (int i=0;i<n;i++) {
        	out.print(dist[i]);
        	if (i<n-1) out.print(" ");
        	else out.println();
        }
        reader.close();
        out.close();
    }
}