import java.util.*;
import java.io.*;
 
public class Main{
	static class Point{
		int x,y; 
		Point(int nx, int ny){
			x = nx; y = ny;
		}
	}
	public static void main(String[] args) throws IOException{
    	In in = new In();
    	for(int q = in.pint(); q > 0; q--) {
    		int n = in.pint();
    		int[] a = new int[n];
    		int[] ct = new int[n];
    		int[] ls = new int[n];
    		int[] ft = new int[n];
    		int[] id = new int[n];
    		Stack<Point> stack = new Stack<Main.Point>();
    		HashSet<Integer> set = new HashSet<Integer>();
    		stack.add(new Point(-1,-1));
    		char[] ans = new char[n];
    		in.tok();
    		for(int i = 0; i < n; i++) {
    			a[i] = in.pint() - 1;
    			ct[a[i]]++;
    			while(a[i] <= stack.peek().x) {stack.pop();}
    			ls[i] = stack.peek().y;
    			stack.push(new Point(a[i], i));
    			set.add(a[i]);
    		}
    		stack.clear();
    		stack.add(new Point(-1, n));
    		for(int i = n - 1; i >= 0; i--) {
    			while(a[i] <= stack.peek().x) {stack.pop();}
    			ft[i] = stack.peek().y;
    			stack.push(new Point(a[i], i));
    		}
    		int[] sz = new int[n];
    		for(int i = 0; i < n; i++) {
    			int diff = ft[i] - ls[i] + 1;
    			sz[a[i]] = diff - 2;
    		}
    		int min = n;
    		boolean f = false;
    		for(int k = n; k >= 1; k--) {
    			min = Math.min(sz[n - k], min);
    			if(k > min) {
    				ans[k - 1] = '0';
    			}else {
    				ans[k - 1] = '1';
    			}
    		}
    		for(int k = n; k > 0; k--) {
    			int s = n - k;
    			if(ct[s] == 0) {
    				f = true;
    			}
    			if(s > 0 && ct[s - 1] > 1) {
    				f = true;
    			}
    			if(f) {ans[k - 1] = '0';}
    		}
    		ans[0] = set.size() == n ? '1' : '0';
    		System.out.println(new String(ans));
    	}
    }
	static boolean check(int[] a, int k) {
		int n = a.length;
		HashSet<Integer> set = new HashSet<Integer>();
		int max = 0;
		for(int i = 0; i <= n - k; i++) {
			int min = Integer.MAX_VALUE;
			for(int j = i; j < i + k; j++) {
				min = Math.min(min, a[j]);
			}
			max = Math.max(max, min);
			set.add(min);
		}
		return set.size() == n - k  + 1 && max == n - k;
	}
}	
 
 
class In{
    BufferedReader in;
    StringTokenizer st = new StringTokenizer("");
    public In(){
        in = new BufferedReader(new InputStreamReader(System.in));
    }
    
    int pint() throws IOException{
        if(st.hasMoreTokens()) {return Integer.parseInt(st.nextToken());}
        else {return Integer.parseInt(in.readLine());}
    }
    double pdbl() throws IOException{
        if(st.hasMoreTokens()) {return Double.parseDouble(st.nextToken());}
        else {return Double.parseDouble(in.readLine());}
    }
    long plng() throws IOException{
        if(st.hasMoreTokens()) {return Long.parseLong(st.nextToken());}
        else {return Long.parseLong(in.readLine());}
    }
    char pchr() throws IOException{
        if(st.hasMoreTokens()) {return st.nextToken().charAt(0);}
        else {return in.readLine().charAt(0);}
    }
    String pstr() throws IOException{
        if(st.hasMoreTokens()) {return st.nextToken();}
        else {return in.readLine();}
    }
    String readLine() throws IOException{
    	return in.readLine();
    }
    boolean ready() throws IOException {return in.ready();}
    boolean readyN() throws IOException{return in.ready() || st.hasMoreTokens();}
    void tok() throws IOException{st = new StringTokenizer(in.readLine());}
    void skip() throws IOException{in.readLine();}
}