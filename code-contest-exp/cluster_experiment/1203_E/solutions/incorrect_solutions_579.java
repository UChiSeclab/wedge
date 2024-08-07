import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 *
 * @author User
 */
public class CF_579_Div3_E 
{
    static class FastReader {
        
        BufferedReader br;
        StringTokenizer st;
        
        public FastReader() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }
        
        String next() {
            while (st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }
        
        int nextInt() {
            return Integer.parseInt(next());
        }
        
        long nextLong() {
            return Long.parseLong(next());
        }
        
        double nextDouble() {
            return Double.parseDouble(next());
        }
        
        String nextLine() {
            String str = "";
            try {
                str = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return str;
        }
    }
    
    static class Node {
        
        long pp;
        long a, b;
        
        Node(long x, long y) {
            a = x;
            b = y;
            pp = a * b;
        }
    }
    
    static class Comp implements Comparator<Node> {
        
        public int compare(Node o1, Node o2) {
            if (o1.pp > o2.pp) {
                return 1;
            } else {
                return -1;
            }
        }
    }

    public static void main(String[] args) {
        FastReader sc = new FastReader();
        
        int n=sc.nextInt();
        int a[]=new int[n];
        
        for(int i=0;i<n;i++)
            a[i]=sc.nextInt();
        
        HashMap<Integer,Integer> hm1=new HashMap();
        HashMap<Integer,Queue<Integer>> hm2=new HashMap();
        
        int start=0;
        int ans=1;
        int i;
        for(i=0;i<n;i++)
        {
            if(!hm1.containsKey(a[i]))
            {
                hm1.put(a[i], 1);
                hm2.put(a[i], new LinkedList<Integer>());
                hm2.get(a[i]).add(i);
            }
            else
            {
                int x=hm1.get(a[i]);
                
                if(a[i]==1)
                {
                    if(x==2)
                    {
                        int d=i-start;
                        if(d>ans)
                            ans=d;
                        start=hm2.get(a[i]).poll()+1;
                    }
                    else
                    {
                        ++x;
                        hm1.put(a[i], x);                                                
                    }
                }
                else
                {
                    if(x==3)
                    {
                        int d=i-start;
                        if(d>ans)
                            ans=d;
                        start=hm2.get(a[i]).poll()+1;
                    }
                    else
                    {
                        ++x;
                        hm1.put(a[i], x);                                                
                    }
                }
                hm2.get(a[i]).add(i);
            }            
                
        }
        if(i-start>ans)
            ans=i-start;
        System.out.println(ans);
    }
    
    
}
