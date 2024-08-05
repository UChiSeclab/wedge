
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Andy Phan
 */
public class e {
    public static void main(String[] args) {
        FS in = new FS(System.in);
        PrintWriter out = new PrintWriter(System.out);
        
        int n = in.nextInt();
        Integer[] arr = new Integer[n];
        for(int i = 0; i < n; i++) arr[i] = in.nextInt();
        Arrays.sort(arr);
        if(arr[0] != 1) arr[0]--;
        int tot = 1;
        int max = 0;
        for(int i = 1; i < n; i++) {
            if(arr[i] == max) {
                tot++;
                arr[i]++;
            } else if(arr[i]-1 > max) {
                tot++;
                arr[i]--;
            } else if(arr[i] > max) {
                tot++;
            }
            max = Math.max(max, arr[i]);
        }
        
        System.out.println(tot);
        
        out.close();
    }
    
    static class FS {

        BufferedReader in;
        StringTokenizer token;
        
        public FS(InputStream str) {
            in = new BufferedReader(new InputStreamReader(str));
        }
        
        public String next() {
            if (token == null || !token.hasMoreElements()) {
                try {
                    token = new StringTokenizer(in.readLine());
                } catch (IOException ex) {
                }
                return next();
            }
            return token.nextToken();
        }
        
        public int nextInt() {
            return Integer.parseInt(next());
        }
    }
}
