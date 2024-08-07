import javafx.util.Pair;

import java.util.*;
import java.io.*;

public class Solution {

    public static void main(String[] args) throws IOException {

        Scanner in=new Scanner(System.in);
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        StringBuffer out = new StringBuffer();

        int size=5*1_00_000;

        ArrayList<Integer> factor[]=new ArrayList[size+1];
        for(int i=0; i<=size; i++)
            factor[i]=new ArrayList<>();

        for(int i=1; i<=size; i++) {
            if(factor[i].size()<=1) {
                for (int j = i+i; j <= size; j += i) {
                    factor[j].add(i);
                }
            }
        }

        int t=in.nextInt();

        OUTER:
        while(t-->0) {

            int n=in.nextInt();
            char arr[]=in.next().toCharArray();

            int D[]=new int[n], K[]=new int[n];
            HashMap<String, Integer> dp[]=new HashMap[n];
            Arrays.fill(dp, new HashMap<>());

            for(int i=0; i<n; i++) {
                if(i!=0) {
                    D[i]+=D[i-1];
                    K[i]+=K[i-1];
                }
                if(arr[i]=='D')
                    D[i]+=1;
                else
                    K[i]+=1;

                if(D[i]==0) {
                    out.append(K[i]+" ");
                } else if(K[i]==0) {
                    out.append(D[i]+" ");
                } else {
                    dp[i].put(Arrays.toString(new int[]{D[i], K[i]}), 1);
                    if(D[i]%K[i]!=0 && K[i]%D[i]!=0) {
                        out.append("1 ");
                    } else {
                        int min=Math.min(D[i], K[i]);
                        int max=Math.max(D[i], K[i]);
                        int diff=max/min, ans=1;

//                        System.out.println(factor[min]);
                        for(int item: factor[min]) if(max%item==0) {
                            int find[]=new int[]{item, item};
                            if(D[i]<K[i]) {
                                find[1]*=diff;
                            } else {
                                find[0]*=diff;
                            }
//                            System.out.println(Arrays.toString(find));
                            int index=i-find[0]-find[1];
                            int value=0;
                            if(index>=0) {
                                value=dp[index].getOrDefault(Arrays.toString(find), 0);
                            }
                            dp[i].put(Arrays.toString(find), value+1);
                            ans=Math.max(ans, value+1);
                        }
                        out.append(ans+" ");
                    }
                }
//                System.out.println(dp[i]);
            }
            out.append("\n");
        }
        System.out.print(out.toString());
    }

    private static int toInt(String s) {
        return Integer.parseInt(s);
    }

    private static long toLong(String s) {
        return Long.parseLong(s);
    }
}