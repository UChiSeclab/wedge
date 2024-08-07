
import java.io.*;
import java.util.*;
public class Boxers {
	static class InputReader { 
		BufferedReader reader; 
		StringTokenizer tokenizer;
		public InputReader(InputStream stream) { 
			reader = new BufferedReader(new InputStreamReader(stream), 32768); 
			tokenizer = null;
		}
		String next() { 
			while (tokenizer == null || !tokenizer.hasMoreTokens()) { try {
				tokenizer = new StringTokenizer(reader.readLine());
			} 
			catch (IOException e) { 
				throw new RuntimeException(e);
			} 
		} 
			return tokenizer.nextToken(); 
		}
		public int nextInt() { 
			return Integer.parseInt(next());
		}
		public long nextLong() { 
			return Long.parseLong(next());
		}
		public double nextDouble() { 
			return Double.parseDouble(next());
		} 
	}
	static InputReader r = new InputReader(System.in); 
	static PrintWriter pw = new PrintWriter(System.out);
	public static void main(String[] args) {
		int number = r.nextInt();
		int[] numbers = new int[number];
		int[] taken = new int[150005];
		for(int i = 0; i < number; i++)  {
			int next = r.nextInt();
			numbers[i] = next;
		}
		int count = 0;
		Arrays.sort(numbers);
		for(int i = 0; i < number; i++)  {
			if(taken[numbers[i]-1] == 0 && numbers[i] > 1)  {
				taken[numbers[i]-1] = 1;
				System.out.println(numbers[i]-1);
				count++;
			}
			else if(taken[numbers[i]] == 0)  {
				taken[numbers[i]] = 1;
				System.out.println(numbers[i]);
				count++;
			}
			else if(taken[numbers[i]+1] == 0)  {
				taken[numbers[i]+1] = 1;
				System.out.println(numbers[i]+1);
				count++;
			}
		}
	pw.println(count);
	pw.close();
	}

		
	

}
