import java.util.Scanner;

/**
 *
 * @author abhishekraj
 */
public class Codefor {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
      int count1=Integer.MIN_VALUE,count2=Integer.MIN_VALUE,max1=Integer.MIN_VALUE,max2=Integer.MIN_VALUE,ans=Integer.MIN_VALUE;
        Scanner sc = new Scanner(System.in);
        long n=sc.nextLong();
        int a[] = new int[(int)n];
        for(int i=0;i<n;i++)
        {
            a[i]=sc.nextInt();
        }
        for(int i=0;i<n-1;i++)
        {
            
           if(a[i]==1)
           {
               if(a[i]==a[i+1])
               {
                   count1++;
                   max1=Math.max(max1, count1);
               }
           }
           
           if(a[i]==2)
           {
               if(a[i]==a[i+1])
               {
                   count2++;
                   max2=Math.max(max2, count2);
               }
           }
           
           
        }
        
        ans= Math.min(max1, max2);
        System.out.println(2*(ans+1));
    }
    
}
