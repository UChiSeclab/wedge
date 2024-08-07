import java.io.*;
import java.util.*;

public class Main
{
    public static void main(String[] args) throws IOException
    {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        TreeSet<String> tr=new TreeSet<String>();
        StringBuffer input = new StringBuffer(in.readLine());
        int k = new Integer(in.readLine());
        long count=0;
        for (int i=0; i<input.length(); i++)
        {
            for (int j=i; j<input.length();j++)
            {
                tr.add(input.substring(i,j+1));
                
            }
        }
        if (k>tr.size()) out.print("No such line.");
        else
        {
            out.print(tr.toArray()[k-1]);
        }
        out.close();
    }
}