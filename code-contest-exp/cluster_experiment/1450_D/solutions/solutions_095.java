import java.io.*;
import java.util.*;


public class A {
    static  Read cin = new Read(System.in);
    static PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
    public static void locl(){
        InputStream in = null;
        try {
            in = new FileInputStream("in.dat");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        cin = new Read(in);
    }
    public static void main(String[] args) {
        /**
         * 要用Collections.sort()而不是Arrays.sort()
         * 记得自己测样例
         * java天下第一
         */
//        locl();
        int n = cin.nextInt();
        while (n>0) {
            solve();
            --n;
        }
    }
    public static void solve(){
        int n = cin.nextInt();
        ArrayList<Integer> a = new ArrayList<>(n);
        TreeMap<Integer, Integer> cnt = new TreeMap<Integer, Integer>();
        for(int i=0; i<n; i++){
            a.add(cin.nextInt());
            cnt.put(a.get(i), cnt.getOrDefault(a.get(i), 0)+1);
        }
        char[] ans = new char[n];
        for(int i =0; i<n; i++) ans[i] = '0';
        if(cnt.getOrDefault(1,0)>0) ans[n-1] = '1';
        if(cnt.size()==n) ans[0] = '1';
        int l = 0;
        int r = n-1;
        int cur = 1;
        while (l<r){
            if(a.get(l)!=cur&&a.get(r)!=cur) break;
            if(a.get(l)==cur){
               int c =  cnt.getOrDefault(cur, 0);
               if(c<=1) cnt.remove(cur);
               c = cnt.firstKey();
               if(c!=cur+1) break;
               ans[n-cur-1]='1';
               l++;
            }else if(a.get(r)==cur){
                int c =  cnt.getOrDefault(cur, 0);
                if(c<=1) cnt.remove(cur);
                c = cnt.firstKey();
                if(c!=cur+1) break;
                ans[n-cur-1]='1';
                r--;
            }
            ++cur;
        }
        out.println(new String(ans));
        out.flush();
    }
}
class Read{
    StringTokenizer tokenizer = null;
    BufferedReader reader = null;
    public Read(){};
    public Read(InputStream is){
        reader = new BufferedReader(new InputStreamReader(is));
    }
    public String next(){
        while(tokenizer==null||!tokenizer.hasMoreTokens()){
            try{
                tokenizer = new StringTokenizer(reader.readLine());
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        return tokenizer.nextToken();
    }
    public Integer nextInt(){
        return Integer.parseInt(next());
    }
    public Long nextLong(){
        return Long.parseLong(next());
    }
    public Double nextDouble(){
        return Double.parseDouble(next());
    }
}

class Node implements Comparable<Node>{
    Integer x;
    Node(){};
    Node(Integer _x){
        x = _x;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return Objects.equals(x, node.x);
    }

    @Override
    public int compareTo(Node o) {
        return equals(o)? 0: x>=o.x? 1: -1;
    }
}
