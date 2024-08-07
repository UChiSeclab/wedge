/*
If you want to aim high, aim high
Don't let that studying and grades consume you
Just live life young
******************************
If I'm the sun, you're the moon
Because when I go up, you go down
*******************************
I'm working for the day I will surpass you
https://www.a2oj.com/Ladder16.html
*/
import java.util.*;
import java.io.*;
import java.math.*;

   public class E
   {
      public static void main(String omkar[]) throws Exception
      {
         BufferedReader infile = new BufferedReader(new InputStreamReader(System.in));  
         StringTokenizer st = new StringTokenizer(infile.readLine());
         int N = Integer.parseInt(st.nextToken());
         int[] arr = new int[N];
         st = new StringTokenizer(infile.readLine());
         for(int i=0; i < N; i++)
            arr[i] = Integer.parseInt(st.nextToken());
         sort(arr);
         boolean[] marked = new boolean[200000];
         int cnt = 1;
         int val = arr[0];
         if(val > 1)
            marked[val--] = true;
         else
            marked[val] = true;
         for(int i=1; i < N; i++)
         {
            if(arr[i] == val)
            {
               if(!marked[arr[i]])
                  marked[arr[i]] = true;
               else if(!marked[arr[i]+1])
                  marked[arr[i]+1] = true;
               cnt++;
            }
            else
            {
               cnt = 1;
               val = arr[i];
               if(!marked[arr[i]-1])
                  marked[arr[i]-1] = true;
               else
                  marked[arr[i]] = true;
            }
         }
         int res = 0;
         for(int i=0; i < 200000; i++)
            if(marked[i])
               res++;
         System.out.println(res);
      }
      public static void sort(int[] arr)
      {
         //stable heap sort
         PriorityQueue<Integer> pq = new PriorityQueue<Integer>();
         for(int a: arr)
            pq.add(a);
         for(int i=0; i < arr.length; i++)
            arr[i] = pq.poll();
      }
   }