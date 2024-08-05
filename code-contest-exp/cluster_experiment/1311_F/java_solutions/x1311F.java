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

   public class x1311F
   {
      public static void main(String omkar[]) throws Exception
      {
         BufferedReader infile = new BufferedReader(new InputStreamReader(System.in));  
         StringTokenizer st = new StringTokenizer(infile.readLine());
         int N = Integer.parseInt(st.nextToken());
         int[] pos = new int[N];
         st = new StringTokenizer(infile.readLine());
         for(int i=0; i < N; i++)
            pos[i] = Integer.parseInt(st.nextToken());
         int[] vel = new int[N];
         st = new StringTokenizer(infile.readLine());
         for(int i=0; i < N; i++)
            vel[i] = Integer.parseInt(st.nextToken());
         //int maxVel = compress(vel);
         //sort by position
         //count how many velocities are greater
         Point[] arr = new Point[N];
         for(int i=0; i < N; i++)
            arr[i] = new Point(pos[i], vel[i]);
         Arrays.sort(arr);
         sort(pos);
         HashMap<Integer,Integer> map = new HashMap<Integer,Integer>();
         long res = 0L;
         for(int k=0; k < N; k++)
         {
            map.put(pos[k],k);
            res += (long)pos[k]*(long)(-1*N+1+2*k);
         }
         for(int k=0; k < N; k++)
         {
            if(k < map.get(arr[k].pos))
               res -= (long)arr[k].pos*(long)(map.get(arr[k].pos)-k);
            else if(k > map.get(arr[k].pos))
               res += (long)arr[k].pos*(long)(k-map.get(arr[k].pos));
         }
         System.out.println(res);
      }
      public static int compress(int[] vel)
      {
         ArrayList<Integer> ls = new ArrayList<Integer>();
         for(int x: vel)
            ls.add(x);
         Collections.sort(ls);
         HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
         int boof = 1;
         for(int x: ls)
            if(!map.containsKey(x))
               map.put(x, boof++);
         for(int i=0; i < vel.length; i++)
            vel[i] = map.get(vel[i]);
         return boof-1;
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
   class Point implements Comparable<Point>
   {
      public int pos;
      public int vel;
      
      public Point(int a, int b)
      {
         pos = a;
         vel = b;
      }
      public int compareTo(Point oth)
      {
         if(vel != oth.vel)
            return vel-oth.vel;
         return pos-oth.pos;
      }
   }