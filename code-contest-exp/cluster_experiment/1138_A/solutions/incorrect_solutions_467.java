import java.util.*;
public class home4{
public static void main(String[] args){
  Scanner in = new Scanner(System.in);
  int n, answer, cons, cond1;
  cond1 = 0;
  answer = 0;
  n = in.nextInt();
  int []num = new int[n];
  int []array = new int[n];
  int []array1 = new int[n];
  array[0] = 1;
  for(int i = 0;i<n;i++){
    num[i] = in.nextInt();
  }
  System.out.println("x");
  for(int e = 0;e<n-1;e++){
    if(num[e] == num[e+1]){
      array[cond1]++;
    } else{
      cond1++;
      array[cond1] = 1;
    }
  }
  System.out.println("X");
  for(int r = 0;r<n-1;r++){
    if(array[r]<array[r+1]){
      array1[r] = (array[r]*2);
    } else if(array[r+1]<array[r]){
      array1[r] = (array[r+1]*2);
    }else if(array[r] == array[r+1]){
      array1[r] = (array[r]*2);
    }else if(array[r] == 0){
      break;
    }
  }
  System.out.println("XX");
  Arrays.sort(array1);
  System.out.println(array1[n-1]);
}
}
