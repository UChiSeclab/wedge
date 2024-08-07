import java.util.*;
import java.io.*;

public class S {
	
	public static void main(String[] args) {
		FastReader in = new FastReader();
		PrintWriter out = new PrintWriter(System.out);
		int n = in.nextInt();
		int[] arr = new int[n];
		ArrayList<Integer> al = new ArrayList<>();
		int one = 0, two = 0;
		for(int i = 0; i<n; i++) {
			int a = in.nextInt();
			arr[i] = a;
			if(a==1 && two==0) {
				one++;
			}else if(a==2 && one==0){
				two++;
			}else if(a==2 && one!=0) {
				al.add(one);
				one = 0;
				two++;
			}else if(a==1 && two!=0) {
				al.add(two);
				two = 0;
				one++;
			}
			if(i==(n-1)) {
				if(one==0) {
					al.add(two);
				}else {
					al.add(one);
				}
			}
		}
		Collections.sort(al);
		if(al.size()>=3) out.print(2*al.get(al.size()-2));
		else out.print(2*al.get(0));
		
		
		
		out.close();
				
    }
	static int search(int[] time, int key, int low, int high) {
		while(low<=high) {
			int mid = (low+high)/2;
			if(time[mid]>key) high = mid-1;
			else if(time[mid]<key) low = mid+1;
			else return mid+1;
		}
		return low;
	}

	static class FastReader 
    { 
        BufferedReader br; 
        StringTokenizer st; 
  
        public FastReader() 
        { 
            br = new BufferedReader(new
                     InputStreamReader(System.in)); 
        } 
  
        String next() 
        { 
            while (st == null || !st.hasMoreElements()) 
            { 
                try
                { 
                    st = new StringTokenizer(br.readLine()); 
                } 
                catch (IOException  e) 
                { 
                    e.printStackTrace(); 
                } 
            } 
            return st.nextToken(); 
        } 
  
        int nextInt() 
        { 
            return Integer.parseInt(next()); 
        } 
  
        long nextLong() 
        { 
            return Long.parseLong(next()); 
        } 
  
        double nextDouble() 
        { 
            return Double.parseDouble(next()); 
        } 
  
        String nextLine() 
        { 
            String str = ""; 
            try
            { 
                str = br.readLine(); 
            } 
            catch (IOException e) 
            { 
                e.printStackTrace(); 
            } 
            return str; 
        } 
    } 
		
}