import java.util.*;
import java.lang.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.io.*;
public class Solution {
		static class FastReader {
	        BufferedReader br;
	        StringTokenizer st;
	
	        public FastReader() {
	            br = new BufferedReader(new
	                    InputStreamReader(System.in));
	        }
	        String next() {
	            while (st == null || !st.hasMoreElements()) {
	                try{
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
		public int[] readIntArray(int n) {
				int[] arr = new int[n];
				for(int i=0; i<n; ++i)
					arr[i]=nextInt();
				return arr;
			}
	
	        double nextDouble() {
	            return Double.parseDouble(next());
	        }
	        static void sort(int[] arr){
	        	Arrays.sort(arr);
	        }
	
	       String nextLine() {
	            String str = "";
	            try{
	                str = br.readLine();
	            }
	            catch (IOException e) {
	                e.printStackTrace();
	            }
	            return str;
	        }
	    }
		static int solve(int[] arr, int n) {
			int count1 = 1;
			int count2 = 1;
			int max = 0;
			int res = 1;
			int min = Integer.MAX_VALUE;
			for(int  i = 0; i < n -1;i++) {
				if(arr[i] != arr[i+1]) {
					min = Math.min(count1, count2);
					max = Math.max(min, res);
					count2 = count1;
					res = max;
					count1 = 1;
				}
				else {
					count1++;
				}
			}
			min = Math.min(count1, count2);
			max = Math.max(min, res);
			return 2*max;
			
			
		}
		public static void main(String args[]) throws IOException {
	        FastReader sc = new FastReader();
	        long start = System.currentTimeMillis();
	        	
	        	int n = sc.nextInt();
	        	int[] arr =  sc.readIntArray(n);
	        	
	        	System.out.println(solve(arr,n));
	        	long end = System.currentTimeMillis();
	        	NumberFormat formatter = new DecimalFormat("#0.00000");
	        	//System.out.print("Execution time is " + formatter.format((end - start) / 1000d) + " seconds");
	        }
	}
