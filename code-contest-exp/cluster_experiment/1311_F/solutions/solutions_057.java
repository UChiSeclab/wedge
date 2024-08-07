//package com.netease.music.codeforces.round624.div3;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by dezhonger on 2020/2/27
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[][] xv = new int[n][2];
        Integer[] v = new Integer[n];
        for (int i = 0; i < n; i++) {
            xv[i][0] = scanner.nextInt();
        }
        for (int i = 0; i < n; i++) {
            xv[i][1] = scanner.nextInt();
            v[i] = xv[i][1];
        }

        Arrays.sort(v);

        Arrays.sort(xv, (o1, o2) -> {
            if (o1[0] != o2[0]) return Integer.compare(o1[0], o2[0]);
            return Integer.compare(o1[1], o2[1]);
        });

        FenwickTreeLong f1 = new FenwickTreeLong(n);
        FenwickTreeLong f2 = new FenwickTreeLong(n);


        long result = 0;
        for (int i = 0; i < n; i++) {
            //按照x坐标从小到大进行枚举
            //找到离散化后v的位置
            int pos = Arrays.binarySearch(v, xv[i][1]);
            pos++;
            //速度比当前速度小的有贡献，贡献为距离之差
            //有num个点的速度为当前速度小
            long num = f1.sum(pos);
            //这num个点的位置的坐标之和为xsum
            long xsum = f2.sum(pos);
            //计算对答案的贡献
            result += num * xv[i][0] - xsum;
            f1.change(pos, 1);
            f2.change(pos, xv[i][0]);
        }
        System.out.println(result);

    }
}

class FenwickTreeLong {
    long[] t;
    int n;
    public FenwickTreeLong(int n) {
        this.n = n;
        this.t = new long[n + 1];
    }

    void change(int pos, int val) {
        while (pos <= n) {
            t[pos] += val;
            pos += lowbit(pos);
        }
    }

    int lowbit(int k) {
        return k & (-k);
    }

    long sum(int pos) {
        long ans = 0;
        while (pos > 0) {
            ans += t[pos];
            pos -= lowbit(pos);
        }
        return ans;
    }

    long ask(int l, int r) {
        return sum(r) - sum(l);
    }
}