import java.io.*;
import java.util.*;
public class Main {
    static class Substring implements Comparable<Substring> 
    {
        public String str;
        public int pos;
        public Substring(String str, int pos) 
        {
                super();
                this.str = str;
                this.pos = pos;
        }
        public boolean equals(Substring cmp) 
        {
                return str.equals(cmp.str);
        }
        public int compareTo(Substring o) 
        {
                return str.compareTo(o.str);
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out=new PrintWriter(System.out);
        String input = in.readLine();
        int k = Integer.parseInt(in.readLine());
        PriorityQueue<Substring> strings = new PriorityQueue<Substring>();
        for (int i = 0; i < input.length(); i++)
        {
            strings.add(new Substring(input.substring(i,i+1),i));
        }
        for (int i = 0; i < k-1; i++) 
        {
            Substring cur = strings.poll();
            if (cur == null) break;
            if (input.length() > cur.pos+cur.str.length()) 
            {
                cur.str = input.substring(cur.pos, cur.pos+cur.str.length()+1);
                strings.add(cur);
            }
        }
        if (strings.isEmpty()) System.out.println("No such line.");
        else 
        {
            Substring p = strings.poll();
            System.out.println(p.str);
        }
        out.close();
    }
}