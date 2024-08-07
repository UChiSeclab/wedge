import java.util.*;

public class Main {
	
	static int[] joinArray(int[]... arrays) {
        int length = 0;
        for (int[] array : arrays) {
            length += array.length;
        }

        final int[] result = new int[length];

        int offset = 0;
        for (int[] array : arrays) {
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }

        return result;
    }
	
	static int[] build(int k, int p) {
		if (k > p) return new int[] {};
		if (k == p) return new int[] {(int) (Math.pow(2,p)-1)};
		if (k == 0) return new int[] {0};
		if (p == 2) return new int[] {1,2};
		int nk = k-1;
		int np = p-1;
		int[] a1 = build(k, np);
		int[] a2 = build(nk, np);
		int base = (int) Math.pow(2,np);
		for (int i = 0; i<a2.length; i++) a2[i] += base;
		return joinArray(a1, a2);
	}

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		int n = sc.nextInt();
		int k = sc.nextInt();
		
		if (k > 0) {
			
			long x = 0;
			long[] m = new long[10001];
		
			for (int i=0; i<n; i++) m[sc.nextInt()]++;
			
			int[] ms = build(k, 14);
			
			for (int ii=0; ii<ms.length; ii++) {
				int i = ms[ii];
				for (int j=0; j<10001; j++) {
					int t = i ^ j;
					if (t >= 0 && t < 10001) {
						x += m[t] * m[j];
					}
				}
			}/*
			for (int i=0; i<16384; i++) {
				if (Integer.bitCount(i) == k) {
					for (int j=0; j<10001; j++) {
						int t = i ^ j;
						if (t >= 0 && t < 10001) {
							x += m[t] * m[j];
						}
					}
				}
			}*/
			System.out.println(x/2);
		
		} else {
			
			long x = 0;
			long[] m = new long[10001];
		
			for (int i=0; i<n; i++) {
				int w = sc.nextInt();
				if (m[w] > 0) {
					x += m[w];
					m[w]++;
				} else {
					m[w] = 1;
				}
			}
			System.out.println(x);
		}
	}

}