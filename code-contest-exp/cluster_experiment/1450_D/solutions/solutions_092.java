//Praise our lord and saviour qlf9
import java.util.*;
import java.io.*;
import java.math.*;
public class D{
public static void main(String[] omkar) throws Exception
{
   BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
   StringTokenizer st = new StringTokenizer(in.readLine());
   StringBuilder sb = new StringBuilder();
   int cases = Integer.parseInt(st.nextToken());
   for(int i = 0; i < cases; i++)
   {
      solve(in, st, sb);
   }  
   System.out.println(sb);
        }
 public static void solve(BufferedReader in, StringTokenizer st, StringBuilder sb) throws Exception
 {
  st = new StringTokenizer(in.readLine());
  int n = Integer.parseInt(st.nextToken());
  int[] bool =  new int[n];
  int[] arr = readArr(n, in, st);
  PriorityQueue<Pair> que = new PriorityQueue<Pair>();
  int[] rights = new int[n];
  int[] lefts = new int[n];
  Pair temp;
  for(int i = 0; i < n; i++)
  {
      while(que.size() > 0 && que.peek().a > arr[i])
      {
         temp = que.poll();
         rights[temp.b] = i-1;
      }
      que.add(new Pair(arr[i], i));
  }
  while(que.size() > 0)
      {
         temp = que.poll();
         rights[temp.b] = n-1;
      }
      for(int i = n-1; i >= 0; i--)
  {
      while(que.size() > 0 && que.peek().a > arr[i])
      {
         temp = que.poll();
         lefts[temp.b] = i+1;
      }
      que.add(new Pair(arr[i], i));
  }
  while(que.size() > 0)
      {
         temp = que.poll();
         lefts[temp.b] = 0;
      }
  for(int i = 0; i < n; i++)
  {
      if(arr[i] > 0 && arr[i] <= n)
      {
         bool[arr[i]-1] = Math.max(bool[arr[i]-1], rights[i]-lefts[i]+1);
      }
  }
  int min = bool[0];
  int[] ret = new int[n];
  for(int i = 0; i < n; i++)
  {
      min = Math.min(bool[i], min);
      if(min >= n-i)
      {
         ret[n-1-i] = 1;
      }
      else
      {
         ret[n-1-i] = 0;
      }
  }
  for(int i = 0; i < n; i++)
  {
   sb.append(ret[i]+"");
  }
  sb.append("\n");
 }
 public static int[] readArr(int N, BufferedReader in, StringTokenizer st) throws Exception
      {
         int[] arr = new int[N];
         st = new StringTokenizer(in.readLine());
         for(int i=0; i < N; i++)
            arr[i] = Integer.parseInt(st.nextToken());
         return arr;
      }
static class Pair implements Comparable<Pair> {
int a;
int b;
Pair(int a, int b) {
this.a = a;
this.b = b;
}
@Override
public int compareTo(Pair other) {
if (a != other.a) {
return other.a-a;
} else {
return other.b-b;
}}}


}