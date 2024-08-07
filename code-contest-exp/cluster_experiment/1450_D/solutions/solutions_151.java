import java.util.*;
import java.io.*;
public class Main {
    static class Scan {
        private byte[] buf=new byte[1024];
        private int index;
        private InputStream in;
        private int total;
        public Scan()
        {
            in=System.in;
        }
        public int scan()throws IOException
        {
            if(total<0)
            throw new InputMismatchException();
            if(index>=total)
            {
                index=0;
                total=in.read(buf);
                if(total<=0)
                return -1;
            }
            return buf[index++];
        }
        public int scanInt()throws IOException
        {
            int integer=0;
            int n=scan();
            while(isWhiteSpace(n))
            n=scan();
            int neg=1;
            if(n=='-')
            {
                neg=-1;
                n=scan();
            }
            while(!isWhiteSpace(n))
            {
                if(n>='0'&&n<='9')
                {
                    integer*=10;
                    integer+=n-'0';
                    n=scan();
                }
                else throw new InputMismatchException();
            }
            return neg*integer;
        }
        public double scanDouble()throws IOException
        {
            double doub=0;
            int n=scan();
            while(isWhiteSpace(n))
            n=scan();
            int neg=1;
            if(n=='-')
            {
                neg=-1;
                n=scan();
            }
            while(!isWhiteSpace(n)&&n!='.')
            {
                if(n>='0'&&n<='9')
                {
                    doub*=10;
                    doub+=n-'0';
                    n=scan();
                }
                else throw new InputMismatchException();
            }
            if(n=='.')
            {
                n=scan();
                double temp=1;
                while(!isWhiteSpace(n))
                {
                    if(n>='0'&&n<='9')
                    {
                        temp/=10;
                        doub+=(n-'0')*temp;
                        n=scan();
                    }
                    else throw new InputMismatchException();
                }
            }
            return doub*neg;
        }
        public String scanString()throws IOException
        {
            StringBuilder sb=new StringBuilder();
            int n=scan();
            while(isWhiteSpace(n))
            n=scan();
            while(!isWhiteSpace(n))
            {
                sb.append((char)n);
                n=scan();
            }
            return sb.toString();
        }
        private boolean isWhiteSpace(int n)
        {
            if(n==' '||n=='\n'||n=='\r'||n=='\t'||n==-1)
            return true;
            return false;
        }
    }
    
    public static void sort(int arr[],int l,int r) {    //sort(arr,0,n-1);
        if(l==r) {
            return;
        }
        int mid=(l+r)/2;
        sort(arr,l,mid);
        sort(arr,mid+1,r);
        merge(arr,l,mid,mid+1,r);
    }
    public static void merge(int arr[],int l1,int r1,int l2,int r2) {
        int tmp[]=new int[r2-l1+1];
        int indx1=l1,indx2=l2;
        //sorting the two halves using a tmp array
        for(int i=0;i<tmp.length;i++) {
            if(indx1>r1) {
                tmp[i]=arr[indx2];
                indx2++;
                continue;
            }
            if(indx2>r2) {
                tmp[i]=arr[indx1];
                indx1++;
                continue;
            }
            if(arr[indx1]<arr[indx2]) {
                tmp[i]=arr[indx1];
                indx1++;
                continue;
            }
            tmp[i]=arr[indx2];
            indx2++;
        }
        //Copying the elements of tmp into the main array
        for(int i=0,j=l1;i<tmp.length;i++,j++) {
            arr[j]=tmp[i];
        }
    }
    
    public static void sort(long arr[],int l,int r) {    //sort(arr,0,n-1);
        if(l==r) {
            return;
        }
        int mid=(l+r)/2;
        sort(arr,l,mid);
        sort(arr,mid+1,r);
        merge(arr,l,mid,mid+1,r);
    }
    public static void merge(long arr[],int l1,int r1,int l2,int r2) {
        long tmp[]=new long[r2-l1+1];
        int indx1=l1,indx2=l2;
        //sorting the two halves using a tmp array
        for(int i=0;i<tmp.length;i++) {
            if(indx1>r1) {
                tmp[i]=arr[indx2];
                indx2++;
                continue;
            }
            if(indx2>r2) {
                tmp[i]=arr[indx1];
                indx1++;
                continue;
            }
            if(arr[indx1]<arr[indx2]) {
                tmp[i]=arr[indx1];
                indx1++;
                continue;
            }
            tmp[i]=arr[indx2];
            indx2++;
        }
        //Copying the elements of tmp into the main array
        for(int i=0,j=l1;i<tmp.length;i++,j++) {
            arr[j]=tmp[i];
        }
    }
    
    public static void main(String args[]) throws IOException {
        Scan input=new Scan();
        StringBuilder ans=new StringBuilder("");
        int test=input.scanInt();
        for(int tt=1;tt<=test;tt++) {
            int n=input.scanInt();
            int arr[]=new int[n];
            for(int i=0;i<n;i++) {
                arr[i]=input.scanInt();
            }
            ans.append(solve(n,arr));
        }
        System.out.println(ans);
    }
    public static StringBuilder solve(int n,int arr[]) {
        int lft[]=lft(n,arr);
        int rgt[]=rgt(n,arr);
        
        int min[]=new int[n];
        int max[]=new int[n];
        int cnt[]=new int[n+5];
        
//        for(int i=0;i<n;i++) {
//            System.out.print(lft[i]+" ");
//        }
//        System.out.println();
//        for(int i=0;i<n;i++) {
//            System.out.print(rgt[i]+" ");
//        }
//        System.out.println();
        
        boolean taken[]=new boolean[n+5];
        
        
        for(int i=0;i<n;i++) {
            if(taken[arr[i]]) {
                continue;
            }
            taken[arr[i]]=true;
            if(lft[i]==-1 && rgt[i]==-1 && i!=0 && i!=n-1) {
//                System.out.println(1+" "+arr[i]);
                cnt[1]++;
                cnt[2]--;
                cnt[n]++;
                cnt[n+1]--;
            }
            else {
//                System.out.println(2+" "+arr[i]);
                int l=lft[i];
                int r=rgt[i];
                if(rgt[i]==-1) {
                    r=n;
                }
                int len=r-l-1;
                if(l==i-1 || r==i+1) {
                    cnt[1]++;
                    cnt[len+1]--;
                }
                else {
                    cnt[1]++;
                    cnt[2]--;
                    cnt[len]++;
                    cnt[len+1]--;
                }
            }
        }
        
        int curr=0;
        for(int i=0;i<cnt.length;i++) {
            curr+=cnt[i];
            cnt[i]=curr;
        }
        
        int prefix[]=new int[n+5];
        for(int i=1;i<=n;i++) {
            prefix[i]=prefix[i-1]+(taken[i]?1:0);
        }
        
        StringBuilder ans=new StringBuilder("");
        boolean mis=false;
        for(int i=1,j=n;i<=n;i++,j--) {
            if(prefix[j]==j && cnt[i]==(n-i+1)) {
                ans.append(1);
            }
            else {
                ans.append(0);
            }
//            System.out.print(cnt[i]+" ");
        }
//        System.out.println();
//        for(int i=1;i<=n;i++) {
//            System.out.print((n-i+1)+" ");
//        }
//        System.out.println("\n");
        ans.append("\n");
        return ans;
    }
    
    static int[] lft(int n,int arr[]) { 
        Stack<Integer> S = new Stack<>(); 
        int tmp[]=new int[n];
        Arrays.fill(tmp, -1); 
        for (int i = 0; i < n; i++) {  
            while (!S.empty() && arr[S.peek()] >= arr[i]) { 
                S.pop(); 
            } 
  

            if (S.empty());
            else 
            { 
                tmp[i]=S.peek(); 
            } 
  
 
            S.push(i); 
        } 
        return tmp;
    } 
    
    static int[] rgt(int n,int arr[]) { 
        Stack<Integer> S = new Stack<>(); 
        int tmp[]=new int[n];
        Arrays.fill(tmp, -1); 
        for (int i = n-1; i >= 0; i--) {  
            while (!S.empty() && arr[S.peek()] >= arr[i]) { 
                S.pop(); 
            } 
  

            if (S.empty());
            else 
            { 
                tmp[i]=S.peek(); 
            } 
  
 
            S.push(i); 
        } 
        return tmp;
    }
}

