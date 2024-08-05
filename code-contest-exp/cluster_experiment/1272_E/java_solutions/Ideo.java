import java.io.BufferedReader; 
import java.io.IOException; 
import java.io.InputStreamReader; 
import java.util.*; 
import java.util.StringTokenizer; 
import java.io.PrintWriter;
import java.io.*;

import java.util.stream.Collectors.*;
import java.lang.*;
import static java.util.stream.Collectors.*;
import static java.util.Map.Entry.*;
public class Ideo
{ 
    static class FastReader 
    { 
        BufferedReader br; 
        StringTokenizer st; 
  
        public FastReader() 
        { 
            br = new BufferedReader(new
                     InputStreamReader(System.in)); 
        } 
  
        String next() 
        { 
            while (st == null || !st.hasMoreElements()) 
            { 
                try
                { 
                    st = new StringTokenizer(br.readLine()); 
                } 
                catch (IOException  e) 
                { 
                    e.printStackTrace(); 
                } 
            } 
            return st.nextToken(); 
        } 
  
        int nextInt() 
        { 
            return Integer.parseInt(next()); 
        } 
  
        long nextLong() 
        { 
            return Long.parseLong(next()); 
        } 
  
        double nextDouble() 
        { 
            return Double.parseDouble(next()); 
        } 
  
        String nextLine() 
        { 
            String str = ""; 
            try
            { 
                str = br.readLine(); 
            } 
            catch (IOException e) 
            { 
                e.printStackTrace(); 
            } 
            return str; 
        } 
    }
   
static class aksh
{
    
    int x;
    int y;
    int moves;

    public aksh(int a,int b,int c) 
    {
        x = a;
        y = b;
        moves=c;
    }
}
static int power(int x,int y) 
{ 
    int res = 1;      // Initialize result 
  
      // Update x if it is more than or  
                // equal to p 
  
    while (y > 0) 
    { 
        // If y is odd, multiply x with result 
        if (y%2==1) 
            res = (res*x); 
  
        // y must be even now 
        y = y>>1; // y = y/2 
        x = (x*x);   
    } 
    return res; 
} 
 
 static int gcd(int a, int b) 
    { 
        if (a == 0) 
            return b; 
        return gcd(b % a, a); 
    } 


  static boolean compareSeq(char[] S, int x, int y, int n) 
    { 
        for (int i = 0; i < n; i++)
         {

            if (S[x] < S[y]) 
               return true;
            else if (S[x] > S[y]) 
                return false; 
            x = (x + 1) % n; 
            y = (y + 1) % n; 
        } 
        return true; 
    } 

    static void build(long[] sum,int[] arr,int n)
    {
      for(int i=0;i<(1<<n);i++)
      {
        long total=0;
        for(int j=0;j<n;j++)
        {
          if((i & (1 << j)) > 0)
            total+=arr[j];
        }

        sum[i]=total;
      }
    }

 
     static int count(long arr[], long x, int n) 
       {

        int l=0;
        int h=n-1;

        int res=-1;
        int mid=-1;
        while(l<=h)
        {
          mid=l+(h-l)/2;
          if(x==arr[mid])
          {
            res=mid;
            h=mid-1;
          }
          else if(x<arr[mid])
            h=mid-1;
          else
            l=mid+1;
        }

        if(res==-1)
          return 0;

        //res is first index and res1 is last index of an element in a sorted array total number of occurences is (res1-res+1)

        int res1=-1;
        l=0;
        h=n-1;
        while(l<=h)
        {
          mid=l+(h-l)/2;
          if(x==arr[mid])
          {
            res1=mid;
            l=mid+1;
          }
          else if(x<arr[mid])
            h=mid-1;
          else
            l=mid+1;
        }



        if(res1==-1)
          return 0;
        if(res!=-1 && res1!=-1)
          return (res1-res+1);

        return 0;

       } 

       static int parity(int a)
       {
    a^=a>>16;
    a^=a>>8;
    a^=a>>4;
    a^=a>>2;
    a^=a>>1;
    return a&1;
  }
/*
  PriorityQueue<aksh> pq = new PriorityQueue<>((o1, o2) -> {
            if (o1.p < o2.p)
                return 1;
            else if (o1.p > o2.p)
                return -1;
            else
                return 0;

        });//decreasing order acc to p*/
static int power(int x, int y, int m)  
    { 
        if (y == 0) 
            return 1; 
          
        int p = power(x, y / 2, m) % m; 
        p = (p * p) % m; 
      
        if (y % 2 == 0) 
            return p; 
        else
            return (x * p) % m; 
    } 
static int modinv(int a, int m) 
    { 
        int g = gcd(a, m); 
        if (g != 1) 
            return 0;
        else 
        { 
           return power(a, m - 2, m); 
        } 
        //return 0;
    } 

static int[] product(int[] nums) {
    int[] result = new int[nums.length];
 
    int[] t1 = new int[nums.length];
    int[] t2 = new int[nums.length];
 
    t1[0]=1;
    t2[nums.length-1]=1;
 
    //scan from left to right
    for(int i=0; i<nums.length-1; i++){
        t1[i+1] = nums[i] * t1[i];
    }
 
    //scan from right to left
    for(int i=nums.length-1; i>0; i--){
        t2[i-1] = t2[i] * nums[i];
    }

    for(int i=0;i<nums.length;i++)
      {
        System.out.print(t1[i]+" "+t2[i]);
        System.out.println();
      }
 
    //multiply
    for(int i=0; i<nums.length; i++){
        result[i] = t1[i] * t2[i];
    }
 
    return result;
}

static int getsum(int[] bit,int ind)
{
  int sum=0;
  while(ind>0)
  {
    sum+=bit[ind];
    ind-= ind & (-ind);
  }

  return sum;
}

static void update(int[] bit,int max,int ind,int val)
{
  while(ind<=max)
  {
    bit[ind]+=val;
    ind+= ind & (-ind);
  }
}

    static ArrayList<Integer>[] adj;
    static int[] bfs(ArrayList<Integer>[] adj,int n,ArrayList<Integer> arr)
    {
      int[] vis=new int[n];
      int[] dis=new int[n];
      int[] par=new int[n];
      Queue<Integer> q=new LinkedList<>();

      Arrays.fill(dis,-1);

      for(int i=0;i<arr.size();i++)
      {
        vis[arr.get(i)]=1;
        dis[arr.get(i)]=0;
        par[arr.get(i)]=-1;
        q.add(arr.get(i));
      }

      while(!q.isEmpty())
      {
        int x=q.remove();
        for(int u:adj[x])
        {
          if(vis[u]==0)
          {
            vis[u]=1;
            dis[u]=dis[x]+1;
            par[u]=x;
            q.add(u);
          }
        }
      }

      return dis;


    }
    public static void main(String args[] ) throws Exception 
    {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT */

         FastReader sc=new FastReader();

         int n=sc.nextInt();
         ArrayList<Integer> odd=new ArrayList<>();
         ArrayList<Integer> even=new ArrayList<>();
         adj=new ArrayList[n];

         for(int i=0;i<n;i++)
          adj[i]=new ArrayList<Integer>();
        
         for(int i=0;i<n;i++)
         {
          int x=sc.nextInt();
          if(x%2==0)
            even.add(i);
          else
            odd.add(i);

          if(i-x>=0)
            adj[i-x].add(i);
          if(i+x<n)
            adj[i+x].add(i);
         }

         int[] ans=new int[n];
         int[] dis=bfs(adj,n,odd);

         for(int i=0;i<even.size();i++)
          ans[even.get(i)]=dis[even.get(i)];

         dis=bfs(adj,n,even);

         for(int i=0;i<odd.size();i++)
          ans[odd.get(i)]=dis[odd.get(i)];


        for(int i=0;i<n;i++)
          System.out.print(ans[i]+" ");

      }

    }


