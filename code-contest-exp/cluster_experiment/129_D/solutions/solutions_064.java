//package practice.Impls;

import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class String {
    static PrintWriter pw;
    public static void main(java.lang.String[] args) throws IOException{
        Scanner sc = new Scanner(System.in);
        pw = new PrintWriter(System.out);

        char[] s = sc.next().toCharArray();
        int k = sc.nextInt();
        if((1l*s.length*(s.length+1))/2 < k)
        {
            System.out.println("No such line.");
            return;
        }
        SA sa = new SA(s);

        sa.findLex(k);

        pw.flush();
        pw.close();
    }

    static class SA {

        int[] link, len;
        boolean[] isClone;
        TreeMap<Character, Integer>[] next;
        int lst, idx;

        SA(char[] s)
        {
            int n = s.length;
            link = new int[n<<1];
            isClone = new boolean[n<<1];
            len = new int[n<<1];
            next = new TreeMap[n<<1];
            next[0] = new TreeMap<>();
            for(char c: s)
                addLetter(c);
        }

        void addLetter(char c)
        {
            int cur = ++idx, p = lst;
            while(!next[p].containsKey(c)) { next[p].put(c, cur); p = link[p]; }

            int q = next[p].get(c);
            if(q != cur)
                if(len[q] == len[p] + 1)
                    link[cur] = q;
                else
                {
                    int clone = ++idx;
                    isClone[clone] = true;
                    len[clone] = len[p] + 1;
                    link[clone] = link[q];
                    next[clone] = new TreeMap<>(next[q]);
                    link[cur] = link[q] = clone;
                    while(next[p].get(c) == q) { next[p].put(c, clone);	p = link[p]; }
                }
            len[cur] = len[lst] + 1;
            next[cur] = new TreeMap<>();
            lst = cur;
        }




        long[] size;
        int[] cnt;
        void findLex(long k){
            vis = new boolean[idx+1];
            size = new long[link.length];
            cnt = new int[link.length];
            calcSubSize(0);
            vis[0] = true;
            calcSize(0);
            int u = 0;
            outer: while(k > 0){
                for (char c: next[u].keySet()) {
                    int v = next[u].get(c);
                    if(size[v] < k)
                        k-=size[v];
                    else
                    {
                        pw.print(c);
                        k -= cnt[v];
                        u = v;
                        continue outer;
                    }
                }
                break outer;
            }
        }

        boolean vis[];
        void calcSize(int u){
            size[u] = cnt[u];
            for (char c : next[u].keySet()) {
                int v = next[u].get(c);
                if(!vis[v]){
                    vis[v] = true;
                    calcSize(v);
                }
                size[u]+=size[v];
            }
        }

        void calcSubSize(int u){
            for (int i = 1; i < cnt.length; i++)
                if(!isClone[i])
                    cnt[i] = 1;

            Integer[] nodes = new Integer[idx+1];
            for (int i = 0; i <= idx; i++)
                nodes[i] = i;
            Arrays.sort(nodes, new Comparator<Integer>() {
                @Override
                public int compare(Integer a, Integer b) {
                    return len[b] - len[a];
                }
            });

            for (int i = 0; i < nodes.length; i++) {
                cnt[link[nodes[i]]] += cnt[nodes[i]];
            }
        }




    }



    static class Scanner
    {
        StringTokenizer st; BufferedReader br;
        public Scanner(InputStream s){	br = new BufferedReader(new InputStreamReader(s));}
        public Scanner(java.lang.String s) throws FileNotFoundException {	br = new BufferedReader(new FileReader(new File(s)));}
        public java.lang.String next() throws IOException {while (st == null || !st.hasMoreTokens()) st = new StringTokenizer(br.readLine());return st.nextToken();}
        public int nextInt() throws IOException {return Integer.parseInt(next());}
        public long nextLong() throws IOException {return Long.parseLong(next());}
        public java.lang.String nextLine() throws IOException {return br.readLine();}
        public boolean ready() throws IOException {return br.ready();}
    }


}
