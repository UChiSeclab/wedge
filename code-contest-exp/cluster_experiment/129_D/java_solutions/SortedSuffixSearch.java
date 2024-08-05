import java.awt.Point;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class SortedSuffixSearch {
	static Scanner sc;
    public static void main(String[] args) throws Exception {
    	sc = new Scanner(System.in);
        char[] str = sc.next().toCharArray();
        long max = (((long) str.length) * (str.length + 1)) / 2;
        int k = sc.nextInt();
        if (k > max) {
            System.out.println("No such line.");
            return;
        }
        SuffixArray suffixArray = new SuffixArray(str);
        suffixArray.sortSuffixes();
        long[] accumSubstrCnt = new long[str.length];
        accumSubstrCnt[0] = str.length - suffixArray.pos[0];
        for (int i = 1; i < str.length; i++) {
            accumSubstrCnt[i] = (str.length - suffixArray.pos[i])
                    + accumSubstrCnt[i - 1];
        }
        StringBuilder res = new StringBuilder();
        int curSt = 0, curEnd = str.length - 1;
        while (k > 0) {
            int l = curSt, h = curEnd + 1, mid;
            while (l < h) {
                mid = l + (h - l) / 2;
                long curSum = accumSubstrCnt[mid];
                if (curSt > 0) {
                    curSum -= accumSubstrCnt[curSt - 1];
                }
                curSum -= ((long) res.length() * (mid - curSt + 1));
                if (curSum < k) {
                    l = mid + 1;
                } else {
                    h = mid;
                }
            }
            Point range = suffixArray.countOccuring(curSt, curEnd,
                    str[suffixArray.pos[l] + res.length()], res.length());
            long toRemove = 0;
            if (range.x > 0) {
                toRemove += accumSubstrCnt[range.x - 1];
            }
            if (curSt > 0) {
                toRemove -= accumSubstrCnt[curSt - 1];
            }
            toRemove -= ((range.x - curSt) * res.length());
            toRemove += (range.y - range.x + 1);
            k -= (toRemove);
            curSt = range.x;
            curEnd = range.y;
            res.append(str[suffixArray.pos[l] + res.length()]);
        }
        System.out.println(res);
    }

    static class SuffixArray {
        Integer[] pos;
        int[] rank, LCP;
        char[] str;

        public SuffixArray(char[] str) {
            this.str = str;
        }

        public Point countOccuring(int startIdx, int endIdx, char reqLet,
                int reqLetIdx) throws Exception {
            int l = startIdx, h = endIdx + 1, mid;
            while (l < h) {
                mid = l + (h - l) / 2;
                if (pos[mid] + reqLetIdx >= str.length
                        || str[pos[mid] + reqLetIdx] < reqLet) {
                    l = mid + 1;
                } else {
                    h = mid;
                }
            }
            int start = l;
            l = startIdx;
            h = endIdx + 1;
            while (l < h) {
                mid = l + (h - l) / 2;
                if (pos[mid] + reqLetIdx >= str.length
                        || str[pos[mid] + reqLetIdx] <= reqLet) {
                    l = mid + 1;
                } else {
                    h = mid;
                }
            }
            l--;
            return new Point(start, l);
        }

        public void sortSuffixes() {
            pos = new Integer[str.length];
            rank = new int[str.length];
            for (int i = 0; i < pos.length; i++) {
                pos[i] = i;
            }
            Arrays.sort(pos, new Comparator<Integer>() {
                public int compare(Integer o1, Integer o2) {
                    if (str[o2] < str[o1])
                        return 1;
                    else if (str[o2] > str[o1])
                        return -1;
                    return 0;
                }
            });
            for (int i = 0; i < pos.length; i++) {
                rank[pos[i]] = i;
            }
            int[] nxt = new int[str.length], cnt = new int[str.length];
            boolean[] BH = new boolean[str.length], B2H = new boolean[str.length];
            for (int i = 0; i < pos.length; i++) {
                if (i == 0 || str[pos[i]] != str[pos[i - 1]])
                    BH[i] = true;
            }
            for (int h = 1; h < str.length; h <<= 1) {
                int buckets = 0;
                for (int i = 0, j; i < str.length; i = j) {
                    j = i + 1;
                    while (j < str.length && !BH[j])
                        j++;
                    nxt[i] = j;
                    buckets++;
                }
                if (buckets == str.length) {
                    break;
                }
                for (int i = 0; i < str.length; i = nxt[i]) {
                    cnt[i] = 0;
                    for (int j = i; j < nxt[i]; j++) {
                        rank[pos[j]] = i;
                    }
                }
                cnt[rank[str.length - h]]++;
                B2H[rank[str.length - h]] = true;
                for (int i = 0; i < str.length; i = nxt[i]) {
                    for (int j = i; j < nxt[i]; j++) {
                        int s = pos[j] - h;
                        if (s >= 0) {
                            int head = rank[s];
                            rank[s] = head + cnt[head]++;
                            B2H[rank[s]] = true;
                        }
                    }
                    for (int j = i; j < nxt[i]; j++) {
                        int s = pos[j] - h;
                        if (s >= 0 && B2H[rank[s]]) {
                            for (int k = rank[s] + 1; k < str.length
                                    && (B2H[k] && !BH[k]); k++) {
                                B2H[k] = false;
                            }
                        }
                    }
                }
                for (int i = 0; i < str.length; i++) {
                    pos[rank[i]] = i;
                    BH[i] |= B2H[i];
                    B2H[i] = false;
                }
            }
        }
    }
}
