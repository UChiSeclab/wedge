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
    		char[] ans = new char[n];
    		in.tok();
    		for(int i = 0; i < n; i++) {
    			a[i] = in.pint() - 1;
    			ct[a[i]]++;
    		}
    		boolean f = false;
    		HashSet<Integer> rm = new HashSet<Integer>();
    		int l = 0, r = n - 1;
    		for(int k = n; k > 0; k--) {
    			int s = n - k;
    			if(ct[s] == 0) {
    				f = true;
    			}
    			if(s > 0 && ct[s - 1] > 1) {
    				f = true;
    			}
    			if(f) {
    				ans[k - 1] = '0';
    				continue;
    			}
    			while(l < n && rm.contains(a[l])) {
    				rm.remove(a[l]);
    				l++;
    			}
    			while(r >= 0 && rm.contains(a[r])) {
    				rm.remove(a[r]);
    				r--;
    			}
    			if(rm.size() == 0) {
    				ans[k - 1] = '1';
    			}else {
    				ans[k - 1] = '0';
    			}
    			rm.add(s);
    		}
    		System.out.println(new String(ans));
    		
    	}
    }
	static boolean check(char[][] c, int i, int j) {
		return checkh(c, i, j) || checkv(c,i,j);
	}
	static boolean checkv(char[][] c, int i, int j) {
		boolean r = false;
		r |= (i > 1 && c[i][j] != '.' && c[i - 1][j] == c[i][j] && c[i - 2][j]  == c[i][j]);
		r |= (i + 2 < c.length && c[i][j] != '.' && c[i + 1][j] == c[i][j] && c[i + 2][j] == c[i][j]);
		r |= (i != 0 && i != c.length - 1 && c[i][j] != '.' && c[i + 1][j] == c[i][j] && c[i - 1][j] == c[i][j]);
		return r;
	}
	static boolean checkh(char[][] c, int i, int j) {
		return checkem(c[i], j) ||  checkdown(c[i], j) || checkup(c[i], j);
	}
	static boolean checkem(char[] c, int k) {
		if(k == 0 || k == c.length - 1) {return false;}
		return c[k] != '.' && c[k] == c[k - 1] && c[k] == c[k + 1];
	}
	static boolean checkdown(char[] c, int k) {
		if(k <= 1) {return false;}
		return c[k] != '.' && c[k] == c[k - 1] && c[k] == c[k - 2];
	}
	static boolean checkup(char[] c, int k) {
		if(k + 2 >= c.length) {return false;}
		return c[k] != '.' && c[k] == c[k + 1] && c[k] == c[k + 2];
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