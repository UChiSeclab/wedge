import java.util.*;
import java.lang.*;
import java.io.*;

public class A
{
	public static void main (String[] args) throws java.lang.Exception
	{
		Scanner sc = new Scanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
			
		int t = sc.nextInt();
outer:	for(int z=0; z<t; z++) {
			
			//Solution begins
			
			int n = sc.nextInt();
			String s = sc.next();
			HashMap<String, Integer> map = new HashMap<>();
			
			int d = 0;
			int k = 0;
			for(int i=0; i<n; i++) {
				if(s.charAt(i) == 'D') d++;
				else k++;
				
				int g = gcd(d,k);
				int a = d/g;
				int b = k/g;
				
				String val = a+":"+b;
				int m_value = map.getOrDefault(val, 0);
				map.put(val, m_value+1);
				
				out.print(map.get(val) + " ");
			}
			System.out.println();
			
			//Solution ends
		}
		sc.close();
	}
	
	public static int gcd(int a, int b) {
		if(b==0) return a;
		return gcd(b,a%b);
	}
}