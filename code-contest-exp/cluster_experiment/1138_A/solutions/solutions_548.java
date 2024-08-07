import java.util.ArrayList;
import java.util.Scanner;

public class Main
{
    public static void main (String[] args) {
        Scanner s = new Scanner(System.in);
        int n = s.nextInt();
        int[] array = new int[n];
        int i;
        for(i=0;i<n;i++){
            array[i] = s.nextInt();
        }

        int countfirst=0;
        int current= array[0];
        ArrayList<Integer> myList = new ArrayList<>();
        for(i=0;i<n;i++){
            if(array[i] == current){
                countfirst++;
            }
            else{
                myList.add(countfirst);
                countfirst=1;
                current=array[i];
            }
        }
        myList.add(countfirst);

        //System.out.println(myList);

        int max = 0;
        for(i=0;i<(myList.size()-1);i++){
            if(Math.min(myList.get(i), myList.get(i+1)) > max){
                max = Math.min(myList.get(i), myList.get(i+1));
            }
        }
        System.out.println(max*2);
    }
}

