// created by 我不知道我是谁
import java.io.*;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.*;
public class cp18{


    static class Reader {
        static BufferedReader reader;
        static StringTokenizer tokenizer;

        static void init(InputStream input) {
            reader = new BufferedReader(
                    new InputStreamReader(input));
            tokenizer = new StringTokenizer("");
        }

        static String next() throws IOException {
            while (!tokenizer.hasMoreTokens()) {
                //TODO add check for eof if necessary
                tokenizer = new StringTokenizer(
                        reader.readLine());
            }
            return tokenizer.nextToken();
        }

        static int nextInt() throws IOException {
            return Integer.parseInt(next());
        }

        static double nextDouble() throws IOException {
            return Double.parseDouble(next());
        }

        static long nextLong() throws IOException {
            return Long.parseLong(next());
        }
    }



    static class Pair{
        int d;
        int k;
        // smallest form only

        public Pair(int d, int k){
            this.d=d;
            this.k=k;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pair pair = (Pair) o;
            return d == pair.d && k == pair.k;
        }

        @Override
        public int hashCode() {
            return Objects.hash(d, k);
        }
    }




    public static void main(String[] args)  throws IOException {
        Reader.init(System.in);
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
        int t= Reader.nextInt();
        for (int tt = 0; tt <t ; tt++) {
            int n= Reader.nextInt();
            String s= Reader.next();
            HashMap<Pair, Integer>map= new HashMap<>();
            int cd=0;
            int ck=0;
            for (int i = 0; i <n ; i++) {
                if(s.charAt(i)=='D'){
                    cd++;
                }
                else{
                    ck++;
                }
                int c1=cd;
                int c2=ck;
                if(cd!=0 && ck!=0){
                    int gcd= gcd(cd,ck);

                    c1/=gcd;
                    c2/=gcd;
                }

                if(cd==0 || ck==0){
                    out.append((i+1)+" ");
                    if(cd==0){
                        map.put(new Pair(0,1),(i+1));
                    }
                    if(ck==0){
                        map.put(new Pair(1,0),i+1);
                    }}
                else{
//                System.out.println(map+" "+c1+" "+c2);
                    if(!map.containsKey(new Pair(c1,c2))){
                        out.append(1+" ");
                        map.put(new Pair(c1,c2),1);
                    }
                    else{
                        out.append((map.get(new Pair(c1,c2))+1)+" ");
                        map.put(new Pair(c1,c2),map.get(new Pair(c1,c2))+1);
                    }
                }
            }
            out.append("\n");




        }
        out.flush();
        out.close();
    }

    static int gcd(int a, int b){
        if(a==0){
            return b;
        }
        else{
            return gcd(b%a,a);
        }
    }

    public static boolean done(ArrayList<Long>arr){
        int x= arr.size();
        Set<Long>set= new HashSet<>();
        for (int i = 0; i <x ; i++) {
            long a= arr.get(i);
            set.add(a);
        }
        boolean done=true;
        for (int i = 0; i <x-1 ; i++) {
            for (int j = i+1; j <x ; j++) {
                long x1= arr.get(i);
                long x2= arr.get(j);
                if(!set.contains(Math.abs(x1-x2))){
                    return false;
                }
            }
        }
        return true;
    }




    static long power( long x, long y, long p)
    {

        long res = 1;


        x = x % p;

        if (x == 0) return 0;

        while (y > 0)
        {

            if((y & 1)==1)
                res = (res * x) % p;


            y = y >> 1;
            x = (x * x) % p;
        }
        return res;
    }


}