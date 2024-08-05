import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;

public class Contest94_D {

    public static void main(String[] args) throws NumberFormatException,
            IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String s = in.readLine();
        int k = Integer.parseInt(in.readLine());
        int stindex = -1;
        int endindex = -1;
        Suffix_Array.str = s;
        Suffix_Array.SA();
        Suffix_Array.getHeight(s.length());
        Suff [] inp = Suffix_Array.Pos;
        int [] lcps = Suffix_Array.height;
        for (int i = 0; i < s.length(); i++) {
            int len = s.length()-inp[i].idx;
            for (int j = lcps[i]+1; j <= len; j++) {
                if(k==1){
                    System.out.println(s.substring(inp[i].idx,inp[i].idx+j));
                    return;
                }else
                    k--;
                for (int j2 = i+1; j2 < s.length(); j2++) {
                    if(lcps[j2]<j)
                        break;
                    if(k==1){
                        System.out.println(s.substring(inp[i].idx,inp[i].idx+j));
                        return;
                    }else
                        k--;
                }
                //System.out.println(k);
            }
        }
        System.out.println("No such line.");
    }
}



////  N*Log^2(N) method.
// Suffix array is an array of integers giving the 
// starting positions of suffixes of a string in lexicographical order.
class Suff {
    int idx;

    Suff(int index) {
        idx = index;
    }
}

 class Suffix_Array {
    static Suff[] Pos;
    static int Bucket[], tmpBucket[], t;
    static String str;
    static int[] rank;
    static int[] height;

    static Comparator<Suff> cmp = new Comparator<Suff>() {

        public int compare(Suff x, Suff y) {
            if (t == 0)
                return str.charAt(x.idx) - str.charAt(y.idx);

            else {
                if (Bucket[x.idx] == Bucket[y.idx]) {
                    int neI = x.idx + t;
                    int beY = y.idx + t;
                    if (neI >= Bucket.length || beY >= Bucket.length)
                        return beY - neI;
                    return Bucket[neI] - Bucket[beY];
                } else
                    return Bucket[x.idx] - Bucket[y.idx];
            }
        }
    };

    public static void updateBuckets() {
        int id = 0;

        for (int i = 0; i < Pos.length; i++) {
            if (i > 0 && cmp.compare(Pos[i], Pos[i - 1]) != 0)
                id++;

            tmpBucket[Pos[i].idx] = id;
        }
        for (int i = 0; i < tmpBucket.length; i++)
            Bucket[i] = tmpBucket[i];
    }

    public static void SA() {
        Pos = new Suff[str.length()];
        Bucket = new int[str.length()];
        tmpBucket = new int[str.length()];
        for (int i = 0; i < str.length(); i++)
            Pos[i] = new Suff(i);
        t = 0;
        Arrays.sort(Pos, cmp);
        updateBuckets();
        for (t = 1; t < str.length(); t *= 2) {
            Arrays.sort(Pos, cmp);
            updateBuckets();
        }
    }

    // O(N) Method for calculating LCP
    // gets the length of the longest common prefixes between sorted suffixes

    public static void getHeight(int n) {
        rank = new int[n];
        height = new int[n];
        for (int i = 0; i < n; ++i)
            rank[Pos[i].idx] = i;
        height[0] = 0;
        for (int i = 0, h = 0; i < n; ++i) {
            if (rank[i] > 0) {
                int j = Pos[rank[i] - 1].idx;
                while (i + h < n && j + h < n
                        && str.charAt(i + h) == str.charAt(j + h))
                    h++;
                height[rank[i]] = h;
                if (h > 0)
                    h--;
            }
        }
    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        str = br.readLine();
        SA();
        getHeight(str.length());
        for (int i = 0; i < str.length(); i++)
            System.out.println(Pos[i].idx + 1);

        for (int i = 0; i < str.length(); i++)
            System.out.println(height[i]);

    }
}
 