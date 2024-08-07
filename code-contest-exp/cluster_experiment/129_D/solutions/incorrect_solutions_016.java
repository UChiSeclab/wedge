import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.StringTokenizer;


public class TaskD {
    public static void main(String[] args) {
        InputStream inputStream;
        String str = null;
        if(str == null){
            inputStream = System.in;
        }else{
            inputStream = new ByteArrayInputStream(str.getBytes());
        }
        
        OutputStream outputStream = System.out;
        InputReader in = new InputReader(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        Solver solver = new Solver();
        solver.solve(1, in, out);
        out.close();
    }
    

    static class Solver {
        public void solve(int testNumber, InputReader in, PrintWriter out) {
            String s = in.next();
            int k = in.nextInt();
            HashMap<Character, HashSet<Integer>>indices = new HashMap<>();
            HashMap<Character, HashSet<Integer>>possible = new HashMap<>();
            for(char i='a';i<='z';i++){
                indices.put(i, new HashSet<>());
                possible.put(i, new HashSet<>());
            }
            
            //index all letters
            for(int i=0;i<s.length();i++){
                indices.get(s.charAt(i)).add(i);
                possible.get(s.charAt(i)).add(i);
            }
            
            String result = "";
            
            int current = 0;
            
            while(current!=k){
                //Find the next character
                char i;
                for(i='a';i<='z';i++){
                    int sum = 0;
                    for(Integer index : possible.get(i)){
                        sum += s.length() - index;
                    }
                    //System.out.println(current+ " " + sum);
                    if(current + sum < k){
                        current += sum;
                    }else{
                        result += i;
                        break;
                    }
                }
                
                if(i > 'z'){
                    break;
                }
                //System.out.println(i+" "+possible.get(i).size());
                if(current + possible.get(i).size() >= k){
                    break;
                }
                current += possible.get(i).size();
                HashMap<Character, HashSet<Integer>>next = new HashMap<>();
                for(char d='a';d<='z';d++){
                    next.put(d, new HashSet<>());
                }
                
                
                
                //System.out.println(i);
                //Build the next possible list
                for(int c : possible.get(i)){
                    if(c+1 < s.length()){
                        //System.out.println("Adding"+s.charAt(c+1) + " " + (c+1));
                        next.get(s.charAt(c+1)).add(c+1);
                    }
                }
                
                possible = next;
            }
            
            System.out.println(result);
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
