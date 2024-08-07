//package codeforces;

import java.util.Arrays;
import java.util.Scanner;

public class Sushi {
	public static int radius(int i, int N) {
		int radius;
		if (N % 2 != 0) {
			if (i + 1 <= Math.ceil(N / 2)) {
				radius = i;
			} else
				radius = N - i + 1;
		} else {
			if (i + 1 <= N / 2) {
				radius = i;
			} else
				radius = N - i;
		}
		return radius;
	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int N = scanner.nextInt();
		int[] sushi = new int[N];
		for (int i = 0; i < N; i++) {
			sushi[i] = scanner.nextInt();
		}
		
//		boolean equal = true;
//		int[] radius = new int[N];
//		int l = 0;
//		for (int i = 1; i < N - 2; i++) {
//			if (sushi[i] != sushi[i + 1]) {
//				for (int rad = 1; rad <= radius(i, N); rad++) {
//					for (int j = i; j <= i - rad; j--) {
//						if (sushi[i] != sushi[j]) {
//							equal = false;
//							break;
//						}
//					}
//					if (equal == true) {
//						for (int k = i + 1; k > i + rad; k++) {
//							if (sushi[i + 1] != sushi[k]) {
//								equal = false;
//								break;
//							}
//						}
//					}
//					if (equal = true) {
//						radius[l] = rad;
//						l++;
//					}
//
//				}
//			}
//		}
//		System.out.println(Arrays.toString(radius));

		int[] sum= new int[N];
		for (int i = 0; i < sum.length; i++) {
			sum[i] = 1;
		}
		int k = 0;
		for (int i = 0; i < sushi.length-1; i++) {
			if (sushi[i] == sushi[i+1]) {
				sum[k]++;
			}
			else
				k++;
		}
		int[] result = new int[N];
		for (int i = 0; i < sum.length-1; i++) {
			result[i] = Math.min(sum[i], sum[i+1]);
		}
		Arrays.sort(result);
//		System.out.println(Arrays.toString(sum));
//		System.out.println(Arrays.toString(result));
		System.out.println(result[result.length-1]*2);
		scanner.close();
	}

}
