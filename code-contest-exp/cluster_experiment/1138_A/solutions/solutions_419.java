//codeforces_1138A
import java.io.*;
import java.util.*;
import static java.lang.Math.*;
import java.math.*;

public class Main{

	static PrintWriter go = new PrintWriter(System.out);

	public static void main(String args[]) throws IOException,FileNotFoundException {
		BufferedReader gi = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(gi.readLine());
		int[] l = parseArray(gi);
		int ans = 0;
		int cur = 0;
		int x = 1;
		int a = 1, b = 0;
		while ( x < n && l[x] == l[cur] ){
			x++; a++;
		}
		cur = x;
		while ( cur < n ){
			while ( x < n && l[cur] == l[x] ){ x++; b++; }
			ans = max( ans, min(a,b) );
			a = b; b = 0;
			cur = x;
		}
		go.println(ans*2);
		go.close();
	}

	static int[] parseArray(BufferedReader gi) throws IOException{
		String[] line = gi.readLine().trim().split(" ");
		int[] rez = new int[line.length];
		for ( int k = 0; k < line.length; k++){
			rez[k] = Integer.parseInt(line[k]);
		}
		return rez;
	}

}
