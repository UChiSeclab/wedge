import java.io.*;
import java.util.*;

// HAI ARIELL GUE BERAKHIR DENGAN MELIHAT GOOGLE :(
    // TAPI GUE NGERJAIN SENDIRI KO BENERAN T-T
public class sushi {
    private static InputReader in = new InputReader(System.in);
    private static PrintWriter out = new PrintWriter(System.out);
    public static int arr[];

    public static void main(String[] args) {
        InputStream inputStream = System.in;
        in = new InputReader(inputStream);
        OutputStream outputStream = System.out;
        out = new PrintWriter(outputStream);
        makanSushi();
        out.close();
    }
    public static void makanSushi() {
        int input = in.nextInt();
        arr = new int[input];
        for (int i = 0; i < input; i++) {
            int sushi = in.nextInt();
            arr[i] = sushi;
        }
        int count1 = 1;
        int count2 = 1;
        int res = 0;

        for (int i = 2; i < input; i++) {
            if(arr[i] == arr[i-1]) {
                if (arr[i] == 1) {
                    count1++;
                }
                else if (arr[i] == 2) {
                    count2++;
                }
            } else {
                if(arr[i] == 1) {
                    res = Math.max(res, Math.min(count1,count2));
                    count1 = 1;
                } else if (arr[i] == 2) {
                    res = Math.max(res, Math.min(count1,count2));
                    count2 = 1;
                }
            }
        }
        res = Math.max (res, Math.min(count1,count2));
        out.println(res*2);
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
 
    }
}
	   						 		      	 	 		 		 		