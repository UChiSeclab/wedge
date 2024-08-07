import java.math.*;
import java.io.*;
import java.util.*;

public class Codeforces{

    static class Valueable
    {
        int a,b;
        Valueable(int x,int y)
        {
            a=x;
            b=y;
        }
    }

    static class Sortbyroll implements Comparator<Valueable> 
    { 
        // Used for sorting in ascending order of 
        // roll number 
        public int compare(Valueable a, Valueable b) 
        { 
            return a.a - b.a; 
        } 
    } 

    static int findMaxElementIndex(int ar[])
    {
        int index=0;
        int max=0;
        int n=ar.length;
        for(int i=0;i<n;i++)
        {
            if(ar[i]>max)
            {
                max=ar[i];
                index=i;
            }
        }
        return index;
    }

    static int findMaxElement(int ar[])
    {
        int max=0;
        int n=ar.length;
        for(int i=0;i<n;i++)
        {
            if(ar[i]>max)
            {
                max=ar[i];
            }
        }
        return max;
    }

    static void printArray(int ar[]) throws Exception
    {
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(java.io.FileDescriptor.out), "ASCII"), 512);
        for(int i=0;i<ar.length;i++)
        {
            out.write(ar[i]+" ");
        }
        out.write("\n");
        out.flush();
    }

    static int binarySearch(int arr[],int value,int start,int end)
    {
        int mid=(start+end)/2;
        int result;
        if(start<=end)
        {
            if(arr[mid]==value)
            {
                return mid;
            }
            else if(arr[mid]<value)
            {
                result=binarySearch(arr, value, mid+1, end);
            }
            else{
                result=binarySearch(arr, value, start, mid-1);
            }
        }
        else{
            return -1;
        }
        return result;
    }
    
    static class FastReader 
    { 
        BufferedReader br; 
        StringTokenizer st; 
 
        public FastReader() 
        { 
            br = new BufferedReader(new
                    InputStreamReader(System.in)); 
        } 
 
        String next() 
        { 
            while (st == null || !st.hasMoreElements()) 
            { 
                try
                { 
                    st = new StringTokenizer(br.readLine()); 
                } 
                catch (IOException e) 
                { 
                    e.printStackTrace(); 
                } 
            } 
            return st.nextToken(); 
        } 
 
        int nextInt() 
        { 
            return Integer.parseInt(next()); 
        } 
 
        long nextLong() 
        { 
            return Long.parseLong(next()); 
        } 
 
        double nextDouble() 
        { 
            return Double.parseDouble(next()); 
        } 
 
        String nextLine() 
        { 
            String str = ""; 
            try
            { 
                str = br.readLine(); 
            } 
            catch (IOException e) 
            { 
                e.printStackTrace(); 
            } 
            return str; 
        }
        
        int[] nextIntArray(int n)
        {
            int arr[]=new int[n];
            for(int i=0;i<n;i++)
            {
                arr[i]=Integer.parseInt(next());
            }
            return arr;
        }

        Integer[] nextIntegerArray(int n)
        {
            Integer arr[]=new Integer[n];
            for(Integer i=0;i<n;i++)
            {
                arr[i]=Integer.parseInt(next());
            }
            return arr;
        }

    } 
 
    static int gcd(Integer a,Integer b)
    {
        if(b==0)
            return a;
        else
            return gcd(b,a%b);
    }
 
    static boolean checkForPrime(long inputNumber)
    {
        boolean isItPrime = true;
        if(inputNumber <= 1) 
        {
            isItPrime = false;
            return isItPrime;
        }
        else
        {
            if(inputNumber%2==0 && inputNumber==2)
            {
                return true;
            }
            else if(inputNumber%2==0)
            {
                return false;
            }
            else{
                int check=(int)Math.sqrt(inputNumber);
                for (int i = 3; i<= check; i+=2) 
                {
                    if ((inputNumber % i) == 0)
                    {
                        isItPrime = false;
                        break;
                    }
                }
            }
        }
 
        return isItPrime;
    }
 
    public static void main(String args[]) throws Exception
    {
        FastReader sc=new FastReader();
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(java.io.FileDescriptor.out), "ASCII"), 512);
        //int t=sc.nextInt();
        //for(int test=0;test<t;test++)
       // {
            int n=sc.nextInt(),i=0,ans=0;;
            int arr[]=sc.nextIntArray(n);
            int prev=0,curr=0,next=0;
            int index=i;
            while(i<n && arr[i]==arr[0])
            {
                i++;
            }
            prev=i-index;
            index=i;
            while(i<n && arr[index]==arr[i])
            {
                i++;
            }
            curr=i-index;
            index=i;
            for(;i<n;i++)
            {
                while(i<n && arr[index]==arr[i])
                {
                    i++;
                }
                next=i-index;
                index=i;
                int x=(int)Math.max(Math.min(prev,curr),Math.min(next,curr));
                if(ans<x)
                {
                    ans=x;
                }
                prev=curr;curr=next;next=0;
            }
            out.write((2*ans)+"");
            out.write("\n");
            out.flush();
        //}
        out.close();
    }
}