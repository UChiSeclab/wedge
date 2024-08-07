
import java.util.*;
import java.lang.*;
import java.io.*;

public class C {

//		            ***                          ++                      
//	             +=-==+                      +++=-                     
//	            +-:---==+                   *+=----=                   
//	           +-:------==+               ++=------==                  
//	           =-----------=++=========================                
//	          +--:::::---:-----============-=======+++====             
//	          +---:..:----::-===============-======+++++++++           
//	          =---:...---:-===================---===++++++++++         
//	          +----:...:-=======================--==+++++++++++        
//	          +-:------====================++===---==++++===+++++      
//	         +=-----======================+++++==---==+==-::=++**+     
//	        +=-----================---=======++=========::.:-+*****    
//	       +==::-====================--:  --:-====++=+===:..-=+*****   
//	       +=---=====================-...  :=..:-=+++++++++===++*****  
//	       +=---=====+=++++++++++++++++=-:::::-====+++++++++++++*****+ 
//	      +=======++++++++++++=+++++++============++++++=======+****** 
//	      +=====+++++++++++++++++++++++++==++++==++++++=:...  . .+**** 
//	     ++====++++++++++++++++++++++++++++++++++++++++-.     ..-+**** 
//	     +======++++++++++++++++++++++++++++++++===+====:.    ..:=++++ 
//	     +===--=====+++++++++++++++++++++++++++=========-::....::-=++* 
//	     ====--==========+++++++==+++===++++===========--:::....:=++*  
//	     ====---===++++=====++++++==+++=======-::--===-:.  ....:-+++   
//	     ==--=--====++++++++==+++++++++++======--::::...::::::-=+++    
//	     ===----===++++++++++++++++++++============--=-==----==+++     
//	     =--------====++++++++++++++++=====================+++++++     
//	     =---------=======++++++++====+++=================++++++++     
//	     -----------========+++++++++++++++=================+++++++    
//	     =----------==========++++++++++=====================++++++++  
//	     =====------==============+++++++===================+++==+++++ 
//	     =======------==========================================++++++

	// created by : Nitesh Gupta

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int t = Integer.parseInt(br.readLine());
		StringBuilder sb = new StringBuilder();
		while (t-- > 0) {
			String[] scn = (br.readLine()).trim().split(" ");
			int n = Integer.parseInt(scn[0]);
			HashMap<Integer, HashMap<Integer, Integer>> map = new HashMap<>();

			char[] str = br.readLine().toCharArray();
			int dd = 0, kk = 0;
			int[] ans = new int[n];
			Arrays.fill(ans, 1);
			for (int i = 0; i < n; i++) {
				if (str[i] == 'D') {
					dd += 1;

				} else {
					kk += 1;
				}
				int g = gcd(dd, kk);
				int d = dd / g;
				int k = kk / g;
				if (map.containsKey(d)) {
					HashMap<Integer, Integer> child = map.get(d);
					if (child.containsKey(k)) {
						int cc = child.get(k) + 1;
						map.get(d).put(k, cc);
						ans[i] = cc;
					} else {
						map.get(d).put(k, 1);
					}
				} else {
					map.put(d, new HashMap<>());
					map.get(d).put(k, 1);
				}
//				System.out.println(d + " " + k + " " + ans[i]);
			}
			for (int ele : ans) {
				sb.append(ele + " ");
			}

			sb.append("\n");
		}
		System.out.println(sb);
		return;

	}

	public static int gcd(int a, int b) {
		return b == 0 ? a : gcd(b, a % b);
	}

	static class pair {
		int y;
		int c;

		pair(int a, int b) {
			y = a;
			c = b;

		}

	}

	public static void sort(long[] arr) {
		int n = arr.length;
		for (int i = 0; i < n; i++) {
			int idx = (int) Math.random() * n;
			long temp = arr[i];
			arr[i] = arr[idx];
			arr[idx] = temp;
		}
		Arrays.sort(arr);
	}

	public static void sort(int[] arr) {
		int n = arr.length;
		for (int i = 0; i < n; i++) {
			int idx = (int) Math.random() * n;
			int temp = arr[i];
			arr[i] = arr[idx];
			arr[idx] = temp;
		}
		Arrays.sort(arr);
	}

	public static void print(long[][] dp) {
		for (long[] a : dp) {
			for (long ele : a) {
				System.out.print(ele + " ");
			}
			System.out.println();
		}
	}

	public static void print(int[][] dp) {
		for (int[] a : dp) {
			for (int ele : a) {
				System.out.print(ele + " ");
			}
			System.out.println();
		}
	}

	public static void print(int[] dp) {
		for (int ele : dp) {
			System.out.print(ele + " ");
		}
		System.out.println();
	}

	public static void print(long[] dp) {
		for (long ele : dp) {
			System.out.print(ele + " ");
		}
		System.out.println();
	}

}
