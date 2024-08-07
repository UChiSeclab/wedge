
import java.io.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) {
        InputReader ir = new InputReader(new BufferedInputStream(System.in));
        int n = ir.nextInt();
        int[] a = new int[n];
        for(int i=0;i<n;i++){
            a[i] = ir.nextInt();
        }
        //Arrays.parallelSort(a);
        int[] mp = new int[150005];
        for(int i=0;i<n;i++){
            if(a[i]-1 > 0 && mp[a[i]-1] == 0){
                mp[a[i]-1] += 1;
            }else if(mp[a[i]] == 0){
                mp[a[i]]++;
            }else if(mp[a[i]+1] == 0){
                mp[a[i]+1]++;
            }
        }

        int count = 0;
        for(int i=1;i<150002;i++){
            if(mp[i]>0){
                count++;
            }
        }
        System.out.println(count);

    }
}

class InputReader {
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