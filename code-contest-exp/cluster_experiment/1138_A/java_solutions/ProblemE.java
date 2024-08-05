import java.util.Scanner;
public class ProblemE
{
    public static void main(String args[])
    {
        Scanner sc=new Scanner(System.in);
        int n=Integer.parseInt(sc.nextLine());
        String s[]=(sc.nextLine()+" .").split(" ");
        int i1=0,i2=0;
        int c1[]=new int[n];
        int c2[]=new int[n];
        for(int x=0; x<=n; x++)
        {
            String str=s[x];
            for(int y=x+1; y<=n; y++)
            {
                if(str.charAt(str.length()-1)==s[y].charAt(0))
                    str+=s[y];                
                else
                {
                    if(str.charAt(0)=='1' && isContinous(str))
                    {
                        c1[i1++]=str.length();                      
                        break;
                    }
                    if(str.charAt(0)=='2' && isContinous(str))
                    {
                        c2[i2++]=str.length();
                        break;
                    }
                }
            }
        }
        int co1=c1[0];
        int co2=c2[0];
        for(int x=1; x<c1.length; x++)
        {
            if(c1[x]>co1)
            co1=c1[x];
        }
        for(int x=1; x<c2.length; x++)
        {
            if(c2[x]>co2)
            co2=c2[x];
        }
        System.out.println(co1<co2?co1*2:co2*2);
        sc.close();
    }
    private static boolean isContinous(String s)
    {
        char a=s.charAt(0);
        for(int x=1; x<s.length(); x++)
        {
            if(s.charAt(x)!=a)
                return false;
        }
        return true;
    }
}