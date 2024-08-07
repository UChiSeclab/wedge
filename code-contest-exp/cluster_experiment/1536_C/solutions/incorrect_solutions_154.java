import java.util.*;
import java.io.*;
 
public class SolutionC{
       public static void main(String[] args) throws Exception{
               Fast sc=new Fast();
       StringBuilder sb=new StringBuilder();
        PrintWriter out=new PrintWriter(System.out);
         int t=sc.nextInt();
    while(t-->0){
            int n=sc.nextInt();
            char[] c=sc.next().toCharArray();
            int[] d=new int[n+1];
            int[] k=new int[n+1];
            int dd=0;
            int kk=0;
            for(int i=0;i<n;i++){
              if(c[i]=='D')dd++;
              if(c[i]=='K')kk++;
              d[i+1]=dd;
              k[i+1]=kk;
            }
            int[] dp=new int[n+1];
            Arrays.fill(dp,1);
            for(int i=1;i<=n;i++){
                 int cnt=1;
                for(int j=i+i;j<=n;j+=i){
                  if(d[j]-d[j-i]==d[i] && k[j]-k[j-i]==k[i] ){
                    dp[j]=Math.max(cnt+1,dp[j]);
                    cnt++;
                  }
                  else break;
                }
            }
            for(int i=1;i<=n;i++)out.print(dp[i]+" ");

            out.println();

       }
     out.close();
}

static void ReverSort(int[] ar){
    ArrayList<Integer> al=new ArrayList<>();
    for(int i=0;i<ar.length;i++){
        al.add(ar[i]);
    }

    Collections.sort(al);
      int j=0;
    for(int i=al.size()-1;i>=0;i--){

        ar[j]=al.get(i);
        j++;
    }
}
static void Sort(int[] ar){
    ArrayList<Integer> al=new ArrayList<>();
    for(int i=0;i<ar.length;i++){
        al.add(ar[i]);
    }

    Collections.sort(al);
      int j=0;
    for(int i=0;i<al.size();i++){

        ar[j]=al.get(i);
        j++;
     }
}
static int gcd(int a,int b){
    if(b==0) return a;
    else return  gcd(b,a%b);
}
}
class Pair{
    int x, y;
     Pair(int x,int y){
        this.x=x;
        this.y=y;
     }
}
class Fast{
  BufferedReader br; 
  StringTokenizer st;
  public Fast(){ br=new BufferedReader(new InputStreamReader(System.in)); }
  String next() 
        {  while (st == null || !st.hasMoreElements()) 
            {   try
                {     st = new StringTokenizer(br.readLine()); } 
                catch (IOException  e) 
                {    e.printStackTrace(); } 
            } 
            return st.nextToken(); 
        }
  int nextInt(){  return Integer.parseInt(next()); }
  long nextLong(){     return Long.parseLong(next()); } 
  double nextDouble() {     return Double.parseDouble(next()); } 
  String nextLine() 
        { 
            String str = ""; 
            try{      str = br.readLine(); } 
            catch (IOException e)  {  e.printStackTrace(); } 
            return str; 
        } 
}
    