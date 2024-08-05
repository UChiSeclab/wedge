import java.io.*;
import java.util.*;

public class Sirius {
    public static void main(String[] args) throws IOException {
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        int[] array = new int[n];
        for(int  i = 0; i < n; ++i){
            array[i] = scan.nextInt();
        }
        int f = 0;
        int s = 0;
        int ans = 0;
        if(array[0] == 1){
            ++f;
        }
        else{
            ++s;
        }
        for(int  i = 1; i < n; ++i){
            if(s == 0 || f == 0){
                if(array[i] == 1){
                    ++f;
                }
                else{
                    ++s;
                }
            }
            else{
                if(array[i] == array[i-1]){
                    if(array[i] == 1){
                        ++f;
                    }
                    else{
                        ++s;
                    }
                }
                else{
                    ans = Math.max(ans, Math.min(f,s)*2);
                    if(array[i] == 1){
                        f = 1;
                    }
                    else{
                        s = 1;
                    }
                }
            }
        }
        System.out.println(Math.max(ans, Math.min(f,s)*2));
    }
}