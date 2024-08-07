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
            String result = "";
            HashMap<Character, HashSet<Integer>> indices = new HashMap<>();
            int current = 0;
            int[] sums = new int[26];

            
            //Index all possible characters
            for(int i=0;i<s.length();i++){
                if(!indices.containsKey(s.charAt(i))){
                    indices.put(s.charAt(i), new HashSet<>());
                    sums[s.charAt(i)-'a']+=s.length() - i;
                }
                indices.get(s.charAt(i)).add(i);
            }
            
            
            //While we haven't located the node, find the right branch
            while(current < k && !indices.isEmpty()){
                //Find the correct branch
                char nextChar = '-';
                
                for(char i='a';i<='z';i++){
                    if(!indices.containsKey(i)){
                        continue;
                    }
                    int sum = sums[i-'a'];
                    
                    if(current + sum >= k){
                        nextChar = i;
                        break;
                    }else{
                        current += sum;
                    }
                }
                
                HashMap<Character, HashSet<Integer>> tmp = new HashMap<>();
                //System.out.println(indices);
                //System.out.println(nextChar + " - " + current +"/" + k);
                sums = new int[26];
                if(indices.containsKey(nextChar)){
                    current += indices.get(nextChar).size();
                    current = Math.min(current, k);
                    for(int index : indices.get(nextChar)){
                        if(index+1 < s.length()){
                            if(!tmp.containsKey(s.charAt(index+1))){
                                tmp.put(s.charAt(index+1), new HashSet<>());
                                sums[s.charAt(index+1)-'a'] += s.length() - (index+1);
                            }
                        
                            tmp.get(s.charAt(index+1)).add(index+1);
                        }
                    }
                    result += nextChar;
                }
                indices = tmp;
                
            }
            
            if(current == k){
                System.out.println(result);
            }else{
                System.out.println("No such line.");
            }
            
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