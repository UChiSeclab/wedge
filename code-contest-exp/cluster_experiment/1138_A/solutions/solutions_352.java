//Author: Patel Rag
//Java version "1.8.0_211"
import java.util.*;
import java.io.*;

public class Main
{
  static class FastReader
  {
    BufferedReader br;
    StringTokenizer st;

    public FastReader()
    {
      br = new BufferedReader(new InputStreamReader(System.in));
    }

    String next()
    {
      while (st == null || !st.hasMoreElements())
      {
        try
        {
          st = new StringTokenizer(br.readLine());
        }
        catch (IOException  e)
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
  }
  static long modExp(long x, long n, long mod)    //Modular exponentiation
  {
    long result = 1;
    while(n > 0)
    {
        if(n % 2 == 1)
            result = (result%mod * x%mod)%mod;
        x = (x%mod * x%mod)%mod;
        n=n/2;
    }
    return result;
  }
  static long gcd(long a, long b)
  {
    if(a==0) return b;
    return gcd(b%a,a);
  }
  static String swap(String a,int i, int j)
  {
    char temp;
    char[] s1 = a.toCharArray();
    temp = s1[i];
    s1[i] = s1[j];
    s1[j] = temp;
    return String.valueOf(s1);
  }
  public static void main(String[] args)
  throws IOException
  {
    FastReader fr = new FastReader();
    int n = fr.nextInt();
    String[] in = fr.nextLine().split(" ");
    int ans = 0;
    int i = 0;
    int c1 = 1;
    while(i < n - 1 && in[i].equals(in[i+1]))
    {
      c1++;
      i++;
    }
    i++;
    while(i < n - 1)
    {
      int c2 = 1;
      while(i < n - 1 && in[i].equals(in[i+1]))
      {
        c2++;
        i++;
      }
      i++;
      ans = Integer.max(ans,Integer.min(c1,c2));
      c1 = c2;
    }
    if(i == n - 1) ans = Integer.max(ans,Integer.min(c1,1));
    System.out.println(2*ans);
  }
}
class Pair<U, V>  // Pair class
{
    public final U first;       // first field of a Pair
    public final V second;      // second field of a Pair

    private Pair(U first, V second)
    {
      this.first = first;
      this.second = second;
    }

    @Override
    public boolean equals(Object o)
    {
      if (this == o) return true;

      if (o == null || getClass() != o.getClass()) return false;

      Pair<?, ?> pair = (Pair<?, ?>) o;

      if (!first.equals(pair.first)) return false;
      return second.equals(pair.second);
    }

    @Override
    public int hashCode()
    {
      return 31 * first.hashCode() + second.hashCode();
    }

    public static <U, V> Pair <U, V> of(U a, V b)
    {
      return new Pair<>(a, b);
    }
}
class myComp implements Comparator<Pair>
{
  public int compare(Pair a,Pair b)
  {
    if((Integer)a.second > (Integer)b.second) return -1;
    if((Integer)a.second < (Integer)b.second) return 1;
    if((Integer)a.first > (Integer)b.first) return 1;
    return -1;
  }
}
class BIT       //Binary Indexed Tree
{
  public long[] m_array;

  public BIT(long[] dat)
  {
    m_array = new long[dat.length + 1];
    Arrays.fill(m_array,0);
    for(int i = 0; i < dat.length; i++)
    {
      m_array[i + 1] = dat[i];
    }
    for(int i = 1; i < m_array.length; i++)
    {
      int j = i + (i & -i);
      if(j < m_array.length)
      {
        m_array[j] = m_array[j] + m_array[i];
      }
    }
  }

  public final long prefix_query(int i)
  {
    long result = 0;
    for(++i; i > 0; i = i - (i & -i))
    {
      result = result + m_array[i];
    }
    return result;
  }

  public final long range_query(int fro, int to)
  {
    if(fro == 0)
    {
      return prefix_query(to);
    }
    else
    {
      return (prefix_query(to) - prefix_query(fro - 1));
    }
  }

  public void update(int i, long add)
  {
    for(++i; i < m_array.length; i = i + (i & -i))
    {
      m_array[i] = m_array[i] + add;
    }
  }
}
