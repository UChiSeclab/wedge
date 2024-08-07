import java.util.*;
import java.io.*;
public class Main {
    
    static class Scan {
        private byte[] buf=new byte[1024];
        private int index;
        private InputStream in;
        private int total;
        public Scan()
        {
            in=System.in;
        }
        public int scan()throws IOException
        {
            if(total<0)
            throw new InputMismatchException();
            if(index>=total)
            {
                index=0;
                total=in.read(buf);
                if(total<=0)
                return -1;
            }
            return buf[index++];
        }
        public int scanInt()throws IOException
        {
            int integer=0;
            int n=scan();
            while(isWhiteSpace(n))
            n=scan();
            int neg=1;
            if(n=='-')
            {
                neg=-1;
                n=scan();
            }
            while(!isWhiteSpace(n))
            {
                if(n>='0'&&n<='9')
                {
                    integer*=10;
                    integer+=n-'0';
                    n=scan();
                }
                else throw new InputMismatchException();
            }
            return neg*integer;
        }
        public double scanDouble()throws IOException
        {
            double doub=0;
            int n=scan();
            while(isWhiteSpace(n))
            n=scan();
            int neg=1;
            if(n=='-')
            {
                neg=-1;
                n=scan();
            }
            while(!isWhiteSpace(n)&&n!='.')
            {
                if(n>='0'&&n<='9')
                {
                    doub*=10;
                    doub+=n-'0';
                    n=scan();
                }
                else throw new InputMismatchException();
            }
            if(n=='.')
            {
                n=scan();
                double temp=1;
                while(!isWhiteSpace(n))
                {
                    if(n>='0'&&n<='9')
                    {
                        temp/=10;
                        doub+=(n-'0')*temp;
                        n=scan();
                    }
                    else throw new InputMismatchException();
                }
            }
            return doub*neg;
        }
        public String scanString()throws IOException
        {
            StringBuilder sb=new StringBuilder();
            int n=scan();
            while(isWhiteSpace(n))
            n=scan();
            while(!isWhiteSpace(n))
            {
                sb.append((char)n);
                n=scan();
            }
            return sb.toString();
        }
        private boolean isWhiteSpace(int n)
        {
            if(n==' '||n=='\n'||n=='\r'||n=='\t'||n==-1)
            return true;
            return false;
        }
    }
    static ArrayList<Integer> adj_lst[];
    static int vis[];
    static int same[],opp[],arr[];
    public static void main(String args[]) throws IOException {
        Scan input=new Scan();
        int n=input.scanInt();
        arr=new int[n];
        for(int i=0;i<n;i++) {
            arr[i]=input.scanInt();
        }
        same=new int[n];
        opp=new int[n];
        //No of nodes
        adj_lst=new ArrayList[n];
        vis=new int[n];
        for(int i=0;i<n;i++) {
            adj_lst[i]=new ArrayList<Integer>();
        }
        //No of edges
        for(int i=0;i<n;i++) {
            if(i-arr[i]>=0) {
                adj_lst[i].add(i-arr[i]);
            }
            if(i+arr[i]<n) {
                adj_lst[i].add(i+arr[i]);
            }
        }
//        for(int i=0;i<adj_lst.length;i++) {
//            System.out.print(i+"->");
//            for(int j=0;j<adj_lst[i].size();j++) {
//                System.out.print(adj_lst[i].get(j)+" ");
//            }
//            System.out.println();
//        }
        for(int i=0;i<n;i++) {
            same[i]=opp[i]=Integer.MAX_VALUE-1;
        }
        for(int i=0;i<n;i++) {
            if(vis[i]!=2) {
                mod_DFS(i);
            }
        }
        for(int i=0;i<n;i++) {
            if(vis[i]!=2) {
                mod_DFS(i);
            }
        }
        for(int i=0;i<n;i++) {
            if(opp[i]==Integer.MAX_VALUE-1) {
                opp[i]=-1;
            }
        }
        for(int i=0;i<n;i++) {
            System.out.print(opp[i]+" ");
        }
    }
    
    public static void mod_DFS(int root) {
//        System.out.println(root);
        vis[root]++;
        for(int i=0;i<adj_lst[root].size();i++) {
            if(vis[adj_lst[root].get(i)]!=2) {
                mod_DFS(adj_lst[root].get(i));
            }
            if(arr[root]%2==arr[adj_lst[root].get(i)]%2) {
                same[root]=1;
                opp[root]=Math.min(opp[root],opp[adj_lst[root].get(i)]+1);
            }
            else {
                opp[root]=1;
                same[root]=Math.min(same[root],opp[adj_lst[root].get(i)]+1);
            }
        }
        vis[root]=2;
    }
}
