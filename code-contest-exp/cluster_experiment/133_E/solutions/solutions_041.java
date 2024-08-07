import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.*;


public class E
{
    BufferedReader in;
    PrintStream out;
    StringTokenizer tok;
    public E() throws NumberFormatException, IOException
    {
        in = new BufferedReader(new InputStreamReader(System.in));
        //in = new BufferedReader(new FileReader("in.txt"));
        out = System.out;
        run();
    }
    
    void run() throws NumberFormatException, IOException
    {
        char[] commands = nextToken().toCharArray();
        int n = nextInt();
        int best = 0;
        int d = 1;
        int[][][] max =new int[commands.length+1][n+1][2];
        int[][][] min = new int[commands.length+1][n+1][2];
        for(int i = 0; i <= commands.length; i++)
            for(int j = 0; j <= n; j++)
                for(int k = 0; k <2; k++)
                {
                    max[i][j][k] = -101;
                    min[i][j][k] = 100;
                }
        
        max[0][0][0] = 0;
        min[0][0][0] = 0;
        int sum = 0;
        for(int i = 1; i <= commands.length; i++)
        {
            if(commands[i-1]=='F')
            {
                sum+=d;
                max[i][0][d==1?0:1] = sum;
                min[i][0][d==1?0:1] = sum;
            }
            else 
            {
                d*=-1;
                max[i][0][d==1?0:1] = sum;
                min[i][0][d==1?0:1] = sum;
            }
        }
        for(int i = 1; i <= commands.length; i++)
        {
            for(int j = 1; j <= n; j++)
            {
                for(int k = 0; k <= j; k++)
                {
                    if(k % 2==0)
                    {
                        if(commands[i-1]=='T')
                        {
                            max[i][j][0] = Math.max(max[i][j][0],max[i-1][j-k][1]);
                            max[i][j][1] = Math.max(max[i][j][1],max[i-1][j-k][0]);
                            
                            min[i][j][0] = Math.min(min[i][j][0],min[i-1][j-k][1]);
                            min[i][j][1] = Math.min(min[i][j][1],min[i-1][j-k][0]);
                        }
                        else
                        {
                            if(max[i-1][j-k][0]>=-100)
                                max[i][j][0] = Math.max(max[i][j][0],max[i-1][j-k][0]+1);
                            if(max[i-1][j-k][1]>=-100)
                                max[i][j][1] = Math.max(max[i][j][1],max[i-1][j-k][1]-1);
                            
                            if(min[i-1][j-k][0]<=100)
                                min[i][j][0] = Math.min(min[i][j][0],min[i-1][j-k][0]+1);
                            if(min[i-1][j-k][1]<=100)
                                min[i][j][1] = Math.min(min[i][j][1],min[i-1][j-k][1]-1);
                        }
                    }
                    else
                    {
                        if(commands[i-1]=='T')
                        {
                            if(max[i-1][j-k][0]>=-100)
                                max[i][j][0] = Math.max(max[i][j][0], max[i-1][j-k][0]+1);
                            if(max[i-1][j-k][1]>=-100)
                                max[i][j][1] = Math.max(max[i][j][1], max[i-1][j-k][1]-1);
                            
                            if(min[i-1][j-k][0]<=100)
                                min[i][j][0] = Math.min(min[i][j][0], min[i-1][j-k][0]+1);
                            if(min[i-1][j-k][1]<=100)
                                min[i][j][1] = Math.min(min[i][j][1], min[i-1][j-k][1]-1);
                        }
                        else
                        {
                            max[i][j][0] = Math.max(max[i][j][0],max[i-1][j-k][1]);
                            max[i][j][1] = Math.max(max[i][j][1], max[i-1][j-k][0]);
                            
                            min[i][j][0] = Math.min(min[i][j][0],min[i-1][j-k][1]);
                            min[i][j][1] = Math.min(min[i][j][1], min[i-1][j-k][0]);
                        }
                    }
                }
            }
        }
        best = Math.max(Math.max(max[commands.length][n][0], max[commands.length][n][1]),Math.abs(Math.min(min[commands.length][n][0], min[commands.length][n][1])));
        out.println(best);
    }
    public static void main(String[] args) throws NumberFormatException, IOException 
    {
        new E();
    }
    String nextToken() throws IOException
    {
        if(tok ==null || !tok.hasMoreTokens()) tok = new StringTokenizer(in.readLine());
        return tok.nextToken();
    }
    int nextInt() throws NumberFormatException, IOException
    {
        return Integer.parseInt(nextToken());
    }
    long nextLong() throws NumberFormatException, IOException
    {
        return Long.parseLong(nextToken());
    }
    double nextDouble() throws NumberFormatException, IOException
    {
        return Double.parseDouble(nextToken());
    }
}
