import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class Main {
	private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		int n = Integer.parseInt(br.readLine().trim());
		int a[] = new int[n];
		String[] s = br.readLine().trim().split(" ");
		for(int i = 0; i < n; i++) {
			a[i] = Integer.parseInt(s[i]);
		}
		int l = 0,c = 1;
		int ans = 0;
		for(int i = 1; i < n; i++) {
			if(a[i] == a[i-1]) c++;
			else {
				l = c;
				c = 1;
			}
			ans = Math.max(ans, Math.min(l, c)*2);
		}
		System.out.println(ans);
	}
}