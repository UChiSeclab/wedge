import java.io.*;
import java.util.*;
 
public class Numbers
{
      public static void main(String[] args) throws IOException 
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
	      
            int n, maxTuna = 0, crrTuna = 0, maxEel = 0, crrEel = 0 , maxContinuous = 0; 
            n = Integer.parseInt(st.nextToken());
            st = new StringTokenizer(br.readLine());
            for(int i=0; i<n; i++)
            {
                  int sushi = Integer.parseInt(st.nextToken());
                  if(sushi == 1) 
                  {
                       crrTuna++; 
                       crrEel = 0;
                  }
                  else
                  {
                      crrEel++;  
                      crrTuna = 0;
                  }
                  if(crrTuna > maxTuna) maxTuna = crrTuna;
                  if(crrEel > maxEel) maxEel = crrEel;
                  maxContinuous = Math.min(maxTuna,maxEel);
            }
            System.out.println(maxContinuous * 2);
            
	}
}