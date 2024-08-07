import java.io.*;
import java.math.*;
import java.text.*;
import java.util.*;
import java.util.regex.*;
import java.util.concurrent.ThreadLocalRandom;
public class Solution{
    InputStream is;
    PrintWriter out;
    String INPUT = "";
    Random r = new Random();
    Scanner in=new Scanner(System.in);
    long mod=1000000007l;
    class Node{
        int id,val;
        Node(int x,int y){
            this.id=x;
            this.val=y;
        }
    }

    public void solve(){
        int n=ni();
        int[] arr=na(n);
        int[] two=new int[n];
        int[] one=new int[n];
        if(arr[0]==1)
            one[0]+=1;
        else
            two[0]+=1;
        for(int i=1;i<n;i++){
            if(arr[i]==1){
                one[i]=one[i-1]+1;
            }
            else{
                two[i]=two[i-1]+1;
            }
        }
        int max=1;
        int i=0;
        ArrayList<Node> al=new ArrayList<Node>();
        while(i<n){
            int j=i;
            if(arr[i]==1){
                while(j<n && arr[j]==1){
                    j+=1;
                }
                al.add(new Node(i,one[j-1]));
            }
            else{
                while(j<n && arr[j]==2){
                    j+=1;
                }
                al.add(new Node(i,two[j-1]));
            }
            i=j;
        }
        int size=al.size();
        for(i=0;i<size;i++){
            Node temp=al.get(i);
            int val=temp.val;
            if(i+1 < size){
                int x=al.get(i+1).val;
                max=Math.max(max,Math.min(x,val));
            }
            if(i-1 >=0 ){
                int x=al.get(i-1).val;
                max=Math.max(max,Math.min(x,val));
            }
        }
        out.println(max*2);

    }
    public void run(){
        is = new DataInputStream(System.in);
        out = new PrintWriter(System.out);
        int t=1;while(t-- > 0)solve();
        out.flush();
    }
    public static void main(String[] args){new Solution().run();}
    //Fast I/O code is copied from uwi code.
    private byte[] inbuf = new byte[1024];
    public int lenbuf = 0, ptrbuf = 0;
    private int readByte(){
        if(lenbuf == -1)throw new InputMismatchException();
        if(ptrbuf >= lenbuf){
            ptrbuf = 0;
            try { lenbuf = is.read(inbuf); } catch (IOException e) { throw new InputMismatchException(); }
            if(lenbuf <= 0)return -1;
        }
        return inbuf[ptrbuf++];
    }
    private boolean isSpaceChar(int c) { return !(c >= 33 && c <= 126); }
    private int skip() { int b; while((b = readByte()) != -1 && isSpaceChar(b)); return b; }
    private double nd() { return Double.parseDouble(ns()); }
    private char nc() { return (char)skip(); }
    private String ns(){
        int b = skip();
        StringBuilder sb = new StringBuilder();
        while(!(isSpaceChar(b))){ 
            sb.appendCodePoint(b);
            b = readByte();
        }
        return sb.toString();
    }
    private char[] ns(int n){
        char[] buf = new char[n];
        int b = skip(), p = 0;
        while(p < n && !(isSpaceChar(b))){
            buf[p++] = (char)b;
            b = readByte();
        }
        return n == p ? buf : Arrays.copyOf(buf, p);
    }
    static int i(long n){
        return (int)Math.round(n);
    }
    private char[][] nm(int n, int m){
        char[][] map = new char[n][];
        for(int i = 0;i < n;i++)map[i] = ns(m);
        return map;
    }
    private int[] na(int n){
        int[] a = new int[n];
        for(int i = 0;i < n;i++)a[i] = ni();
        return a;
    }
    private int ni(){
        int num = 0, b;
        boolean minus = false;
        while((b = readByte()) != -1 && !((b >= '0' && b <= '9') || b == '-'));
        if(b == '-'){
            minus = true;
            b = readByte();
        }
        
        while(true){
            if(b >= '0' && b <= '9'){
                num = num * 10 + (b - '0');
            }else{
                return minus ? -num : num;
            }
            b = readByte();
        }
    }
    private long nl(){
        long num = 0;
        int b;
        boolean minus = false;
        while((b = readByte()) != -1 && !((b >= '0' && b <= '9') || b == '-'));
        if(b == '-'){
            minus = true;
            b = readByte();
        }
        
        while(true){
            if(b >= '0' && b <= '9'){
                num = num * 10 + (b - '0');
            }else{
                return minus ? -num : num;
            }
            b = readByte();
        }
    }
    
}