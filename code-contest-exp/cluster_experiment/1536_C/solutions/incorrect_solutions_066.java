import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class qwe {
    static int p = 1000000007;
    static long power(long x, long y)//(x^y)%p
    {


        long res = 1;

        x = x % p;

        while (y > 0) {
            if (y % 2 == 1)
                res = (res * x) % p;
            y = y >> 1;
            x = (x * x) % p;
        }

        return res;
    }




    static int gcd(int a, int b)
    {
        return (a % b == 0) ?
                Math.abs(b) : gcd(b,a%b);
    }
    static class pair
    {
        int first, second;
        public pair(int first, int second)
        {
            this.first = first;
            this.second = second;
        }
    }


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            int t = Integer.parseInt(br.readLine());
            while (t -->0){
                int N = Integer.parseInt(br.readLine());
                String s = br.readLine();

                HashMap<String,Integer> map = new HashMap<>();
                int[] ans = new int[N];
                int d = 0, k = 0;
                for(int i=0;i<N;i++){
                    if(s.charAt(i) == 'D')
                        d++;
                    else
                        k++;
                    int x=d,y=k;
                    if(x==0)
                        y=1;
                    else if(y==0)
                        x=1;

                    else{

                        int  g = gcd(x,y);
                        x/=g;
                        y/=g;

                    }
                    String p = x+" "+y;
                    if(map.containsKey(p))
                        map.put(p,map.get(p)+1);
                    else
                        map.put(p,1);

                    ans[i] = map.get(p);

                }
                System.out.println(Arrays.toString(ans));






         }

        }


        }