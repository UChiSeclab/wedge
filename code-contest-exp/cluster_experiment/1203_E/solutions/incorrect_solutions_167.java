import java.util.Scanner;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collection;
public class Main {
 
    public static void main(String[] args) 
    {
        Scanner ab=new Scanner(System.in);
        
        int x=ab.nextInt();long freq[]=new long[150009];int max=0;
        for(int y=0;y<x;y++)
        {
            int w=ab.nextInt();
            freq[w]++;
            if(w>max)max=w;
        }
    long first=last(freq,max);    
    long second=one(freq,max);    
        
        
   if(second>x)System.out.println(first);
   else
   System.out.println(second);
   
    }
   public static long last (long arr[],int max)
    {
        long sum=0;
        for(int y=max;y>0;y--)
        {
            if(arr[y]==0) continue;
            
            else if(y==max&&arr[y]>0)
            {
                sum++;
                arr[y+1]=1;
                arr[y]-=1;
                if(arr[y]>0&&y-1>0)
                {
                    if(arr[y-1]==0)
                    {
                        arr[y-1]=1;
                        arr[y]-=1;
                    }
                    if(arr[y]>0){sum++;}
                }
            }   
            else 
            {
                
                        if(arr[y+1]==0){sum++;arr[y+1]+=1;arr[y]-=1;}
                     //  else if(arr[y-1]==0){arr[y-1]+=1;arr[y]-=1;}
                        else {sum++;arr[y]-=1;}
                        if(arr[y]>0&&y!=1)
                        {
                            if(arr[y-1]==0){arr[y-1]+=1;if(arr[y]>1)arr[y]-=1;}
                        }
                
                       else if (arr[y]>0&&y==1){sum++;if(arr[y]>1)arr[y]-=1;}
 
            }   
        }
        //System.out.println("f");
       // for(int y=0;y<max+2;y++)System.out.print(arr[y]+" ");System.out.println();
        return sum;
    }
    public static long one (long arr[],int max)
    {
        long sum=0;
        for(int y=0;y<max+2;y++)
        {
            if(arr[y]==0) continue;
            
            else if(y==max&&arr[y]>0)
            {
                sum++;
                arr[y+1]=1;
                arr[y]-=1;
                if(arr[y]>0&&y-1>0)
                {
                    if(arr[y-1]==0)
                    {
                        arr[y-1]=1;
                        arr[y]-=1;
                    }
                    if(arr[y]>0){sum++;}
                }
            }   
            else 
            {           
                        if(arr[y+1]==0){sum++;arr[y+1]+=1;arr[y]-=1;}
                     //  else if(arr[y-1]==0){arr[y-1]+=1;arr[y]-=1;}
                        else {sum++;arr[y]-=1;}
                        if(arr[y]>0&&y!=1)
                        {
                            if(arr[y-1]==0){arr[y-1]+=1;if(arr[y]>1)arr[y]-=1;}
                        }
                
                       else if (arr[y]>0&&y==1){sum++;if(arr[y]>1)arr[y]-=1;}
 
            }   
        }
      //  System.out.println("s");
        //for(int y=0;y<max+2;y++)System.out.print(arr[y]+" ");System.out.println();
        return sum;
    }    
}