import java.io.*;

import java.util.StringTokenizer;

/**
 * Created by marsov on 04.03.17.
 */
public class MainD {
    public static void main(String args[]) {
        InputStream ins = System.in;
        OutputStream outs = System.out;
        InputReader in = new InputReader(ins);
        PrintWriter out = new PrintWriter(outs);
        TaskD task = new TaskD();
        task.solve(in, out);
        out.close();
    }

    static class TaskD {
        int n, k;
        void solve(InputReader in, PrintWriter out) {
            n = in.nextInt();
            k = in.nextInt();

            int[] nums = new int[n];
            for (int i = 0; i < n; i++)
                nums[i] = in.nextInt();
            int len = nums.length;
            int pairs = 0;
                for (int i = 0; i < len; i++)
                    for (int j = i+1; j < len; j++)
                        if (k == difference(nums[i],nums[j])) {
                            ++pairs;
                        }
            out.println(pairs);
        }

        static int binlog(int bits) {
            int log = 0;
            if( ( bits & 0xffff0000 ) != 0 ) { bits >>>= 16; log = 16; }
            if( bits >= 256 ) { bits >>>= 8; log += 8; }
            if( bits >= 16  ) { bits >>>= 4; log += 4; }
            if( bits >= 4   ) { bits >>>= 2; log += 2; }
            return log + ( bits >>> 1 );
        }

        int difference(int num1, int num2) {
            int count;
            if (num1 == 0)
                count = (num2 & 1) + binlog(num2);
            else
                if (num2 == 0)
                    count = (num1 & 1) + binlog(num1);
                else
                    count = ((num1 & 1) & (num2 & 1)) + (binlog(num1 ^ num2));
            return count;
        }

         /*void test() {
            long s = System.currentTimeMillis();
            long e = 0;
            int pairs = 0;
            int n = 10000;
            int k = 4;
            for (int i = 0; i < n; i++)
                for (int j = i+1; j < n; j++)
                    if (k == difference(i,j)) {
                        ++pairs;
                    }
             System.out.println(pairs);
             e = System.currentTimeMillis();
             System.out.println((double)(e - s) / 1000);
        }*/

        /*boolean check2d(int num) {
            return (num == 0) || ((num > 0) && ((num & (num - 1)) == 0));
        }*/

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
                    tokenizer = new StringTokenizer(reader.readLine()," ");
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
