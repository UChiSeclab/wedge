import java.util.*;
import java.io.*;


public class Main{
    public static long gcd(long a,long b){
        if (a==0){
            return b;
        }
        else{
            return gcd(b%a,a);
        }
    }
    public static void main(String[] args) throws IOException{
        Reader scan = new Reader();
        scan.init(System.in);
        OutputStream output = System.out;
        PrintWriter out = new PrintWriter(output);
		int N = scan.nextInt();
		PriorityQueue<Integer> q = new PriorityQueue<>(new Comp());
		HashSet<Integer> map = new HashSet<>();
        for (int i=0;i<N;i++){
			q.add(scan.nextInt());
		}
		while(!q.isEmpty()){
			int A = q.poll();
			// System.out.print(A+" ");
			if (!map.contains(A+1)){
				map.add(A+1);
			}
			else if(!map.contains(A)){
				map.add(A);
			}
			else if (!map.contains(A-1) && A-1>0){
				map.add(A-1);
			}
		}
		// System.out.println();
		System.out.println(map.size());
        out.close();
    }
}
class Comp implements Comparator<Integer>{
	public int compare(Integer a,Integer b){
		return (int)(b-a);
	}
}
class Reader {
    static BufferedReader reader;
    static StringTokenizer tokenizer;
    static void init(InputStream input) {
        reader = new BufferedReader(
                     new InputStreamReader(input) );
        tokenizer = new StringTokenizer("");
    }
    static String next() throws IOException {
        while ( ! tokenizer.hasMoreTokens() ) {
            tokenizer = new StringTokenizer(
                   reader.readLine() );
        }
        return tokenizer.nextToken();
    }
    static int nextInt() throws IOException {
        return Integer.parseInt( next() );
    }
    static long nextLong() throws IOException {
        return Long.parseLong( next() );
    }
    static double nextDouble() throws IOException {
        return Double.parseDouble( next() );
    }
}