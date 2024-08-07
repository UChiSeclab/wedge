import java.util.Scanner;
public class Sushi{
public static void main(String[] args)
{
Scanner s=new Scanner(System.in);
int n=s.nextInt();
int next;
int tuni=0;
int eel=0;
int max=0;
int last=0;
for(int i=0;i<n;i++)
{
next=s.nextInt();
if(next==1)
tuni=(last==1?tuni:0)+1;
else
eel=(last==2?eel:0)+1;
last=next;
max=Math.max(max,Math.min(tuni,eel));
}
System.out.println(max*2);
}
}