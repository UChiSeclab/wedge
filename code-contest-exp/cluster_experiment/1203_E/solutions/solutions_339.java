// This template code suggested by KT BYTE Computer Science Academy
//   for use in reading and writing files for USACO problems.

// https://content.ktbyte.com/problem.java

import java.util.*;
import java.io.*;

public class boxers {

    static StreamTokenizer in;

    static int nextInt() throws IOException {
        in.nextToken();
        return (int) in.nval;
    }

    static String next() throws IOException {
        in.nextToken();
        return in.sval;
    }

    public static void main(String[] args) throws Exception {
        in = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));

        int n = nextInt();

        TreeMap<Integer, Integer> nums = new TreeMap<>();

        for (int i = 0; i < n; i++) {
            int a = nextInt();

            if (nums.containsKey(a)) {
                nums.put(a, nums.get(a) + 1);
            }

            else {
                nums.put(a, 1);
            }
        }

        HashSet<Integer> used = new HashSet<>();

        for (int key: nums.keySet()) {
            int num_of = nums.get(key);

            if (num_of >= 3) {
                used.add(key-1);
                used.add(key);
                used.add(key+1);
            }

            else if (num_of == 2) {
                if (used.contains(key-1) || key-1 == 0) {
                    used.add(key);
                    used.add(key+1);
                }

                else if (used.contains(key)) {
                    used.add(key-1);
                    used.add(key+1);
                }

                else {
                    used.add(key-1);
                    used.add(key);
                }
            }

            else {
                if (used.contains(key-1) || key-1 == 0) {
                    if (used.contains(key)) used.add(key+1);
                    else used.add(key);
                }

                else if (used.contains(key)) {
                    used.add(key-1);
                }

                else {
                    used.add(key-1);
                }
            }
        }

        used.remove(0);

        int result = used.size();
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));
        out.println(result);
        out.close();
    }
}


