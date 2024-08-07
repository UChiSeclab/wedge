import java.util.*;
import java.lang.*;
import java.io.*;

public class Main
{
	public static void main (String[] args) throws java.lang.Exception
	{
		FastScanner scan=new FastScanner();
// 		int t=scan.nextInt();
        int t=1;
        BufferedWriter w = new BufferedWriter(new OutputStreamWriter(System.out));
		outer:while(t-->0){
		        int n=scan.nextInt();
		        long []arr=new long[n];
		        for(int i=0;i<n;i++){
		            arr[i]=scan.nextLong();
		        }
		        sort(arr);
		        HashSet<Long> set=new HashSet<>();
		        set.add(0L);
		        for(int i=n-1;i>=0;i--){
		            if(!set.contains(arr[i]+1)){
		                set.add(arr[i]+1);
		            }else if(!set.contains(arr[i])){
		                set.add(arr[i]);
		            }else if(!set.contains(arr[i]-1)){
		                set.add(arr[i]-1);
		            }
		        }
		        
		        w.write((set.size()-1)+"\n");
		    
		}
		w.close();
		
	}
	
	public static void sort(long[] a) {
		ArrayList<Long> l = new ArrayList<>();
		for (long i : a) {
			l.add(i);
		}
		Collections.sort(l);
		for (int i = 0; i < a.length; i++) {
			a[i] = l.get(i);
		}
	}

}
class pair{
    int f;
    int s;
    pair(){
        
    }
    
    pair(int f,int s){
        this.f=f;
        this.s=s;
    }
}
class FastScanner { 
        BufferedReader br; 
        StringTokenizer st;
        public FastScanner() { 
            br = new BufferedReader(new InputStreamReader(System.in)); 
        } 
        String next() { 
            while (st==null || !st.hasMoreElements()) { 
                try { 
                    st = new StringTokenizer(br.readLine()); 
                } 
                catch (IOException  e) { 
                    e.printStackTrace(); 
                } 
            } 
            return st.nextToken(); 
        } 
        int nextInt() { 
            return Integer.parseInt(next()); 
        } 
        long nextLong() { 
            return Long.parseLong(next()); 
        } 
        double nextDouble() { 
            return Double.parseDouble(next()); 
        } 
        String nextLine() { 
            String s=""; 
            try { 
                s=br.readLine(); 
            } 
            catch (IOException e) { 
                e.printStackTrace(); 
            } 
            return s; 
        } 

int[] readArray(int n) {
			int[] a=new int[n];
			for (int i=0; i<n; i++) a[i]=nextInt();
			return a;
		}
    } 
    
