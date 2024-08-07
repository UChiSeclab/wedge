/*
If you want to aim high, aim high
Don't let that studying and grades consume you
Just live life young
******************************
If I'm the sun, you're the moon
Because when I go up, you go down
*******************************
I'm waiting for the day I beat the shit outta you
*/
import java.util.*;
import java.io.*;
import java.math.*;

   public class x1272E
   {
      public static void main(String omkar[]) throws Exception
      {
         //BufferedReader infile = new BufferedReader(new FileReader("cowdate.in"));
         BufferedReader infile = new BufferedReader(new InputStreamReader(System.in));  
         StringTokenizer st = new StringTokenizer(infile.readLine());
         int N = Integer.parseInt(st.nextToken());
         StringBuilder sb = new StringBuilder();
         int[] arr = new int[N];
         st = new StringTokenizer(infile.readLine());
         for(int i=0; i < N; i++)
            arr[i] = Integer.parseInt(st.nextToken());
         LinkedList<Integer>[] edges = new LinkedList[N];
         for(int i=0; i < N; i++)
            edges[i] = new LinkedList<Integer>();
         for(int i=0; i < N; i++)
         {
            int left = i-arr[i];
            if(left >= 0)
               edges[left].add(i);
            int right = i+arr[i];
            if(right < N)
               edges[right].add(i);
         }
         //even -> even -> odd (backtracking hard bc of cycles)
         //even <- even <- odd (build from the answer bc it's easy)
         int[] res = new int[N];
         Arrays.fill(res, -1);
         int[] d = new int[N];
         Arrays.fill(d, Integer.MAX_VALUE);
         Queue<Integer> q = new LinkedList<Integer>();
         for(int i=0; i < N; i++)
            if(arr[i]%2 == 0)
            {
               d[i] = 0;
               q.add(i);
            }
         while(q.size() > 0)
         {
            int curr = q.poll();
            for(int next: edges[curr])
               if(d[next] > d[curr]+1)
               {
                  d[next] = d[curr]+1;
                  q.add(next);
               } 
         }
         for(int i=0; i < N; i++)
            if(d[i] != Integer.MAX_VALUE && d[i] != 0)
               res[i] = d[i];
         d = new int[N];
         Arrays.fill(d, Integer.MAX_VALUE);
         q = new LinkedList<Integer>();
         for(int i=0; i < N; i++)
            if(arr[i]%2 == 1)
            {
               d[i] = 0;
               q.add(i);
            }
         while(q.size() > 0)
         {
            int curr = q.poll();
            for(int next: edges[curr])
               if(d[next] > d[curr]+1)
               {
                  d[next] = d[curr]+1;
                  q.add(next);
               } 
         }
         for(int i=0; i < N; i++)
            if(d[i] != Integer.MAX_VALUE && d[i] != 0)
               res[i] = d[i];
         for(int x: res)
            sb.append(x+" ");
         System.out.println(sb);
      }
   }