import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class ques3 {
    static class FastReader {
        BufferedReader br;
        StringTokenizer st;

        public FastReader() {
            br = new BufferedReader(new InputStreamReader(System.in));
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

    public static void main(String[] args) throws NumberFormatException, IOException {
        FastReader scn = new FastReader();
        long t = scn.nextInt();
        for (int i = 0; i < t; i++) {
            int n=scn.nextInt();
            String s=scn.nextLine();
            HashMap<Double,Integer> hm=new HashMap<>();
            double ratio=0;
            int[] countk=new int[n];
            int[] countd=new int[n];
            if(s.charAt(0)=='D'){
                countd[0]=1;
            }
            else{
                countk[0]=1;
            }
            for(int j=1;j<n;j++){
                if(s.charAt(j)=='D'){
                    countd[j]=countd[j-1]+1;
                }
                else{
                    countk[j]=countk[j]+1;
                }
            }
            for(int j=0;j<n;j++){
                int ans=0;
                if(countk[j]==0||countd[j]==0){
                    ans=Math.max(countk[j],countd[j]); 
                }
                else {
                    ratio=(double)countd[j]/(double)countk[j];
                    if(hm.containsKey(ratio)){
                        ans=hm.get(ratio);
                    }
                    hm.put(ratio,ans+1);
                    ans++;
                }
                System.out.print(ans+" ");   
            }
            System.out.println();

        }
    }
}
