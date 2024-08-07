/*
If you want to aim high, aim high
Don't let that studying and grades consume you
Just live life young
******************************
If I'm the sun, you're the moon
Because when I go up, you go down
*******************************
I'm working for the day I will surpass you
*/
import java.util.*;
import java.io.*;
import java.math.*;

   public class F
   {
      public static void main(String omkar[]) throws Exception
      {
         BufferedReader infile = new BufferedReader(new InputStreamReader(System.in));  
         StringTokenizer st = new StringTokenizer(infile.readLine());
         int N = Integer.parseInt(st.nextToken());
         int[] post = readArr(N, infile, st);
         int[] vel = readArr(N, infile, st);
         //compress vel
         int boof = compress(vel);
         Point[] arr = new Point[N];
         for(int i=0; i < N; i++)
            arr[i] = new Point(post[i], vel[i]);
         //sort by location
         Arrays.sort(arr);
         FenwickTree pos = new FenwickTree(boof+2);
         FenwickTree pc = new FenwickTree(boof+2);
         FenwickTree neg = new FenwickTree(boof+2);
         FenwickTree nc = new FenwickTree(boof+2);
         long res = 0L;
         for(Point p: arr)
         {
            if(p.vel < 0)
            {
               long temp = nc.find((int)p.vel, boof)*p.pos;
               temp -= neg.find((int)p.vel, boof);
               temp += pos.find(1, boof)-pc.find((int)p.vel, boof)*p.pos;
               nc.add((int)p.pos, 1);
               neg.add((int)p.vel, p.pos);
               res += temp;
            }
            else
            {
               long temp = pc.find((int)p.vel, boof)*p.pos;
               temp -= pos.find((int)p.vel, boof);
               temp += neg.find(1, boof)-nc.find((int)p.vel, boof)*p.pos;
               pc.add((int)p.pos, 1);
               pos.add((int)p.vel, p.pos);
               res += temp;
            }
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
      public static int[] readArr(int N, BufferedReader infile, StringTokenizer st) throws Exception
      {
         int[] arr = new int[N];
         st = new StringTokenizer(infile.readLine());
         for(int i=0; i < N; i++)
            arr[i] = Integer.parseInt(st.nextToken());
         return arr;
      }
   }
   class Point implements Comparable<Point>
   {
      public long pos;
      public long vel;
      
      public Point(long a, long b)
      {
         pos = a;
         vel = b;
      }
      public int compareTo(Point oth)
      {
         /*
         if(oth.vel == vel)
            return (int)(pos-oth.pos);
         return (int)(vel-oth.vel);
         */
         return (int)(vel-oth.vel);
      }
   }
   class FenwickTree
   {
      //1 indexed
      public long[] tree;
      public int size;
      
      public FenwickTree(int size)
      {
         this.size = size;
         tree = new long[size+5];
      }
      public void add(int i, long v)
      {
         while(i <= size)
         {
            tree[i] += v;
            i += i&-i;
         }
      }
      public long find(int i)
      {
         long res = 0L;
         while(i >= 1)
         {
            res += tree[i];
            i -= i&-i;
         }
         return res;
      }
      public long find(int l, int r)
      {
         return find(r)-find(l-1);
      }
   }