import java.util.*;
import java.io.*;
public class C {
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
    
    static int primes[];
    
    public static void main(String args[]) throws IOException {
        Scan input=new Scan();
        StringBuilder ans=new StringBuilder("");
        sieve();
        int test=input.scanInt();
        for(int tt=1;tt<=test;tt++) {
            int n=input.scanInt();
            String str=input.scanString();
            int d[]=new int[n];
            int k[]=new int[n];
            int cd=0,ck=0;
            for(int i=0;i<n;i++) {
                if(str.charAt(i)=='D') {
                    cd++;
                }
                else {
                    ck++;
                }
                d[i]=cd;
                k[i]=ck;
            }
            
            int dr[]=new int[n];
            int kr[]=new int[n];
            for(int i=0;i<n;i++) {
                cd=get(d,0,i);
                ck=get(k,0,i);
                int dd=cd,kk=ck;
                if(dd==0 || kk==0) {
                    continue;
                }
                for(int j=0;j<primes.length;j++) {
                    while(dd%primes[j]==0 && kk%primes[j]==0) {
                        dd/=primes[j];
                        kk/=primes[j];
                    }
                }
                if(dd%kk==0) {
                    dd/=kk;
                }
                
                dr[i]=dd;
                kr[i]=kk;
            }
            
            int arr[]=new int[n];
            Arrays.fill(arr, 1);
            
            int indx=0;
            while(indx<n && str.charAt(indx)=='D') {
                arr[indx]=indx+1;
                indx++;
            }
            
            indx=0;
            while(indx<n && str.charAt(indx)=='D') {
                arr[indx]=indx+1;
                indx++;
            }
            
            HashMap<Long,Long> map=new HashMap<>();
            
            for(int i=0;i<n;i++) {
                if(dr[i]==0 || kr[i]==0) {
                    continue;
                }
                long tmp=dr[i];
                tmp*=1000000;
                tmp+=kr[i];
                if(!map.containsKey(tmp)) {
                    map.put(tmp, 0L);
                }
                map.replace(tmp, map.get(tmp)+1);
                tmp=map.get(tmp);
                arr[i]=Math.max(arr[i],(int)tmp);
            }
            for(int i=0;i<n;i++) {
                ans.append(arr[i]+" ");
            }
            ans.append("\n");
        }
        System.out.println(ans);
    }
    
    
    public static int get(int arr[],int l,int r) {
        if(l==0) {
            return arr[r];
        }
        return arr[r]-arr[l-1];
    }
    
    public static void sieve() {
        boolean sieve[]=new boolean[750];
        int indx=0;
        primes=new int[132];
        for(int i=2;i<sieve.length;i++) {
            if(sieve[i]) {
                continue;
            }
            primes[indx]=i;
            indx++;
            for(int j=2;i*j<sieve.length;j++) {
                sieve[i*j]=true;
            }
        }
//        System.out.println(indx);
    }
}
