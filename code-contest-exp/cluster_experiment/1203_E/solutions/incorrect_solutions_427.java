
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
		ArrayList<Integer> result = new ArrayList<Integer>();
		for(int i = 0; i < number; i++)  {
			int next = r.nextInt();
			numbers[i] = next;
		}
		Arrays.sort(numbers);
		for(int i = 0; i < number; i++)  {
				if(numbers[i] != 1 && !result.contains(numbers[i]-1)) {
					result.add(numbers[i]-1);
				}
				else if(!result.contains(numbers[i]))  {
					result.add(numbers[i]);
				}
				else if(!result.contains(numbers[i]+1))  {
					result.add(numbers[i]+1);
				}
			
		}
		for(int i = 0; i < result.size(); i++)  {
			System.out.println(result.get(i));
		}
		pw.println(result.size());
		pw.close();
		
	}

}
