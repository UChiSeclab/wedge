  import java.util.*;
  import java.io.*;
  public class Appyandballon
    {
        static BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        static BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(System.out));
        static StringTokenizer st=null;
        static String next()
        {
            while(st==null || !st.hasMoreElements())
            {
                try{
                    st=new StringTokenizer(br.readLine());
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }
        static int nextInt()
        {
            return Integer.parseInt(next());
        }
        static long nextLong()
        {
            return Long.parseLong(next());
        }
        
        static double nextDouble(){
            return Double.parseDouble(next());
        }
       public static void main(String[] args) {
            try{
                int n=nextInt();
                int t[]=new int[n+1];
                for(int i=1;i<=n;i++)
                {
                    t[i]=nextInt();
                }
                int c1=0;
                int c2=0;
                int pr=t[1];
                if(pr==1)
                {
                    c1++;
                }
                else
                {
                    c2++;
                }
                int max=0;
                for(int i=2;i<=n;i++)
                {
                    if(t[i]==pr)
                    {
                        if(t[i]==1)
                        {
                            c1++;
                        }
                        else
                        {
                            c2++;
                        }
                    }
                    else
                    {
                      //  bw.write(c1+" "+c2+"\n");
                        max=Math.min(c1,c2);
                        if(t[i]==1)
                        {
                            c1=1;
                        }
                        else
                        {
                            c2=1;
                        }
                        pr=t[i];
                    }
                }
                max=Math.min(c1,c2);
                bw.write((2*max)+"\n");
    		bw.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }  
