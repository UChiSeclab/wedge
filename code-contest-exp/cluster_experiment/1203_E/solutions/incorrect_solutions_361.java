import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.io.InputStream;
import java.util.HashSet;
public class Main {
    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        InputReader in = new InputReader(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        Task solver = new Task();
		solver.solve(1, in, out);
        out.close();
    }
	
	

    static class Task {
		public int solve(int testNumber, InputReader in, PrintWriter out) {;
			int n,cnt,flag;
			int[] a;
			HashSet<Integer> set;
			n=in.nextInt();
			a=new int[n];
			cnt=0;
			flag=0;
			set=new HashSet<Integer>();
			for(int i=0;i<n;i++){
				a[i]=in.nextInt();
				if(a[i] == 1)
					flag=1;
			}
			for(int i=0;i<n;i++){
				if(a[i] == 1 && !set.contains(a[i])){
					cnt++;
					set.add(1);
				}
				else if(flag == 1 && a[i] == 2 ){
					 if(!set.contains(a[i]) ){
					cnt++;
					set.add(a[i]);
					}
					else if( !set.contains(a[i]+1) ){
						cnt++;
						set.add(a[i]+1);
					}
				}
				else if(a[i]!=1 && !set.contains(a[i]-1)){
					cnt++;
					set.add(a[i]-1);
				}
				else if(!set.contains(a[i]) ){
					cnt++;
					set.add(a[i]);
				}
				else if( !set.contains(a[i]+1) ){
					cnt++;
					set.add(a[i]+1);
				}
			}
			out.println(cnt);
			return 0;
		}
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