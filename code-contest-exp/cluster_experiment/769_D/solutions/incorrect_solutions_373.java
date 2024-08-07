import java.util.Scanner;

public class D {
    public static void main(String[] args) {
        Scanner input=new Scanner(System.in);
        int n = input.nextInt();
        int k = input.nextInt();
        
        String[] reversedNumbers= new String[n];
        
        for(int i = 0; i < n; i++) {
            reversedNumbers[i] = new StringBuilder(Integer.toBinaryString(input.nextInt())).reverse().toString();
        }
        
        int result = 0;
        
        for(int i = 0; i < n - 1; i++) {
            for(int j = i + 1; j < n; j++) {
                int currentK = 0;
                for(int a = 0 ; a < reversedNumbers[i].length() && a < reversedNumbers[j].length(); a++) {
                    if(reversedNumbers[i].charAt(a) != reversedNumbers[j].charAt(a))
                        currentK++;
                }
                if(currentK == k)
                    result++;
            }
        }
        
        
        System.out.println(result);
    }
}