import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class boxers
{
   static int n;

   public static void main(String[] args) throws Exception
   {
      BufferedReader f = new BufferedReader(new InputStreamReader(System.in));
      PrintWriter outf = new PrintWriter(new OutputStreamWriter(System.out));
      StringTokenizer st = new StringTokenizer(f.readLine());
      n = Integer.parseInt(st.nextToken());

      int[] weights = new int[n];
      st = new StringTokenizer(f.readLine());
      for (int i = 0; i < n; i++)
      {
         weights[i] = Integer.parseInt(st.nextToken());
      }

      Arrays.sort(weights);
      boolean[] seen = new boolean[150002]; // will include 1 to n + 1
      int numDuplicates = 0;
      seen[weights[0]] = true;

      for (int i = 1; i < n; i++)
      {
         seen[weights[i]] = true;
         if (weights[i] == weights[i - 1])
         {
            numDuplicates++;
//            System.out.print(weights[i] + " ");
         }
         else
         {
            numDuplicates = 0;
//            System.out.println();
         }

         if (numDuplicates == 1)
         {
            if (!seen[weights[i] + 1])
            {
               seen[weights[i] + 1] = true;
            }
            else
            {
               seen[weights[i] - 1] = true;
            }
         }
         else if (numDuplicates > 1)
         {
            seen[weights[i] + 1] = true;
            seen[weights[i] - 1] = true;
         }
      }

      int boxers = 0;
      for (int i = 1; i <= 150001; i++)
      {
         if (seen[i])
         {
            boxers++;
         }
      }

      outf.println(boxers);
      f.close();
      outf.close();
   }
}
