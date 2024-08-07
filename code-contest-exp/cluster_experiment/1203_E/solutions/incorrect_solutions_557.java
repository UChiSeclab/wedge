import java.util.*;


public class Main {
  public static void main(String[] args) {
    System.out.println("Hello world!");
    Scanner in = new Scanner(System.in);
    int people = in.nextInt();
    int arr[] = new int[people];
    Integer ind[] = new Integer[people];
    for(int i = 0; i < people; i++){
      ind[i] = i;
        arr[i] = in.nextInt();
    }
    Arrays.sort(ind, (a,b) -> arr[ind[a]] - arr[ind[b]]); 
    int cur = 0;
    int amnt = 0;
    for(int ele : ind)
    System.out.print(arr[ele] + " ");
    for(int i = 0; i < arr.length; i++){
      
      if(cur <  arr[ind[i]]  && cur - arr[ind[i]] != -1){
        amnt++;
        cur = arr[ind[i]]-1;
        
      }
      else if( cur<arr[ind[i]] ){
        amnt++;
       cur = arr[ind[i]];
      }
      else if (arr[ind[i]] == cur){
        amnt++;
        cur = arr[ind[i]]+1;
      }
    }
    System.out.println(amnt);

  }
}