import java.io.*;
import java.util.*;

public class F {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter writer = new PrintWriter(System.out);
        int n = Integer.parseInt(reader.readLine());
        ArrayList<Integer> arr = new ArrayList<>();
        StringTokenizer st = new StringTokenizer(reader.readLine());
        for(int i = 0; i < n; ++i){
            arr.add(Integer.parseInt(st.nextToken()));
        }
        Collections.sort(arr);
        HashSet<Integer> found = new HashSet<>();
        for(int i = 0; i < n; ++i){
            if(arr.get(i) > 1){
                if(!found.contains(arr.get(i) - 1)){
                    found.add(arr.get(i) - 1);
                }
                else if(!found.contains(arr.get(i))){
                    found.add(arr.get(i));
                }
                else{
                    found.add(arr.get(i) + 1);
                }
            }
            else{
                if(found.contains(1)){
                    found.add(2);
                }
                else{
                    found.add(1);
                }
            }
        }
        System.out.println(found.size());
    }
}