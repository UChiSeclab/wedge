import java.io.*;
import java.util.*;

public class A {
    static class FastReader {
        BufferedReader br;
        StringTokenizer st;

        public FastReader() {
            br = new BufferedReader(new
                    InputStreamReader(System.in));
        }

        String next() {
            while (st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
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
                str = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return str;
        }
    }

    static FastReader s = new FastReader();
    static PrintWriter out = new PrintWriter(System.out);

    private static int[] rai(int n) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = s.nextInt();
        }
        return arr;
    }

    private static int[][] rai(int n, int m) {
        int[][] arr = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                arr[i][j] = s.nextInt();
            }
        }
        return arr;
    }

    private static long[] ral(int n) {
        long[] arr = new long[n];
        for (int i = 0; i < n; i++) {
            arr[i] = s.nextLong();
        }
        return arr;
    }

    private static long[][] ral(int n, int m) {
        long[][] arr = new long[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                arr[i][j] = s.nextLong();
            }
        }
        return arr;
    }

    private static int ri() {
        return s.nextInt();
    }

    private static long rl() {
        return s.nextLong();
    }

    private static String rs() {
        return s.next();
    }
    static int gcd(int a,int b)
    {
        if(b==0)
        {
            return a;
        }
        return gcd(b,a%b);
    }


    public static void main(String[] args) {
        StringBuilder ans = new StringBuilder();
//        int t = ri();
        int t=1;
        while (t-- > 0)
        {
            int n=ri();
            int[] arr=rai(n);

            int curr=-1;
            int c1=0;
            int c2=0;
            int res=0;
            for(int i=0;i<n;i++)
            {
                if(curr==-1)
                {
                    if(arr[i]==1)
                    {
                        curr=1;
                        c1++;
                    }
                    else {
                        curr=2;
                        c2++;
                    }
                }
                else {
                    if(curr==2)
                    {
                        if(arr[i]==1)
                        {
                            res=Math.max(res,Math.min(c2,c1)*2);
                            c1=0;
                            c1++;
                            curr=1;
                        }
                        else
                        {
                            c2++;
                        }
                    }
                    else if(curr==1)
                    {
                        if(arr[i]==1)
                        {
                            c1++;

                        }
                        else
                        {
                            curr=2;
                            res=Math.max(res,Math.min(c2,c1)*2);
                            c2=0;
                            c2++;
                        }
                    }
                }

//                System.out.println(i+" "+c1+" "+c2);
//                System.out.println("res= "+res);
            }
            res=Math.max(res,Math.min(c2,c1)*2);
            ans.append(res).append("\n");
        }

        out.print(ans.toString());
        out.flush();

    }

}
