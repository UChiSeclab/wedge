//package com.trivv.training.otp;

import java.io.File;
import java.util.Scanner;

public class SushiForTwo {
	
	private int n;
	private int[] a;
	private int[] tuna_left;
	private int[] tuna_right;
	private int[] eel_left;
	private int[] eel_right;
	
	private final static String FILE_IN = "subseq.inp";
	
	public void input(String fileInput) {
		try {
			//Scanner scanner = new Scanner(new File(fileInput));
			Scanner scanner = new Scanner(System.in);
			n = scanner.nextInt();
			a = new int[n+1];
			tuna_left = new int[n+2];
			tuna_right = new int[n+2];
			eel_left = new int[n+2];
			eel_right = new int[n+2];
			for (int i = 1; i <= n; ++i) {
				a[i] = scanner.nextInt();
				if (a[i] == 1) {
					tuna_left[i] = tuna_left[i - 1] + 1;
				}
				else {
					eel_left[i] = eel_left[i - 1] + 1;
				}
			}
			scanner.close();
		} catch (Exception ex) {
			System.out.println("[input] file not found " + ex.getMessage());
		}
	}
	
	private int min(int a, int b) {
		if (a < b) return a;
		else return b;
	}
	
	private int max(int a, int b) {
		if (a > b) return a;
		else return b;
	}
	
	public int solve() {
		int res = 0;
		
		for (int i = n; i > 0; --i) {
			if (a[i] == 1) {
				tuna_right[i] = tuna_right[i + 1] + 1;
				int res_tmp1 = min(tuna_left[i], eel_right[i + 1]);
				int res_tmp2 = min(tuna_right[i], eel_left[i - 1]);
				res = max (res, res_tmp1);
				res = max (res, res_tmp2);
			}
			else {
				eel_right[i] = eel_right[i + 1] + 1;
				int res_tmp1 = min(eel_left[i], tuna_right[i + 1]);
				int res_tmp2 = min(eel_right[i], tuna_left[i - 1]);
				res = max (res, res_tmp1);
				res = max (res, res_tmp2);
			}
		}
		return res * 2;
	}
	
	public static void main(String[] args) {
		SushiForTwo runner = new SushiForTwo();
		runner.input(FILE_IN);
		System.out.println(runner.solve());
	}
}
