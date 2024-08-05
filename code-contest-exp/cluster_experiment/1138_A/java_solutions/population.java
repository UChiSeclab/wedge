import java.util.*;
import java.io.*;
import java.math.*;
public class population
{
    
    static long power(long x, long y) 
    { 
    // Initialize result 
    long res = 1;  
  
    while (y > 0)  
    { 
        // If y is odd,  
        // multiply 
        // x with result 
        if ((y & 1) == 1) 
            res = res * x; 
  
        // n must be even now 
        y = y >> 1; // y = y/2 
        x = x * x; // Change x to x^2 
    } 
    return res; 
    } 
  
    public static void main(String ag[])
    {
        FastReader sc=new FastReader();
        
        
        /*ArrayList<Integer> al=new ArrayList<Integer>();*/

        /*String s=sc.nextLine();*/
        /*String[] arrOfStr = s.split(" "); */
        /*boolean visited[]=new boolean[arrOfStr.length];*/
     int i,j,l;
     
     int n=sc.nextInt();
     int arr[]=new int[n];
     for(i=0;i<n;i++)
     {
         arr[i]=sc.nextInt();
     }
int count2=0;
int count1=0;
int sum=0;
boolean flag=false;

     for(i=0;i<n;i++)
     {
         
         if(i!=0&&(arr[i])!=arr[i-1]&&(count1!=0&&count2!=0))
         {//System.out.println(count1+" "+count2);
             sum=Math.max(sum,2*Math.min(count1,count2));
           //  System.out.println(sum);
             if(arr[i]==2)
             count2=0;
             else 
             count1=0;
         }
         if(arr[i]==2)
         {
             count2++;
             
         }
         if(arr[i]==1)
         {
             count1++;
         }

     }
     sum=Math.max(sum,2*Math.min(count1,count2));
     System.out.println(sum);
    }  
    
  
       
}
class graph
{
    int numVertices;
    
    ArrayList<ArrayList<Integer>> adjLists;
 
    graph(int vertices)
    {
        numVertices = vertices;
        adjLists=new ArrayList<>();       
        for (int i = 0; i <=numVertices; i++)
        adjLists.add(i,new ArrayList<Integer>());
    }
 

public void addEdge(int src, int dest)
{
    
    adjLists.get(src).add(dest);
    adjLists.get(dest).add(src);    
    
    
}


public void DFSUtil(int v,boolean visited[]) 
{ 
  
        visited[v] = true; 
  
  
    
 
   
    Iterator<Integer> i = adjLists.get(v).listIterator(); 
    while (i.hasNext()) 
    { 
       
        int n = i.next(); 
       
        if (!visited[n]) 
        {
          DFSUtil(n, visited);    
        }
       
    } 
} 
  
    

public void dfs(Node arr[]) 
{ 
    
    
    boolean visited[] = new boolean[numVertices+1]; 
 
    for(int i=0;i<numVertices;i++)
    {
    
    if(visited[i]==false)
    {
         DFSUtil(i,visited); 
        
    }
      
    }
   
   
} 
}

class FastReader {

    BufferedReader bf;
    StringTokenizer st;

    public FastReader() {
        bf = new BufferedReader(new InputStreamReader(System.in));
    }

    String next() {
        while (st == null || !st.hasMoreElements()) {
            try {
                st = new StringTokenizer(bf.readLine());
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return st.nextToken();
    }

    int nextInt() {
        return Integer.parseInt(next());
    }

    long nextLong() {
        return Long.parseLong(next());
    }

    double nextDouble() {
        return Double.parseDouble(next());
    }

    String nextLine() {
        String str = "";
        try {
            str = bf.readLine();
        } catch (Exception e) {
            System.out.println(e);
        }
        return str;
    }
}

class Tcomp implements Comparator<Node>
{
 
    
    @Override
    public int compare(Node e1, Node e2) {
        if(e1.getDist()<e2.getDist()){
            return 1;
        } else {
            return -1;
        }
    }
}
class Node
{
    int a;
    int b;
    int dist;
    

    Node(int a,int b,int dist)
    {
        this.a=a;
        this.b=b;
        this.dist=dist;
        
    }
    
    

    public int getA()
    {
        return this.a;
    }
    public int getB()
    {
        return this.b;
    }
    public int getDist()
    {
        return this.dist;
    }
    
    
    

}

/*for(int i=0;i<arrOfStr.length;i++)
        {
            String str=arrOfStr[i].toUpperCase();
            
            if(hm.containsKey(str))
            {
                int z=hm.get(str);
                hm.put(str,z+1);
            
            }
            else
            hm.put(str,1);
        }  


        for(int i=0;i<arrOfStr.length;i++)
        {
            String x=arrOfStr[i].toUpperCase();
            if(d[i]==false)
            {
                int y=hm.get(x);
                for(int j=0;j<d.length;j++)
                {
                    if(arrOfStr[j].equalsIgnoreCase(x))
                    d[j]=true;
                }
                System.out.println(x.toUpperCase()+" "+y);

            }
        }
*/






