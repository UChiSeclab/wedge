import java.io.*;
import java.util.*;
 
public class Main {

	public static void main(String[] args)throws IOException {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);
        
		int n = in.nextInt();
		int arr[] = new int[150002];
		Arrays.fill(arr,0);
		arr[0]=1;
		for(int i=0;i<n;i++)
			arr[in.nextInt()]++;
		int ans=0;
		for(int i=1;i<arr.length;i++)
		{
			if(arr[i]==0)
				continue;
			ans++;
			if(arr[i]==2)
			{
				if(arr[i-1]==0)
					ans++;
				else if(arr[i+1]==0){
					arr[i+1]=1;
				}
			}
			else if(arr[i]>=3)
			{
				if(arr[i-1]==0)
					ans++;
				if(arr[i+1]==0){
					arr[i+1]=1;
				}
			}
		}
		out.println(ans);
        out.close();
	}
    static class InputReader {
        public BufferedReader reader;
        public StringTokenizer tokenizer;

        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
            tokenizer = null;
        }

        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
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

    }

}