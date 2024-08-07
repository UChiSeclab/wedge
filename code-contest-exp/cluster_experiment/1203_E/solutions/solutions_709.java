import java.io.*;
import java.util.*;
import java.util.regex.*;
public class vk18
{
    public static void main(String[]stp) throws Exception
    {
    	Scanner scan=new Scanner(System.in);
    	PrintWriter pw = new PrintWriter(System.out);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st=new StringTokenizer(br.readLine());
        int n=Integer.parseInt(st.nextToken()),i,count=0;
        st=new StringTokenizer(br.readLine());
        Integer []fre=new Integer[1500002];
        Arrays.fill(fre,0);
        for(i=1;i<=n;i++) fre[Integer.parseInt(st.nextToken())]++;
        for(i=1;i<=150000;i++)
        {
        	if(i==1 && fre[1] >=1) 
        	{   fre[1]--;  fre[2]++;  continue; }
        	if(fre[i] >=1 && fre[i-1] == 0) { fre[i]--; fre[i-1]++; }
        	if(fre[i] > 1) { fre[i]--; fre[i+1]++; }
        }
        for(i=1;i<150002;i++) { if(fre[i] > 0) count++; }
        pw.print(count);
    	pw.flush();
   	}

}