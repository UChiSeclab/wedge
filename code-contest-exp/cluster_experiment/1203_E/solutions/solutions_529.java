import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.io.InputStream;

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
		
		public boolean check(int f,int s, int val){
			if(val == f || val == s){
				return true;
			}
			return false;
		}
		
		public int solve(int testNumber, InputReader in, PrintWriter out) {
			int n,ans,as,f,s,tmp;
			int[] a;
			n=in.nextInt();
			as=150001;
			a=new int[as];
			for(int i=0;i<n;i++){
				tmp=in.nextInt();
				a[tmp]++;
			}
			f=0;
			s=0;
			ans=0;
			if(a[1] > 0){
				ans++;
				s=1;
				a[1]--;
			}
			if(a[1] > 0){
				ans++;
				f=1;
				s=2;
			}
			
			for(int i=2;i<as;i++){
				if(a[i] > 0 ){
					if(!check(f,s,i-1)){
						f=s;
						s=i-1;
						a[i]--;
						ans++;
					}
				}
				if(a[i] > 0 ){
					if(!check(f,s,i)){
						f=s;
						s=i;
						a[i]--;
						ans++;
					}
				}
				if(a[i] > 0 ){
					if(!check(f,s,i+1)){
						f=s;
						s=i+1;
						a[i]--;
						ans++;
					}
				}
				
			}
			out.println(ans);
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