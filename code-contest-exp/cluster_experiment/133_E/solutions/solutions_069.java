import java.awt.Point;
import java.io.*;
import java.math.BigInteger;
import java.util.*;

import static java.lang.Math.*;

public class AC {
    
    //solve by Fdg (it's good solve)
    
    int [][][][] d;
    int l;
    char [] c ;
    public void solve() throws IOException {
        
        c = in.readLine().toCharArray();
        int n  = readInt();
        l = c.length;
        d = new int [202][l+2][n+2][2]; 
        for (int i =0; i<202;i++){
            for (int j =0; j<l+2;j++){
                for(int k = 0; k<n+2; k++){
                    for (int q = 0; q<2; q++){
                        d[i][j][k][q] = -1;
                    }
                }
            }
        }
        out.print(rec(100,0,n,0));
    }
    
    int rec (int pos,int step,int left,int cource ){
    
        if(d[pos][step][left][cource]!=-1) {
            return d[pos][step][left][cource];
        }
        if (step==l){
            if ((left&1)>0){
                return 0;
            }
            return abs(pos-100);
        }
        int way=0;
        if (cource==0){
            way++;
        }
        else way--; 
        int ans = 0;
        if (c[step]=='F'){
            ans = max(ans,rec(pos+way,step+1,left,cource));
        }
        if (c[step] == 'T'){
            ans = max(ans,rec(pos,step+1,left,cource^1));
        }
        if (left>0){
            if (c[step]=='F'){
                ans = max(ans,rec(pos,step+1,left-1,cource^1));
            }
            if (c[step]=='T'){
                ans = max(ans,rec(pos+way,step+1,left-1,cource));
            }
        }
        return d[pos][step][left][cource] = ans;
        
        
    }
    

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    // ===========================================================================
    final boolean ONLINE_JUDGE = System.getProperty("ONLINE_JUDGE") != null;
    BufferedReader in;
    PrintWriter out;
    StringTokenizer tok = new StringTokenizer("");

    void init() throws FileNotFoundException {
        if (ONLINE_JUDGE) {
            in = new BufferedReader(new InputStreamReader(System.in));
            out = new PrintWriter(System.out);
        } else {
            in = new BufferedReader(new FileReader("input.txt"));
            out = new PrintWriter("output.txt");
        }
    }

    String readString() throws IOException {
        while (!tok.hasMoreTokens()) {
            tok = new StringTokenizer(in.readLine());
        }
        return tok.nextToken();
    }

    int readInt() throws IOException {
        return Integer.parseInt(readString());
    }

    long readLong() throws IOException {
        return Long.parseLong(readString());
    }

    double readDouble() throws IOException {
        return Double.parseDouble(readString());
    }

    public static void main(String[] args) {
        new AC().run();
    }

    public void run() {
        try {
            long t1 = System.currentTimeMillis();
            init();
            solve();
            out.close();
            long t2 = System.currentTimeMillis();
            System.err.println("Time = " + (t2 - t1));
        } catch (Exception e) {
            e.printStackTrace(System.err);
            System.exit(-1);
        }
    }

}
