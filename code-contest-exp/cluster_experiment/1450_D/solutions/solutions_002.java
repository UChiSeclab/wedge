/*
TASK: template
LANG: JAVA
*/
import java.io.*;
import java.lang.*;
import java.util.*;

public class D1450 {
    public static void main(String[] args) throws IOException{
        StringBuffer ans = new StringBuffer();
        StringTokenizer st;
        BufferedReader f = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(f.readLine());
        int t = Integer.parseInt(st.nextToken());
        for(int i = 0; i < t; i++){
            st = new StringTokenizer(f.readLine());
            int n = Integer.parseInt(st.nextToken());
            int[] arr = new int[n];
            st = new StringTokenizer(f.readLine());
            HashMap<Integer, Integer> hash = new HashMap<>();
            int minValley = Integer.MAX_VALUE;
            int smallestDupe = Integer.MAX_VALUE;
            for(int x = 0; x < n; x++){
                arr[x] = Integer.parseInt(st.nextToken());
                if(!hash.containsKey(arr[x])){
                    hash.put(arr[x],0);
                }else{
                    smallestDupe = Math.min(arr[x], smallestDupe);
                }
                hash.put(arr[x],hash.get(arr[x])+1);
                int c = x-1;
                if(c > 0 && c < n-1 && arr[c] < arr[c-1] && arr[c] < arr[c+1]){
                    minValley = Math.min(arr[c], minValley);
                }
            }
            int maxPermute = 0;
            for(int x = 1; x <= n; x++){
                if(hash.containsKey(x)){
                    maxPermute = x;
                }else
                    break;
            }
            int[] arr2 = arr.clone();
            Arrays.sort(arr2);
            int firstAppearanceValley = -1;
            int firstAppearanceDupe = -1;
            for(int x = 0; x < n; x++){
                if(arr2[x] == minValley && firstAppearanceValley == -1) {
                    firstAppearanceValley = x;
                }
                if(arr2[x] == smallestDupe && firstAppearanceDupe == -1) {
                    firstAppearanceDupe = x;
                }
            }
            int r = n-Math.min(firstAppearanceDupe, firstAppearanceValley)-1;
            if(firstAppearanceDupe == -1 && firstAppearanceValley == -1)
                r = 0;
            else if(firstAppearanceDupe == -1 || firstAppearanceValley == -1)
                r = n-1-Math.max(firstAppearanceDupe, firstAppearanceValley);
            //System.out.println(r + " " + firstAppearanceDupe + " " + firstAppearanceValley);
            for(int x = 0; x < n; x++){
                if((x >= r) && maxPermute >= n-x)
                    ans.append(1);
                else if(x == 0 && maxPermute == n)
                    ans.append(1);
                else
                    ans.append(0);

            }
            ans.append("\n");

        }
        f.close();


        System.out.println(ans);

    }
}